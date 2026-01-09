package com.gestion.eventos.api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventRequestDto {

    @NotBlank(message="El nombre del evento no puede estar vacio")
    private String name;

    @NotNull(message = "La fecha no puede ser nula")
    @FutureOrPresent(message = "El evento no puede tener une fecha pasada")
    private LocalDate date;

    @NotBlank(message="La locacion del evento no puede estar vacia")
    private String location;

}
