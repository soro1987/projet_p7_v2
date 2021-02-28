package fr.soro.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.soro.security.jwt.JwtSecurityConfigurer;
import fr.soro.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/auth/signin").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers(HttpMethod.GET, "/user").permitAll()
                .antMatchers("/exemplaires/**").permitAll()               //.hasRole("ADMIN")
                .antMatchers("/bibliotheques/**").permitAll()
                .antMatchers("/ouvrages/**").permitAll()
                .antMatchers("/ouvrages/exemplairecount/**").permitAll()
//                .antMatchers("/ouvrages/**").hasRole("USER")
                .antMatchers("/ouvrages-id/**").permitAll()
                .antMatchers("/search/**").permitAll()
                .antMatchers("/category/**").permitAll()
                .antMatchers("/emprunts/expired/**").permitAll()
                .antMatchers("/emprunts/user/expired/**").permitAll()
                .antMatchers("/emprunts/").hasRole("ADMIN")
                .antMatchers("/emprunts/delete/**").permitAll()
                .antMatchers("/emprunts-prolongation/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/emprunts-user/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()

        /*"/swagger-resources/**"
        "/v2/api-docs/**"*/
                //.antMatchers("/emprunts/**").hasRole("USER")
                .anyRequest().authenticated()
            .and()
            .apply(new JwtSecurityConfigurer(jwtTokenProvider));
        //@formatter:on
        

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
}

