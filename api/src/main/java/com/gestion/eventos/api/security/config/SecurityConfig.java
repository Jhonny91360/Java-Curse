package com.gestion.eventos.api.security.config;

import com.gestion.eventos.api.security.jwt.JwtAuthEntryPoint;
import com.gestion.eventos.api.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indica que esta clase contiene configuraciones de Beans para Spring
@RequiredArgsConstructor // Crea el constructor para inyectar userDetailsService automáticamente
@EnableMethodSecurity // Habilitar validacion personalizada para metodos http
public class SecurityConfig {

    // Inyectamos el servicio que creamos antes (donde estaba el Collection<? extends ...>)
    private final UserDetailsService userDetailsService;

    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Define el "Filtro de Seguridad". Es como una aduana por la que pasan todas las peticiones.
     */
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitamos CSRF (Cross-Site Request Forgery).
                // Común en APIs REST que usan tokens o Basic Auth y no cookies/sesiones de navegador.
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling( exception ->
                        exception.authenticationEntryPoint(jwtAuthEntryPoint)
                        )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                // 2. Definimos REGLAS de autorización.
                .authorizeHttpRequests( auth ->
                        auth   // para habilitar la ruta del login
                                .requestMatchers("/api/v1/auth/**").permitAll()
                               // .requestMatchers(HttpMethod.GET, "/api/v1/events/**").permitAll()
                                // Por ahora, CUALQUIER petición (anyRequest) debe estar autenticada.
                                // Nadie entra si no está logueado.
                                .anyRequest().authenticated()
                );

                // 3. Activamos la Autenticación Básica.
                // Es el popup del navegador o el header "Authorization: Basic base64(user:pass)".
                // .httpBasic(Customizer.withDefaults());

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // Construimos y devolvemos la cadena de filtros configurada.
        return http.build();
    }

    /**
     * Define CÓMO se van a encriptar las contraseñas.
     * BCrypt es el estándar actual (genera un hash seguro con salt).
     */
    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    /**
     * El "Director de Autenticación". Es el objeto encargado de coordinar
     * el proceso de login (recibe credenciales, busca al usuario, verifica contraseña).
     */
    @Bean
    public AuthenticationManager authenticationManager
    (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Spring ya tiene una configuración por defecto, simplemente la exponemos como Bean.
        return authenticationConfiguration.getAuthenticationManager();
    }
}