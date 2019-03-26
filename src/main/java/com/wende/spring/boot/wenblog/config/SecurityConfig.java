package com.wende.spring.boot.wenblog.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/user/loginpage").permitAll().and()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**","/img/**", "/editormd/**","/layui/**","/upload/**","/test.md").permitAll()//静态资源
                .antMatchers("/user/register","/user/index","/user/login","/user/authUserId").permitAll()//登录注册主页不拦截
                .antMatchers("/article/getall","/article/byUUID","/article/like").permitAll()
                .antMatchers("/check/**").permitAll()
                .antMatchers("/rest/**").permitAll()//restApi走token认证
                .antMatchers("/user/**").hasRole("active")
                .anyRequest().authenticated()
                .and().headers().frameOptions().disable()
                .and().csrf().ignoringAntMatchers("/user/upload/photo/editorMD","/user/upload/photo/layEdit");
    }


}
