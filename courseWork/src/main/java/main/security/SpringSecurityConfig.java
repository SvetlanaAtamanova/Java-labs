package main.security;

import main.security.jwt.JwtSecurityConfig;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/signin").permitAll()
                .antMatchers(HttpMethod.GET, "/api/journal").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/api/clients").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/api/books").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/api/bookTypes").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/api/journal").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/books").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/bookTypes").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/journal/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/clients/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/books/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/bookTypes/{id}").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider));
    }
}
