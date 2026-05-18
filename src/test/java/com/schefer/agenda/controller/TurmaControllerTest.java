package com.schefer.agenda.controller;

import com.schefer.agenda.dto.TurmaDTO;
import com.schefer.agenda.enums.TipoPeriodo;
import com.schefer.agenda.service.TurmaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class TurmaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TurmaService turmaService;

    @Test
    @WithMockUser
    void DeveraRetornarStatus200ParaExibirTurmas() throws Exception {

        // act
        var response = mvc.perform(
                get("/turma")
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus201ParaCriarTurma() throws Exception {

        // arrange
        String json = """
                {
                    "periodo": "MATUTINO",
                    "serie": 1,
                    "turma": 1 
                }
                """;

        when(turmaService.salvarTurma(any()))
                .thenReturn(new TurmaDTO(1L, TipoPeriodo.MATUTINO, 1, 1));

        // act
        var response = mvc.perform(
                post("/turma")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus400ParaCriarTurma() throws Exception {

        // arrange
        String json = """
                {
                }
                """;

        // act
        var response = mvc.perform(
                post("/turma")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus200ParaDeletarTurma() throws Exception {

        // arrange
        Long id = 1L;

        when(turmaService.deletarTurma(any()))
                .thenReturn(ResponseEntity.ok("Turma deletado com sucesso"));

        // act
        var response = mvc.perform(
                delete("/turma/" + id)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus404ParaDeletarTurmaNaoEncontrada() throws Exception {

        // arrange
        Long id = 1L;

        when(turmaService.deletarTurma(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrado"));

        // act
        var response = mvc.perform(
                delete("/turma/" + id)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus200ParaAtualizarTurma() throws Exception {

        // arrange
        Long id = 1L;
        String json = """
                {
                    "periodo": "MATUTINO",
                    "serie": 1,
                    "turma": 1 
                }
                """;

        when(turmaService.atualizarTurma(any(), any()))
                .thenReturn(ResponseEntity.ok("Turma atualizada com sucesso!"));

        // act
        var response = mvc.perform(
                put("/turma/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus400ParaAtualizarTurma() throws Exception {

        // arrange
        Long id = 1L;
        String json = """
                {
                }
                """;

        // act
        var response = mvc.perform(
                put("/turma/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus404ParaAtualizarTurmaParaNaoEncontrado() throws Exception {

        // arrange
        Long id = 999L;
        String json = """
                {
                    "periodo": "MATUTINO",
                    "serie": 1,
                    "turma": 1 
                }
                """;

        when(turmaService.atualizarTurma(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada!"));

        // act
        var response = mvc.perform(
                put("/turma/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(404, response.getStatus());
    }
}