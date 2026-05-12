package com.schefer.agenda.controller;

import com.schefer.agenda.dto.MateriaDTO;
import com.schefer.agenda.model.Materia;
import com.schefer.agenda.service.MateriaService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MateriaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MateriaService materiaService;

    @Test
    @WithMockUser
    void DeveraRetornarStatus200ParaExibirMateria() throws Exception {

        // act
        var response = mvc.perform(get("/materia"))
                .andExpect(status().isOk()).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus201ParaCriarMateria() throws Exception {

        // arrange
        String json = """
            {
                "materia": "teste"
            }
            """;

        when(materiaService.salvarMateria(any()))
                .thenReturn(new MateriaDTO(1L, "teste"));

        // act
        var response = mvc.perform(
                post("/materia")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus400ParaCriarMateria() throws Exception {
        // arrange
        String json = "{}";

        // act
        var response = mvc.perform(
                post("/materia")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus200ParaDeletarMateria() throws Exception {

        // arrange
        Long id = 1L;

        when(materiaService.deletarMateria(any()))
                .thenReturn(ResponseEntity.ok("Materia deletada com sucesso"));

        // act
        var response = mvc.perform(
                delete("/materia/" + id)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus404ParaDeletarMateriaNaoEcontrada() throws Exception {

        // arrange
        Long id = 999L;

        when(materiaService.deletarMateria(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Materia não encontrada!"));

        // act
        var response = mvc.perform(
                delete("/materia/" + id)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus200ParaAtualizarMateria() throws Exception {

        // arrange
        Long id = 1L;
        String json = """
            {
              "materia": "teste"
            }
            """;

        when(materiaService.atualizarMateria(any(), any()))
                .thenReturn(ResponseEntity.ok("Materia atualizada com sucesso!"));

        // act
        var response = mvc.perform(
                put("/materia/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus400ParaAtualizarMateria() throws Exception {
        // arrange
        Long id = 1L;
        String json = "{}";

        // act
        var response = mvc.perform(
                put("/materia/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "SECRETARIO")
    void DeveraRetornarStatus404ParaAtualizarMateriaNaoEncontrada() throws Exception {

        // arrange
        Long id = 999L;
        String json = """
            {
              "materia": "teste"
            }
            """;

        when(materiaService.atualizarMateria(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Materia não encontrada!"));

        // act
        var response = mvc.perform(
                put("/materia/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        Assertions.assertEquals(404, response.getStatus());
    }
}