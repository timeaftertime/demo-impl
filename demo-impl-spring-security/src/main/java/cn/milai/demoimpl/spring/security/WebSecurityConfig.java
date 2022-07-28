package cn.milai.demoimpl.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationProvider authProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 设置用于校验用户名密码的 AuthenticationProvider
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// 关闭 csrf，否则需要在 header 传递 csrf token
			.csrf()
			.disable()

			// 表单设置
			.formLogin()
			.loginPage("/myLogin") // 自定义未登录时跳转到的 url
			.loginProcessingUrl("/login") // 自定义接受登录请求的 url，处理 handler 由 spring-security 自动生成
			.and()

			// url 对应的访问规则配置
			.authorizeRequests()
			// 登录页面和登录处理 url 对所有人开放
			.antMatchers("/myLogin", "/login")
			.permitAll()
			// 其他只对完成鉴权的用户开发
			.anyRequest()
			.authenticated();
	}

}
