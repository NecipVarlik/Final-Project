package com.necipvarlik.final_project.configuration;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.necipvarlik.final_project.dto.MyUserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final UserDetailsService service;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeRequests(auth -> auth
						.antMatchers("/").permitAll()
						.antMatchers("/registrate").permitAll()
						.antMatchers("/userlist").permitAll()
						.antMatchers("/uploads/*").permitAll() // Upload dosyasına yükleme yapılabilmesi için gereken izin
						.anyRequest().authenticated()
						)
//				.formLogin(Customizer.withDefaults())
				.formLogin()
				.loginPage("/login").permitAll()
				.loginProcessingUrl("/dologin").permitAll()
				.failureUrl("/login?error=true").permitAll()
				.successHandler(new AuthenticationSuccessHandler() {
					
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						
						MyUserPrincipal u = (MyUserPrincipal)authentication.getPrincipal();
						
						log.info("{} , {} sisteme giriş yaptı.", u.getUsername(), LocalDateTime.now());
						
						response.sendRedirect(request.getContextPath());
						
					}
				})
				
				.and()
				.logout(logout-> logout.logoutUrl("/logout").logoutSuccessUrl("/login")
						.addLogoutHandler(new LogoutHandler() {
							
							@Override
							public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
								
								MyUserPrincipal u = (MyUserPrincipal)authentication.getPrincipal();
								
								if(authentication != null) {
									log.info("{} , {} sistemden çıkış yaptı.",u.getUsername(), LocalDateTime.now());
								}
								
							}
						})
						.invalidateHttpSession(true)
						)
				.userDetailsService(service)
				.exceptionHandling()
				.accessDeniedPage("/accessdenied")
				.and() 
				.build();
				
				
				
				
	}

	@Bean
	PasswordEncoder encoder() {

		return new BCryptPasswordEncoder();
//		return NoOpPasswordEncoder.getInstance();

	}

}
