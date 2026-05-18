package com.schefer.agenda.controller;

import com.schefer.agenda.dto.ProfDTO;
import com.schefer.agenda.service.ProfessorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfessorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProfessorService professorService;

    @Test
    @WithMockUser
    void DeveraRetornarStatus200ParaExibirProfessor() throws Exception {

        // act
        var response = mvc.perform(get("/professor"))
                .andExpect(status().isOk()).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus201ParaCriarProfessor() throws Exception {

        // arrange
        String json = """
                {
                    "name": "teste",
                    "senha": "teste",
                    "permissao": "USUARIO"
                }
                """;

        when(professorService.salvarProfessor(any()))
                .thenReturn(new ProfDTO(1L, "teste"));

        // act
        var response = mvc.perform(
                post("/professor")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus400ParaCriarProfessor() throws Exception {

        // arrange
        String json = "{}";

        // act
        var response = mvc.perform(
                post("/professor")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus200ParaDeletarProfessor() throws Exception {

        // arrange
        Long id = 1L;

        when(professorService.deletarProfessor(any()))
                .thenReturn(ResponseEntity.ok("Professor deletado com sucesso"));
        // act
        var response = mvc.perform(
                delete("/professor/" + id)).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus404ParaDeletarProfessorNaoEncontrado() throws Exception{

        // arrange
        Long id = 999L;

        when(professorService.deletarProfessor(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encontrado"));

        // act
        var response = mvc.perform(
                delete("/professor/" + id)).andReturn().getResponse();

        //assert
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus200ParaAtualizarProfessor() throws Exception {

        // arrenge
        Long id = 1L;
        String json = """
                {
                    "name": "teste",
                    "senha": "teste",
                    "permissao": "USUARIO"
                }
                """;

        when(professorService.atualizarProfessor(any(), any()))
                .thenReturn(ResponseEntity.ok("Professor atualizado com sucesso!"));

        // act
        var response = mvc.perform(
                put("/professor/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus400ParaAtualizarProfessor() throws Exception {

        // arrenge
        Long id = 1L;
        String json = "{}";

        // act
        var response = mvc.perform(
                put("/professor/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus404ParaAtualizarProfessorNaoEncontrado() throws Exception {

        // arrenge
        Long id = 999L;
        String json = """
                {
                    "name": "teste",
                    "senha": "teste",
                    "permissao": "USUARIO"
                }
                """;

        when(professorService.atualizarProfessor(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encontrado"));

        // act
        var response = mvc.perform(
                put("/professor/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "USUARIO")
    void DeveraRetornarStatus200ParaAtualizarNomeProfessor() throws Exception {

        // arrange
        Long id = 1L;
        String json = """
                {
                    "name": "teste"
                }
                """;

        when(professorService.atualizarNomeProfessor(any(), any()))
                .thenReturn(ResponseEntity.ok("Professor atualizado com sucesso!"));

        // act
        var response = mvc.perform(
                patch("/professor/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "USUARIO")
    void DeveraRetornarStatus400ParaAtualizarNomeProfessor() throws Exception {

        // arrange
        Long id = 1L;
        String json = "{}";

        // act
        var response = mvc.perform(
                patch("/professor/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "USUARIO")
    void DeveraRetornarStatus404ParaAtualizarNomeProfessorNaoEncontrado() throws Exception {

        // arrange
        Long id = 999L;
        String json = """
                {
                    "name": "teste"
                }
                """;

        when(professorService.atualizarNomeProfessor(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encontrado"));

        // act
        var response = mvc.perform(
                patch("/professor/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(404, response.getStatus());
    }
}