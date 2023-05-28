package org.example.web.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = Logger.getLogger(AppContextConfig.class);

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("populate in memory auth user");
        auth
                .inMemoryAuthentication()
                .withUser("root")
                .password(passwordEncoder().encode("111"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("config http security");
        http
                .csrf().disable() //Отключили защиту CSRF ()
                .authorizeRequests() //Начало настройки авторизации запросов
                .antMatchers("/login**").permitAll() //Разрешили не авторизованным пользователям переходить на страницу логина
                .anyRequest().authenticated() //Настройка авторизации для остальных страниц
                .and()
                .formLogin() //Настройка формы логина
                .loginPage("/login") //Здесь указывается сама страница с формой логина
                .loginProcessingUrl("/login/auth") //Здесь указывается URL для обработки запроса на логин.
                .defaultSuccessUrl("/books/shelf", true) //Перенаправление при успешной авторизации
                .failureUrl("/login"); //Перенаправление при не успешной авторизации
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        logger.info("config web security");
        web
                .ignoring() //Игнорирование запросов
                .antMatchers("/images/**"); //Игнорирование всех запросов с текущим паттерном
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
