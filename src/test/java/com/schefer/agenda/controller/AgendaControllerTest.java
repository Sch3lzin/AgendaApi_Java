package com.schefer.agenda.controller;

import com.schefer.agenda.service.AgendaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AgendaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AgendaService agendaService;

    @Test
    @WithMockUser
    void DeveraRetornarStatus200ParaAgendaInformatica() throws Exception {

        // act
        var response = mvc.perform(get("/agenda/informatica"))
                .andExpect(status().isOk()).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser
    void DeveraRetornarStatus200ParaAgendaAuditorio() throws Exception {

        // act
        var response = mvc.perform(get("/agenda/auditorio"))
                .andExpect(status().isOk()).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser
    void DeveraRetornarStatus200ParaAgendaTablet() throws Exception {

        // act
        var response = mvc.perform(get("/agenda/tablet"))
                .andExpect(status().isOk()).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser
    void DeveraRetornarStatus201ParaCriarAgendamento() throws Exception {

        // arrange
        String json = """
            {
              "turmaId": 1,
              "materiaId": 1,
              "professorId": 1,
              "tipoAula": "AULA_1",
              "tipoAgenda": "SALA_INFORMATICA",
              "tipoPeriodo": "MATUTINO",
              "data": "9999-12-25",
              "observacao": "Aula de reforço"
            }
            """;

        when(agendaService.salvarAgendamento(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body("Agendamento cadastrado com sucesso!"));

        // act
        var response = mvc.perform(
                post("/agenda")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    @WithMockUser
    void DeveraRetornarStatus400ParaCriarAgendamento() throws Exception {

        // arrange
        String json = "{}";

        // act
        var response = mvc.perform(
                post("/agenda")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser
    void DeveraRetornarStatus200ParaDeletarAgendamento() throws Exception {

        // arrange
        Long id = 1L;

        when(agendaService.deletarAgendamento(any()))
                .thenReturn(ResponseEntity.ok("Agendamento deletado com sucesso!"));

        // act
        var response = mvc.perform(
                delete("/agenda/" + id)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser
    void DeveraRetornarStatus404ParaDeletarAgendamentoNaoEncontrado() throws Exception {

        // arrange
        Long id = 999L;

        when(agendaService.deletarAgendamento(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamento não encontrado!"));

        // act
        var response = mvc.perform(
                delete("/agenda/" + id)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    void DeveraRetornarStatus200ParaAtualizarAgendamento() throws Exception {

        // arrange
        Long id = 1L;
        String json = """
            {
              "turmaId": 1,
              "materiaId": 1,
              "professorId": 1,
              "tipoAula": "AULA_1",
              "tipoAgenda": "SALA_INFORMATICA",
              "tipoPeriodo": "MATUTINO",
              "data": "9999-12-25",
              "observacao": "Aula de reforço"
            }
            """;

        when(agendaService.atualizarAgendamento(any(), any()))
                .thenReturn(ResponseEntity.ok("Agendamento atualizado com sucesso!"));

        // act
        var response = mvc.perform(
                put("/agenda/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser
    void DeveraRetornarStatus400ParaAtualizarAgendamento() throws Exception {

        // arrange
        Long id = 1L;
        String json = "{}";

        // act
        var response = mvc.perform(
                put("/agenda/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser
    void DeveraRetornarStatus404ParaAtualizarAgendamentoNaoEncontrado() throws Exception {

        // arrange
        Long id = 999L;
        String json = """
            {
              "turmaId": 1,
              "materiaId": 1,
              "professorId": 1,
              "tipoAula": "AULA_1",
              "tipoAgenda": "SALA_INFORMATICA",
              "tipoPeriodo": "MATUTINO",
              "data": "9999-12-25",
              "observacao": "Aula de reforço"
            }
            """;

        when(agendaService.atualizarAgendamento(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamento não encontrado!"));

        // act
        var response = mvc.perform(
                put("/agenda/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(404, response.getStatus());
    }
}