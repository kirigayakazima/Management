package com.qym.config;

import com.qym.util.MD5Utils;
import com.qym.util.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.provider.MD5;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //注意,不加上这个,就没办法使用iframe
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("manager")
                .antMatchers("/admin/index").hasRole("manager")
                .antMatchers("/admin/kind").hasRole("manager")
                .antMatchers("/admin/kind-input").hasRole("manager")
                .antMatchers("/admin/product").hasRole("manager")
                .antMatchers("/admin/product-input").hasRole("manager");
    //没有权限重定向到login
      http.formLogin().loginPage("/toLogin");

        http.csrf().disable();
    //注销
        http.logout().deleteCookies().invalidateHttpSession(true).logoutSuccessUrl("/");
    }
    //认证
    //There is no PasswordEncoder mapped for the id "null",密码编码错误,版本错误
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
        .withUser("xuaner").password(new BCryptPasswordEncoder().encode("123456")).roles("manager");
//        auth.jdbcAuthentication().dataSource(dataSource)
//        .usersByUsernameQuery("select username,password,true from user where username=?")
//        .authoritiesByUsernameQuery("select username,authority from user where username=?");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();
    }
}
