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
    private final VerificarDados verificarDados;

    public MateriaService(MateriaRepository repository, VerificarDados verificarDados) {
        this.repository = repository;
        this.verificarDados = verificarDados;
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
        VerificarDados.DadosVerificarMateria dados = verificarDados.verificarMateria(dto);

        Materia materia = new Materia(dados.materia().getMateria());
        Materia salva = repository.save(materia);
        return new MateriaDTO(salva.getId(), salva.getMateria());
    }
}
