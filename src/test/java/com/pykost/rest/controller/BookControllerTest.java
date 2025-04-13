package com.pykost.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pykost.rest.dto.AuthorForBookDTO;
import com.pykost.rest.dto.BookDTO;
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
class BookControllerTest {
    private MockMvc mockMvc;
    @Mock
    private Service<BookDTO, Long> service;
    @InjectMocks
    private BookController controller;

    private final ObjectMapper objMapper = new ObjectMapper();

    private BookDTO book1;
    private BookDTO book2;
    private AuthorForBookDTO author;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        author = new AuthorForBookDTO(1L, "Фёдор Достоевский");
        book1 = new BookDTO(1L, "Преступление и наказание", "Описание книги", author);
        book2 = new BookDTO(2L, "Идиот", "Описание книги", author);
    }

    @Test
    void getAllBooks() throws Exception {
        List<BookDTO> bookList = List.of(book1, book2);

        doReturn(bookList).when(service).getAll();

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(book1.getId()))
                .andExpect(jsonPath("$[0].name").value(book1.getName()))
                .andExpect(jsonPath("$[0].description").value(book1.getDescription()))
                .andExpect(jsonPath("$[0].author.id").value(author.getId()))
                .andExpect(jsonPath("$[0].author.name").value(author.getName()))
                .andExpect(jsonPath("$[1].id").value(book2.getId()))
                .andExpect(jsonPath("$[1].name").value(book2.getName()))
                .andExpect(jsonPath("$[1].description").value(book2.getDescription()))
                .andExpect(jsonPath("$[1].author.id").value(author.getId()))
                .andExpect(jsonPath("$[1].author.name").value(author.getName()));

        verify(service, times(1)).getAll();
    }

    @Test
    void getBooks() throws Exception {
        doReturn(Optional.of(book1)).when(service).getById(book1.getId());

        mockMvc.perform(get("/api/books/{id}", book1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book1.getId()))
                .andExpect(jsonPath("$.name").value(book1.getName()))
                .andExpect(jsonPath("$.description").value(book1.getDescription()))
                .andExpect(jsonPath("$.author.id").value(author.getId()))
                .andExpect(jsonPath("$.author.name").value(author.getName()));

        verify(service, times(1)).getById(book1.getId());
    }

    @Test
    void addNewBook() throws Exception {
        doReturn(book1).when(service).create(book1);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper.writeValueAsString(book1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book1.getId()))
                .andExpect(jsonPath("$.name").value(book1.getName()))
                .andExpect(jsonPath("$.description").value(book1.getDescription()))
                .andExpect(jsonPath("$.author.id").value(author.getId()))
                .andExpect(jsonPath("$.author.name").value(author.getName()));

        verify(service, times(1)).create(book1);
    }

    @Test
    void updateBook() throws Exception {
        BookDTO upBook = new BookDTO(1L, "Новое название", "Новое описание", author);

        doReturn(upBook).when(service).update(upBook.getId(), upBook);

        mockMvc.perform(put("/api/books/{id}", upBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper.writeValueAsString(upBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(upBook.getId()))
                .andExpect(jsonPath("$.name").value(upBook.getName()))
                .andExpect(jsonPath("$.description").value(upBook.getDescription()))
                .andExpect(jsonPath("$.author.id").value(author.getId()))
                .andExpect(jsonPath("$.author.name").value(author.getName()));

        verify(service, times(1)).update(upBook.getId(), upBook);
    }

    @Test
    void deleteBook() throws Exception {
        doNothing().when(service).delete(book1.getId());

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(book1.getId());
    }

    @Test
    void deleteBook_WhenAuthorNotExists_ShouldReturn404() throws Exception {
        doThrow(new NoSuchException("Book not found")).when(service).delete(10L);

        mockMvc.perform(delete("/api/books/10"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).delete(10L);
    }
}