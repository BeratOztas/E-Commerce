package com.beratoztas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.beratoztas.security.JwtAuthenticationEntryPoint;
import com.beratoztas.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private JwtAuthenticationEntryPoint handler;

	public SecurityConfig(JwtAuthenticationEntryPoint handler) {
		this.handler = handler;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		// Şu an için tüm isteklere izin veriyoruz
		httpSecurity.csrf()
		.disable() // CSRF korumasını devre dışı bırakıyoruz, ancak production ortamında aktif  olmalı
				.authorizeRequests()	
				.anyRequest()
				.permitAll() // Şu an için tüm isteklere izin veriyoruz
				.and()
				.exceptionHandling().authenticationEntryPoint(handler); // Hata yönetimi, oturum açılmamış
																				// kullanıcılar için

		// JWT filter'ını Spring Security'ye ekliyoruz
		httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

}
