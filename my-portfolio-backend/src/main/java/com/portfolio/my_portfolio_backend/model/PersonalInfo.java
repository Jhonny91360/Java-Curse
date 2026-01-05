package com.portfolio.my_portfolio_backend.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfo {
    private Long id; // Clave primaria

    @NotBlank(message = "El nombre no puede estar vacio")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacio")
    private String lastName;

    @NotBlank(message = "Debe tener un titulo")
    private String title; // Ej: "Full Stack Developer"

    @NotBlank(message = "La descripcion no puede estar vacio")
    private String profileDescription; // El texto largo del "Who am I?"

    @NotBlank(message = "El imagen no puede estar vacia")
    private String profileImageUrl; // URL o ruta a la imagen de perfil

    @Min(value=0, message = "Los annios de experiencia no pueden ser negativos")
    private Integer yearsOfExperience;

    @Email( message = "El email no es valido")
    private String email;

    @NotBlank(message = "El telefono no puede estar vacio")
    private String phone;

    @URL(message = "El enlace a linkedin no puede estar vacio")
    private String linkedinUrl;

    @URL(message = "El enlace a github no puede estar vacio")
    private String githubUrl;
}
