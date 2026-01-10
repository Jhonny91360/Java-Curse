package com.gestion.eventos.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Indica que esta clase contiene configuraciones de Beans para Spring
@RequiredArgsConstructor // Crea el constructor para inyectar userDetailsService automáticamente
public class SecurityConfig {

    // Inyectamos el servicio que creamos antes (donde estaba el Collection<? extends ...>)
    private final UserDetailsService userDetailsService;

    /**
     * Define el "Filtro de Seguridad". Es como una aduana por la que pasan todas las peticiones.
     */
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitamos CSRF (Cross-Site Request Forgery).
                // Común en APIs REST que usan tokens o Basic Auth y no cookies/sesiones de navegador.
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Definimos REGLAS de autorización.
                .authorizeHttpRequests( auth ->
                        // Por ahora, CUALQUIER petición (anyRequest) debe estar autenticada.
                        // Nadie entra si no está logueado.
                        auth.anyRequest().authenticated()
                )

                // 3. Activamos la Autenticación Básica.
                // Es el popup del navegador o el header "Authorization: Basic base64(user:pass)".
                .httpBasic(Customizer.withDefaults());

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