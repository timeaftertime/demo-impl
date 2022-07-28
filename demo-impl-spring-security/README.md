# 依赖
* spring-boot-starter
* spring-boot-starter-security
* spring-boot-starter-web
* javax.servlet-api
  
  
  
# 步骤
1. @EnableWebSecurity
2. 添加 WebSecurityConfigurer
3. 在 WebSecurityConfigurer.configure(AuthenticationManagerBuilder auth) 自定义 AuthenticationProvider
4. 在 WebSecurityConfigurer.configure(HttpSecurity http) 自定义请求拦截规则、登录登出页面、请求处理页面等


   
# 请求处理流程
1. 用户的请求被封装为 Authentication 的实现类
    * 如登录时为 UsernamePasswordAuthenticationToken , 其中封装了 username 和 password
2. 通过 WebSecurityConfigurerAdapter 中配置的 AuthenticationProvider 进行身份校验和授权
    * 验证步骤即调用 AuthenticationProvider 的 authenticate，参数为用户输入封装成的 Authentication，不同 AuthenticationProvider 实现类代表不同验证方式，例如：
        * 用户名密码       AbstractUserDetailsAuthenticationProvider
        * “记住我”       RememberMeAuthenticationProvider



# AbstractUserDetailsAuthenticationProvider
AuthenticationProvider 的一种实现，对应用户名、密码方式验证。<br>
authenticate(Authentication authentication) 主要做以下事：
1. 获取用户输入的用户名
2. 尝试根据用户名从缓存中获取 UserDetails
3. 若缓存中没有，调用模板方法 retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) 获取
4. 断言 UserDetails 不为 null
5. 依次调用 preAuthenticationChecks.check 、additionalAuthenticationChecks 、postAuthenticationChecks.check
    * preAuthenticationChecks 和 postAuthenticationChecks 可通过 setter 进行设置
    * additionalAuthenticationChecks 为模板方法
        * 三个方法都可抛出 AuthenticationException 表示验证失败（如密码错误）
6. 若用户不来自缓存，将其放入缓存
7. 返回验证成功结果
        
        
# DaoAuthenticationProvider
AbstractUserDetailsAuthenticationProvider 的实现，通过 UserDetailsService 进行验证
* 构造时必须通过 getUserDetailsService 设置 UserDetailsService ，否则将断言失败
* 不能使用 @Autowired 注解一个  UserDetailsService 成员然后在构造方法设置，因为 @Autowired 自动注入是在构造完之后，构造时为 null
* 实现了抽象模板方法 retrieveUser ，从 UserDetailsService 中获取 UserDetails 并返回
* 实现了抽象模板方法 additionalAuthenticationChecks 验证 Authentication 中的密码经过编码后是否与 UserDetails 中的密码相等
    * 必须 setPasswordEncoder(PasswordEncoder passwordEncoder) 设置密码编码方式
* 可通过继承 DaoAuthenticationProvider 并重写 additionalAuthenticationChecks 实现自己的验证逻辑
