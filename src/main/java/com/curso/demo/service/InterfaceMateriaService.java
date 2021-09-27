package com.curso.demo.service;

import java.util.List;

import com.curso.demo.dto.MateriaDto;
import com.curso.demo.model.Materia;

public interface InterfaceMateriaService {

    public List<Materia> listarMaterias();

    public Materia consultarMateria(Long id);

    public Boolean cadastrarMateria(MateriaDto materia);

    public Boolean atualizar(MateriaDto materia);

    public Boolean excluir(Long id);
}