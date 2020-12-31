# wangjc-vip-permission-starter

一套迅捷版的Java权限控制方案（对比shiro 和 spring security）

# 一、简介

wangjc-vip-permission-starter是一套Java web权限控制的极速版解决方案。它不需要复杂的配置项和特定的数据表支持，也不依赖臃肿的登录业务，秉持零代码的初心，大道至简，超轻量的切面处理尽显灵动。预设的标签在前端页面控制功能按钮的显隐，预设的注解在后台控制具体接口的通行，双层控制，彻底细化权限的粒度。



PS：如果你是新建或者重构版，可在它和apache shiro，spring security之间对比抉择，如果你是在项目开发到一半或者是后期的优化，想细化权限这块，强烈推荐选择wangjc-vip-permission-starter，因为它可插拔式的无缝对接会让你爱不释手。且根本不需要为了额外的代码来做引入支持，开箱即用。

## 硬性要求

### 数据表设置

权限细化控制到按钮，请求拦截到具体接口，所依赖的菜单数据表必须存在两个字段：permission、url。

![](http://www.wangjc.vip/group1/M00/00/01/rBAAD1_tLlmAWZSSAAHzeYeO3G8103.png)

url：表示该菜单项对应的后台请求地址；

permission：表示该菜单项对应的权限表达式。（不推荐重复，否则下文的权限验证将会多些操作）

### 缓存组件

考虑到分布式业务，无法使用本地缓存和session隔离，后端采用缓存中间件来解决（目前支持redis，后期考虑集成memcache），前端采用cookie来保存用户登录成功后的认证签名状态（已加密），所以缓存redis，是引用该套组件的一个前置条件，考虑到部分特殊的业务都是定制化需求，会禁用前端cookie（银行内置系统……），如此一来，则自动化集成的局限性很大，不推荐使用。正常情况很少禁用cookie的。

------

# 二、功能说明

双层权限验证，前端做功能的过滤，后端做请求的拦截：

1、用户在查看对应页面时，对于一些特定的功能按钮，比如删除，冻结……没有该项权限的话，按钮则为不可见状态，即便是手动打开调试工具，通过编辑元素的方式也无法找到它，因为它在页面解析时，默认被移除了。

2、通过页面验证，在JS层发起数据请求时（数据请求/页面跳转），对于一些特定的请求接口，没有该项权限的话，则返回权限验证失败的提示内容。

## 集成步骤

### 启动项

在启动类或者其他可扫描类上，添加注解PermissionConfigure，作为其唯一的配置项。

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(PermissionRegister.class)
public @interface PermissionConfigure {

    /**
     * 页面类型（freemarker，thymeleaf），必填项
     * @return
     */
    PermissionPageType pageType();

    /**
     * 缓存类型
     * @return
     */
    PermissionCacheType cacheType() default PermissionCacheType.REDIS;

    /**
     * 缓存的有效时间构建器
     * @return
     */
    Class<? extends AbstractCacheExpireBuilder> expireBuilder() default DefaultCacheExpireBuilder.class;

    /**
     * 权限验证错误的处理（data/view）
     * @return
     */
    Class<? extends AbstractPermissionError> errorBuilder() default DefaultPermissionError.class;
}
```

1、页面类型的后缀可能是.ftl 或者 .html，PermissionPageType为必填项；

2、缓存类型默认使用redis，后期考虑集成memcache，PermissionCacheType遵守默认项；

3、有效时间构建器，可以根据自己需求重写，继承AbstractCacheExpireBuilder，重写抽象方法即可，也可采用默认项DefaultCacheExpireBuilder（12小时，可刷新）；

4、权限验证不通过的处理（页面跳转不通过，数据请求不通过……），可以采用默认版，也可根据需求定制化，

继承AbstractPermissionError，重写data()和view()方法。

```java
public abstract class AbstractPermissionError {

    /**
     * 错误内容：数据
     * @return
     */
    public abstract Object data();

    /**
     * 错误内容：页面
     * @return
     */
    public abstract Object view();

    /**
     * 判断返回值类型
     * @param permission
     * @return
     */
    public Object error(Permission permission){
        switch (permission.request()){
            case DATA:
                return this.data();
            case VIEW:
                return this.view();
            default:
                return PermissionErrorResult.error("AbstractPermissionError decide error!please check it");
        }
    }
}
```

### 登录成功的后置

在通过自定义的登录模块登录成功后，需要添加两行权限认证代码

```java
            /** 菜单权限 */
            List<MenuButtonView> list = iMenuButtonService.getMenuButtonByUserId(userModel.getId());

            /** 权限认证服务： 根据userId，userName获取用户签名 */
            String userSign = permissionAuthService.authPermissionUser(userModel.getId(), userModel.getLoginname());

            /** 权限认证服务：设置权限菜单缓存,permission和url分别为菜单对应字段名称，内部涉及到反射 */
            permissionAuthService.authPermissionList(list,"permission","url",userSign);

```

其中，权限认证服务在组件初始化时已注册成bean，可以Autowire自动引入即可。

### 请求拦截注解

不管是数据接口，还是页面跳转，如果需要将其纳入权限控制的范畴，都需要添加注释Permission。

```java
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    /**
     * 权限表达式，必填项
     * @return
     */
    String express();

    /**
     * 非必填项：假日权限表达式不唯一的话，采用requestUrl二次验证
     * @return
     */
    String requestUrl() default "";

    /**
     * 请求类型：默认是数据接口
     * @return
     */
    PermissionRequestType request() default PermissionRequestType.DATA;
}
```

1、权限表达式为数据表对应的权限内容，必填项；

2、请求链接非必填，除非权限表达式有重复，那么requestUrl将作为二次判断，不推荐填（即不推荐express重复）；

3、请求类型(data，view)，默认为数据接口，页面跳转则改为了view。

### 页面功能按钮的显隐

后端权限不通过，则被拦截在方法执行之前，前端没有具体的功能权限，则对应的按钮不显示。双重控制。页面类型分为FREEMARKER(".ftl")，THYMELEAF(".html")。

#### html

```html
<button permission:express="permission:test/save">保存</button>
```

如上所述：HTML自定义的方言格式为：permission:express="express"，其中express是权限表达式的值，按道理，它与该按钮功能对应的后端数据接口的权限表达式是同一个，拥有该权限则显示，没有则过滤标签。

#### ftl

```html
<@permission express="activityCenter:activityDeploy/insert">
	<button class="layui-btn" lay-submit lay-filter="save">新增活动</button>
</@permission>
```

与html不同的是，ftl是采用预定义好的标签来包裹按钮，同样express是权限表达式的值，控制着标签包裹内容的显示与过滤（不解析，跳过）

------

# 三、代码简介

## 结构图

 包结构前缀统一为：vip.wangjc.permission。下面根据职责细分：注解，aop切面拦截，权限验证、检查，自动化配置、初始化，缓存服务，实体类，错误处理，页面freemarker、thymeleaf，注册器，模板，工具……其中，所有的抽象类，都允许被继承用来定制化需求。 

```java
├─src
│  ├─main
│  │  ├─java
│  │  │  └─vip
│  │  │      └─wangjc
│  │  │          └─permission
│  │  │              ├─annotation
│  │  │              │      Permission.java （注解：数据接口的权限检测）
│  │  │              │      PermissionConfigure.java （注解：权限组件的配置）
│  │  │              │
│  │  │              ├─aop
│  │  │              │      PermissionAnnotationAdvisor.java （权限的aop通知）
│  │  │              │      PermissionInterceptor.java （权限的切面拦截器）
│  │  │              │
│  │  │              ├─auth
│  │  │              │      PermissionAuthService.java （权限的认证服务,提供给登录成功后调用）
│  │  │              │      PermissionCheckService.java （权限的检测服务，提供给自身）
│  │  │              │
│  │  │              ├─auto
│  │  │              │  ├─configure
│  │  │              │  │      PermissionAutoConfiguration.java （权限的自动化配置项）
│  │  │              │  │      PermissionPageTagAutoConfiguration.java （权限页面标签的自动化配置项）
│  │  │              │  │
│  │  │              │  └─init
│  │  │              │          PermissionInit.java （初始化）
│  │  │              │
│  │  │              ├─cache
│  │  │              │  │  PermissionCacheServiceBuilder.java （权限缓存服务的构建器）
│  │  │              │  │
│  │  │              │  ├─expire
│  │  │              │  │  │  AbstractCacheExpireBuilder.java （抽象类：缓存有效时间的构建器）
│  │  │              │  │  │
│  │  │              │  │  └─rewrite
│  │  │              │  │          DefaultCacheExpireBuilder.java （默认有效时间）
│  │  │              │  │
│  │  │              │  └─service
│  │  │              │      │  PermissionCacheService.java （权限缓存服务）
│  │  │              │      │
│  │  │              │      └─impl
│  │  │              │              RedisPermissionCacheServiceImpl.java （Redis实现）
│  │  │              │
│  │  │              ├─entity
│  │  │              │      PermissionCacheType.java （枚举：指定缓存组件类型）
│  │  │              │      PermissionExpress.java （权限表达式实体）
│  │  │              │      PermissionPageType.java （枚举：权限页面类型）
│  │  │              │      PermissionRequestType.java （枚举：请求类型，data/view）
│  │  │              │      PermissionResult.java （权限验证的结果处理）
│  │  │              │      PermissionUserAuth.java （权限认证的用户）
│  │  │              │
│  │  │              ├─error
│  │  │              │  │  AbstractPermissionError.java （抽象类：权限的错误处理）
│  │  │              │  │  PermissionErrorResult.java （权限的错误结果集）
│  │  │              │  │
│  │  │              │  └─rewrite
│  │  │              │          DefaultPermissionError.java （默认的权限错误处理）
│  │  │              │
│  │  │              ├─page
│  │  │              │  │  PermissionPageTag.java （权限的页面标签）
│  │  │              │  │
│  │  │              │  ├─freemarker
│  │  │              │  │      FreemarkerConfigure.java （freemarker页面配置）
│  │  │              │  │      FreemarkerPermissionTagDirective.java （freemarker页面标签）
│  │  │              │  │
│  │  │              │  └─thymeleaf
│  │  │              │      │  ThymeleafPermissionTagDirective.java （Thymeleaf权限方言）
│  │  │              │      │
│  │  │              │      └─processor
│  │  │              │              ThymeleafPermissionProcessor.java （Thymeleaf方言解析）
│  │  │              │
│  │  │              ├─register
│  │  │              │      PermissionRegister.java （权限注册器）
│  │  │              │
│  │  │              ├─template
│  │  │              │      PermissionTemplate.java （模板方法提供）
│  │  │              │
│  │  │              └─util
│  │  │                      PermissionConstant.java （权限的常量控制：缓存key值提供）
│  │  │                      PermissionUtil.java （工具类：生成签名token，解析token）
│  │  │
│  │  └─resources
│  │      └─META-INF
│  │              spring.factories （自动化配置）
```

## UML图如下：

![](http://www.wangjc.vip/group1/M00/00/01/rBAAD1_tN9WAQ93UAANvlzkmBGc273.png)