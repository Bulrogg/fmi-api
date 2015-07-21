package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableWebMvcSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class ApiConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    .withUser("api")
                    .password("password")
                    .roles("USER");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .antMatcher("/v1/**")
                    .authorizeRequests().anyRequest()
                    .permitAll();
            //.fullyAuthenticated().and().httpBasic();
        }

    }

    @Configuration
    @Order(2)
    public static class WebConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    .withUser("doc")
                    .password("password")
                    .roles("DOC");
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/css/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/api-docs**").hasRole("DOC")
                    .antMatchers("/documentation/**").hasRole("DOC")
                    .anyRequest().permitAll();
        }

    }


}

