package com.schefer.agenda.service;

import com.schefer.agenda.dto.MateriaDTO;
import com.schefer.agenda.dto.MateriaRequestDTO;
import com.schefer.agenda.model.Materia;
import com.schefer.agenda.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {

    private final MateriaRepository repository;

    public MateriaService(MateriaRepository repository) {
        this.repository = repository;
    }

    private List<MateriaDTO> converteDados(List<Materia> materia) {
        return materia.stream()
                .map(m -> new MateriaDTO(m.getId(), m.getMateria()))
                .toList();
    }

    public List<MateriaDTO> exibirMateria() {
        return converteDados(repository.findAll());
    }

    public MateriaDTO salvarMateria(MateriaRequestDTO dto) {
        Materia materia = new Materia(dto.materia());
        Materia salva = repository.save(materia);
        return new MateriaDTO(salva.getId(), salva.getMateria());
    }
}
