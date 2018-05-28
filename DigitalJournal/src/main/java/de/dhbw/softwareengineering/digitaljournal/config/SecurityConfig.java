package de.dhbw.softwareengineering.digitaljournal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.sql.DataSource;
import java.util.LinkedHashMap;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    public SecurityConfig(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint delegatingEntryPoint() {
        final LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> map = new LinkedHashMap();
        map.put(new AntPathRequestMatcher("/"), new LoginUrlAuthenticationEntryPoint("/unauthorized"));

        final DelegatingAuthenticationEntryPoint entryPoint = new DelegatingAuthenticationEntryPoint(map);
        entryPoint.setDefaultEntryPoint(new LoginUrlAuthenticationEntryPoint("/unauthorized"));

        return entryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(delegatingEntryPoint());

        http.authorizeRequests()
                .antMatchers("/", "/register", "/confirmemail/**", "/recoverpassword/**", "/unauthorized", "/error").permitAll()
                .antMatchers("/scss/**", "/css/**", "/img/**", "/js/**", "/fonts/**", "/res/**").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginProcessingUrl("/login")
                        .loginPage("/")
                        .defaultSuccessUrl("/journal", true)
                        .failureUrl("/login-error")
                        .permitAll()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(getPasswordEncoder());
    }
}
