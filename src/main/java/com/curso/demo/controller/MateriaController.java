package com.curso.demo.controller;

import java.util.List;

import javax.validation.Valid;

import com.curso.demo.config.SwaggerConfig;
import com.curso.demo.dto.MateriaDto;
import com.curso.demo.model.Materia;
import com.curso.demo.model.Response;
import com.curso.demo.service.InterfaceMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = SwaggerConfig.MATERIA)
@RestController
@RequestMapping("/materia")
public class MateriaController {

    @Autowired
    private InterfaceMateriaService materiaService;

    @ApiOperation(value = "Listar matérias")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Matérias listadas com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno do serviço"), })
    @GetMapping
    public ResponseEntity<List<Materia>> listarMaterias() {
        return ResponseEntity.status(HttpStatus.OK).body(materiaService.listarMaterias());
    }

    @ApiOperation(value = "Buscar matéria")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Matéria buscada com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno do serviço"), })
    @GetMapping("/{id}")
    public ResponseEntity<Response<Materia>> consultaMateria(@PathVariable("id") Long id) {
        Response<Materia> response = new Response<>();
        response.setData(materiaService.consultarMateria(id));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultaMateria(id))
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Cadastrar uma nova materia")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Entidade criada com sucesso"),
            @ApiResponse(code = 400, message = "Erro na requisição"),
            @ApiResponse(code = 500, message = "Erro interno do serviço"), })
    @PostMapping
    public ResponseEntity<Boolean> cadastrarMaterias(@Valid @RequestBody MateriaDto materia) {
        try {
            materiaService.cadastrarMateria(materia);
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @ApiOperation(value = "Editar uma nova matéria")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Entidade editada com sucesso"),
            @ApiResponse(code = 400, message = "Erro na requisição"),
            @ApiResponse(code = 500, message = "Erro interno do serviço"), })
    @PutMapping
    public ResponseEntity<Boolean> atualizarMateria(@Valid @RequestBody MateriaDto materia) {
        try {
            materiaService.atualizar(materia);
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

}
