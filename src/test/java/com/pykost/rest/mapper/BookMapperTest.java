package com.pykost.rest.mapper;

import com.pykost.rest.dto.AuthorForBookDTO;
import com.pykost.rest.dto.BookDTO;
import com.pykost.rest.entity.AuthorEntity;
import com.pykost.rest.entity.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {
    private BookMapper bookMapper;
    private BookEntity bookEntity;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapperImpl();

        AuthorEntity author = new AuthorEntity(1L, "Author");

        bookEntity = new BookEntity(1L, "Book", "Description", author);

        AuthorForBookDTO authorForBookDTO = new AuthorForBookDTO(1L,"Author");

        bookDTO = new BookDTO(1L, "Book", "Description", authorForBookDTO);
    }

    @Test
    void toDTO() {
        BookDTO expected = bookMapper.toDTO(bookEntity);

        assertThat(bookDTO.getId()).isEqualTo(expected.getId());
        assertThat(bookDTO.getName()).isEqualTo(expected.getName());
        assertThat(bookDTO.getDescription()).isEqualTo(expected.getDescription());
        assertThat(bookDTO.getAuthor()).isEqualTo(expected.getAuthor());
    }

    @Test
    void toEntity() {
        BookEntity expected = bookMapper.toEntity(bookDTO);

        assertThat(bookEntity.getId()).isEqualTo(expected.getId());
        assertThat(bookEntity.getName()).isEqualTo(expected.getName());
        assertThat(bookEntity.getDescription()).isEqualTo(expected.getDescription());
        assertThat(bookEntity.getAuthor()).isEqualTo(expected.getAuthor());

    }
}