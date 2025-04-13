package com.pykost.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pykost.rest.dto.AuthorDTO;
import com.pykost.rest.exception.NoSuchException;
import com.pykost.rest.service.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {
    private MockMvc mockMvc;
    @Mock
    private Service<AuthorDTO, Long> service;
    @InjectMocks
    private AuthorController controller;

    private final ObjectMapper objMapper = new ObjectMapper();

    private AuthorDTO author1;
    private AuthorDTO author2;
    private AuthorDTO updAuthor;

    @BeforeEach
    void seUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        author1 = new AuthorDTO();
        author1.setId(1L);
        author1.setName("Лев Толстой");

        author2 = new AuthorDTO();
        author2.setId(2L);
        author2.setName("Фёдор Достоевский");

        updAuthor = new AuthorDTO();
        updAuthor.setId(1L);
        updAuthor.setName("Лев Николаевич Толстой");

    }

    @Test
    void getAllAuthors() throws Exception {
        List<AuthorDTO> authorsList = List.of(author1, author2);
        doReturn(authorsList).when(service).getAll();

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(author1.getId()))
                .andExpect(jsonPath("$[0].name").value(author1.getName()))
                .andExpect(jsonPath("$[1].id").value(author2.getId()))
                .andExpect(jsonPath("$[1].name").value(author2.getName()));

        verify(service, times(1)).getAll();
    }

    @Test
    void getAuthor() throws Exception {
        doReturn(Optional.of(author1)).when(service).getById(author1.getId());

        mockMvc.perform(get("/api/authors/{id}", author1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(author1.getId()))
                .andExpect(jsonPath("$.name").value(author1.getName()));

        verify(service, times(1)).getById(author1.getId());
    }

    @Test
    void addNewAuthor() throws Exception {
        doReturn(author1).when(service).create(author1);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper.writeValueAsString(author1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(author1.getId()))
                .andExpect(jsonPath("$.name").value(author1.getName()));

        verify(service, times(1)).create(author1);
    }

    @Test
    void updateAuthor() throws Exception {
        doReturn(updAuthor).when(service).update(updAuthor.getId(), updAuthor);

        mockMvc.perform(put("/api/authors/{id}", updAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper.writeValueAsString(updAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(author1.getId()))
                .andExpect(jsonPath("$.name").value(updAuthor.getName()));

        verify(service, times(1)).update(1L, updAuthor);
    }

    @Test
    void deleteAuthor() throws Exception {
        doNothing().when(service).delete(author1.getId());

        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(author1.getId());
    }

    @Test
    void deleteAuthor_WhenAuthorNotExists_ShouldReturn404() throws Exception {
        doThrow(new NoSuchException("Author not found")).when(service).delete(10L);

        mockMvc.perform(delete("/api/authors/10"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).delete(10L);
    }
}