package com.pykost.rest.mapper;

import com.pykost.rest.dto.AuthorDTO;
import com.pykost.rest.dto.AuthorForBookDTO;
import com.pykost.rest.dto.BookDTO;
import com.pykost.rest.entity.AuthorEntity;
import com.pykost.rest.entity.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorMapperTest {
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private AuthorMapperImpl authorMapper;

    private AuthorEntity authorEntity;
    private AuthorDTO authorDTO;
    private BookEntity bookEntity;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        AuthorEntity author = new AuthorEntity(3L, "Roderick Johnson");
        bookEntity = new BookEntity(1L, "Effective Java", "Programming book", author);
        authorEntity = new AuthorEntity(1L, "Joshua Bloch", List.of(bookEntity));

        AuthorForBookDTO authorForBookDTO = new AuthorForBookDTO(3L, "Roderick Johnson");
        bookDTO = new BookDTO(1L, "Effective Java", "Programming book", authorForBookDTO);
        authorDTO = new AuthorDTO(1L, "Joshua Bloch", List.of(bookDTO));
    }
    @Test
    void toDTO() {
        doReturn(bookDTO).when(bookMapper).toDTO(bookEntity);

        AuthorDTO expected = authorMapper.toDTO(authorEntity);

        assertThat(expected.getId()).isEqualTo(authorEntity.getId());
        assertThat(expected.getName()).isEqualTo(authorEntity.getName());
    }

    @Test
    void toEntity() {
        doReturn(bookEntity).when(bookMapper).toEntity(bookDTO);

        AuthorEntity expected = authorMapper.toEntity(authorDTO);

        assertThat(expected.getId()).isEqualTo(authorEntity.getId());
        assertThat(expected.getName()).isEqualTo(authorEntity.getName());
    }
}