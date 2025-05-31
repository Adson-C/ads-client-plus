package com.ads.clien.plus.configuration;

import com.ads.clien.plus.filter.TokenAuthenticationFilter;
import com.ads.clien.plus.repository.jpa.UserDetailsRepository;
import com.ads.clien.plus.service.TokenService;
import com.ads.clien.plus.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    private static final String[] SWAGGER_WHITELIST = {
            "/v1/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/swagger-resources/**"
    };

//    @Value("${swagger.auth.username}")
//    private String swaggerUsername;
//
//    @Value("${swagger.auth.password}")
//    private String swaggerPassword;
//
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    //responsável pela configuração de autorizacao -> Acesso a URL's
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(SWAGGER_WHITELIST).permitAll() // Permite acesso ao Swagger
                .antMatchers(HttpMethod.GET, "/subscriptions-type").permitAll()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.POST, "/payment/process").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers( "/auth/recovery-code/*").permitAll()
//                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").authenticated() // Protege o Swagger
                .anyRequest().authenticated()
//                .and()
//                .formLogin().disable()
//                .httpBasic()  Habilita autenticação básica para o Swagger
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenService, userDetailsRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configura usuário em memória para acessar o Swagger
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username(swaggerUsername) // seu usuário para o Swagger
//                .password(swaggerPassword) // sua senha para o Swagger
//                .roles("SWAGGER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
}
