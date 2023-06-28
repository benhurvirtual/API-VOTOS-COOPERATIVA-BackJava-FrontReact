package br.com.meta.apivotoscooperativa.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public record DadosPauta(
    @Positive
    Long id,

    @NotBlank
    @Length(min = 5, max = 15, message = "O titulo deve ter entre 5 e 10 caracteres")
    @Pattern(regexp = "[a-zA-Z0-9 ]*$", message = "O titulo não deve conter caracteres especiais")
    String titulo,
    @Length(min = 5, max = 30, message = "A descricao deve ter entre 5 e 30 caracteres")
    @Pattern(regexp = "[a-zA-Z0-9 ]*$", message = "A descrição não deve conter caracteres especiais")
    @NotBlank
    String descricao)

     {
}
