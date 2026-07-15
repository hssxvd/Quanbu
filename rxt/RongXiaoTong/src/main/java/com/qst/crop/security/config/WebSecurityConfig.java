package com.qst.crop.security.config;

import com.qst.crop.security.filter.CustomAuthenticationFilter;
import com.qst.crop.security.filter.JwtAuthenticationTokenFilter;
import com.qst.crop.security.filter.WebSecurityCorsFilter;
import com.qst.crop.security.handler.EntryPointUnauthorizedHandler;
import com.qst.crop.security.handler.MyAuthenticationFailureHandler;
import com.qst.crop.security.handler.MyAuthenticationSuccessHandler;
import com.qst.crop.security.handler.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomAuthenticationFilter customAuthenticationFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/user/login",
                                "/user/register",
                                "/swagger-ui.html#/**",
                                "/swagger-ui.html/**",
                                "/user/add/**",
                                "/order/goods/**",
                                "/order/All/**",
                                "/order/needs/**",
                                "/order/selectById/**",
                                "/order/searchGoodsByKeys/**",
                                "/order/searchNeedsByKeys/**",
                                "/order/searchAllByKeys/**",
                                "/knowledge/**",
                                "/paySuccessful/**",
                                "/file/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(entryPointUnauthorizedHandler)
                        .accessDeniedHandler(restAccessDeniedHandler)
                )
                .addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    @Bean
    CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManager authenticationManager) {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/user/login");
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
}
