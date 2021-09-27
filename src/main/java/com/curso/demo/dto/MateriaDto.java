package com.curso.demo.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MateriaDto {
    private Long id;
    @NotBlank(message = "Insira o nome da matéria")
    private String nomeMateria;
    @Min(value = 20, message = "Deve ter mais de 20 horas")
    private int horas;
    private String codigo;
    private int frequencia;
}