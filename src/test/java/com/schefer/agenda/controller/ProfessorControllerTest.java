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
                    "name": "teste"
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
}