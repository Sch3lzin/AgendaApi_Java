package com.schefer.agenda.service;

import com.schefer.agenda.dto.MateriaDTO;
import com.schefer.agenda.dto.MateriaRequestDTO;
import com.schefer.agenda.dto.ProfDTO;
import com.schefer.agenda.model.Materia;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository repository;

    private List<MateriaDTO> converteDados(List<Materia> materia) {
        return materia.stream()
                .map(m -> new MateriaDTO(
                        m.getId(),
                        m.getMateria()
                ))
                .collect(Collectors.toList());
    }

    public List<MateriaDTO> exibirMateria() {
        return converteDados(repository.findAll());
    }

    public Materia salvarMateria(MateriaRequestDTO dto) {
        Materia materia = new Materia(dto.materia());
        return repository.save(materia);
    }
}
