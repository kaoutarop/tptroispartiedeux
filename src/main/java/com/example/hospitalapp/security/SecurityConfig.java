package com.example.hospitalapp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.HttpSecurityDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

//@Bean
public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
    return new JdbcUserDetailsManager(dataSource);
}
    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode("1234");
        System.out.println(encodedPassword);
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(encodedPassword).roles("USER").build(),
                User.withUsername("user2").password(encodedPassword).roles("USER").build(),
                User.withUsername("admin").password(encodedPassword).roles("USER", "ADMIN").build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

       httpSecurity.formLogin(Customizer.withDefaults());

        //httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll();
        //httpSecurity.rememberMe();
        httpSecurity.authorizeHttpRequests(ar -> ar.requestMatchers("/webjars/**", "h2-console").permitAll());
        //httpSecurity.authorizeHttpRequests(ar -> ar.requestMatchers("/user/**").hasRole("USER"));
        //httpSecurity.authorizeHttpRequests(ar -> ar.requestMatchers("/admin/**").hasRole("ADMIN"));
        httpSecurity.authorizeHttpRequests(ar -> ar.anyRequest().authenticated());
        httpSecurity.exceptionHandling(ar -> ar.accessDeniedPage("/notAuthorized"));
httpSecurity.userDetailsService(userDetailsService);
        return httpSecurity.build();
        //return httpSecurity
        //  .formLogin()
        // .authorizeHttpRequests(ar->ar.requestMatchers("/delete/**").hasRole("ADMIN"))
        //  .authorizeHttpRequests(ar->ar.requestMatchers("/admin/**").hasRole("ADMIN"))
        //   .authorizeHttpRequests(ar->ar.requestMatchers("/user/**").hasRole("USER"))
        //  .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
        // .build();
    }

}
