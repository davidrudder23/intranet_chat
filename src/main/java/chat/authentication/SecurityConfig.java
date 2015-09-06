package chat.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    
    private String getUserQuery() {
    	return "SELECT username , password, true from account where username = ?"; 
    }
    
    private String getAuthoritiesQuery() {
    	return "SELECT username, 'USER' from account where username = ?";
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//            .withUser("user").password("password").roles("USER")
//            .and()
//            .withUser("chat").password("chat").roles("USER");
    	
    	auth.jdbcAuthentication()
    		.passwordEncoder(passwordEncoder)
    		.dataSource(dataSource)
    		 .usersByUsernameQuery(getUserQuery())
             .authoritiesByUsernameQuery(getAuthoritiesQuery());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        	.authorizeRequests()
                .antMatchers("/registration", "/register", "/css/**", "/fonts/**", "/image/**", "/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login").permitAll()
                .successHandler(authenticationSuccessHandler)
                .and()
            .logout()                                    
                .logoutUrl("/logout").permitAll();

    }

}
