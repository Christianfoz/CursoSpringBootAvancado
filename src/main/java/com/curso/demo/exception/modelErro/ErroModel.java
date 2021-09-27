package com.curso.demo.exception.modelErro;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErroModel {
    private String mensagem;
    private HttpStatus httpStatus;
    private Long timeStamp;
}
