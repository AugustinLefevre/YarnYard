package com.yarnyard.user_service.configurations.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringSecurityConfig {

   // @Autowired
   // private CustomUserDetailsService customUserDetailsService;
//
   // @Bean
   // public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
   //     AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
   //     authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
   //     return authenticationManagerBuilder.build();
   // }
//
   // @Bean
   // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
   // {
   //     return http.authorizeHttpRequests(auth -> {
   //         //auth.requestMatchers(Roles.ADMIN.getRequestMatcher()).hasRole(Roles.ADMIN.getName());
   //         auth.requestMatchers("/user").hasRole("USER");
   //         auth.anyRequest().authenticated();
   //     }).formLogin(Customizer.withDefaults()).build();
   // }
//
   // @Bean
   // public BCryptPasswordEncoder passwordEncoder() {
   //     return new BCryptPasswordEncoder();
   // }
}