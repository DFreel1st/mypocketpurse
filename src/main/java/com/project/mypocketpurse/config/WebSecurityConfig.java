package com.project.mypocketpurse.config;

import com.project.mypocketpurse.service.userdetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private CustomUserDetailsService customUserDetails;

    public WebSecurityConfig(CustomUserDetailsService customUserDetails) {
        this.customUserDetails = customUserDetails;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpFirewall allowPercent() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedPercent(true);
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                // Доступ для всех пользователей
                .antMatchers("/login", "/user/registration", "/user/remember-password", "/user/change-password/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/resources/**",
                        "/static/**",
                        "/assets/**",
                        "/css/**",
                        "/js/**",
                        "/image/**",
                        "/webjars/**")
                .permitAll()
                // Все остальные страницы требуют авторизации
                .anyRequest().authenticated()
                .and()
                // Настройка для входа в систему
                .formLogin()
                .loginPage("/login")
                // Перенаправление на главную страницу после успешног входа
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login");
        return http.build();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(customUserDetails).passwordEncoder(bCryptPasswordEncoder());
    }
}
