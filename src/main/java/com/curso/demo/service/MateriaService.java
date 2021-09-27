package com.curso.demo.service;

import java.util.List;
import java.util.Optional;

import com.curso.demo.dto.MateriaDto;
import com.curso.demo.exception.MateriaException;
import com.curso.demo.model.Materia;
import com.curso.demo.repository.MateriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "materia")
public class MateriaService implements InterfaceMateriaService {

    private MateriaRepository materiaRepository;
    private ModelMapper mapper;

    @Autowired
    public MateriaService(MateriaRepository repository) {
        this.materiaRepository = repository;
        this.mapper = new ModelMapper();
    }

    @Override
    // nao precisaria do materia nos outros caches
    @CachePut(value = "materia", key = "#materia.id")
    public Boolean atualizar(MateriaDto materia) {
        try {
            Materia materiaSalvar = mapper.map(materia, Materia.class);
            materiaRepository.save(materiaSalvar);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean excluir(Long id) {
        try {
            Optional<Materia> materiaBuscada = materiaRepository.findById(id);
            if (materiaBuscada.isPresent()) {
                materiaRepository.delete(materiaBuscada.get());
                return true;
            }
            throw new MateriaException("Matéria não encontrada", HttpStatus.NOT_FOUND);
        } catch (MateriaException e) {
            throw e;
        } catch (Exception e) {
            throw new MateriaException("Erro no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @CachePut(unless = "#result.size() < 3")
    public List<Materia> listarMaterias() {
        return materiaRepository.findAll();
    }

    @Override
    @CachePut(value = "materia", key = "#id")
    public Materia consultarMateria(Long id) {
        try {
            Optional<Materia> materiaBuscada = materiaRepository.findById(id);
            if (materiaBuscada.isPresent()) {
                return materiaBuscada.get();
            }
            throw new MateriaException("Matéria não encontrada", HttpStatus.NOT_FOUND);
        } catch (MateriaException e) {
            throw e;
        } catch (Exception e) {
            throw new MateriaException("Erro no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    // exclui cache
    @CacheEvict(allEntries = true)
    public Boolean cadastrarMateria(MateriaDto materia) {
        try {
            Materia materiaSalvar = mapper.map(materia, Materia.class);
            materiaRepository.save(materiaSalvar);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
