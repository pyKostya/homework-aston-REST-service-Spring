package com.pykost.rest.service;

import com.pykost.rest.dto.AuthorForBookDTO;
import com.pykost.rest.dto.BookDTO;
import com.pykost.rest.entity.AuthorEntity;
import com.pykost.rest.entity.BookEntity;
import com.pykost.rest.mapper.BookMapper;
import com.pykost.rest.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository repository;
    @Mock
    private BookMapper mapper;
    @InjectMocks
    private BookServiceImpl service;

    private BookEntity bookEntity;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        AuthorEntity authorEntity = new AuthorEntity(1L, "Author");
        bookEntity = new BookEntity(1L, "Book", "Description", authorEntity);

        AuthorForBookDTO authorForBookDTO = new AuthorForBookDTO();
        authorForBookDTO.setId(1L);
        authorForBookDTO.setName("Book");

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setName("Book");
        bookDTO.setDescription("Description");
        bookDTO.setAuthor(authorForBookDTO);
    }

    @Test
    void create() {
        doReturn(bookEntity).when(mapper).toEntity(bookDTO);
        doReturn(bookEntity).when(repository).save(bookEntity);
        doReturn(bookDTO).when(mapper).toDTO(bookEntity);

        BookDTO expectedBook = service.create(bookDTO);

        assertNotNull(expectedBook);
        assertThat(expectedBook.getId()).isEqualTo(bookDTO.getId());
        assertThat(expectedBook.getName()).isEqualTo(bookDTO.getName());
        assertThat(expectedBook.getDescription()).isEqualTo(bookDTO.getDescription());
        assertThat(expectedBook.getAuthor()).isEqualTo(bookDTO.getAuthor());

        verify(mapper, times(1)).toEntity(bookDTO);
        verify(repository, times(1)).save(bookEntity);
        verify(mapper, times(1)).toDTO(bookEntity);
    }

    @Test
    void getById() {
        doReturn(Optional.of(bookEntity)).when(repository).findById(bookEntity.getId());
        doReturn(bookDTO).when(mapper).toDTO(bookEntity);

        Optional<BookDTO> expectedBook = service.getById(bookDTO.getId());

        assertThat(expectedBook).isPresent();
        assertThat(expectedBook.get().getId()).isEqualTo(bookDTO.getId());
        assertThat(expectedBook.get().getName()).isEqualTo(bookDTO.getName());
        assertThat(expectedBook.get().getDescription()).isEqualTo(bookDTO.getDescription());
        assertThat(expectedBook.get().getAuthor()).isEqualTo(bookDTO.getAuthor());

        verify(repository, times(1)).findById(bookEntity.getId());
        verify(mapper, times(1)).toDTO(bookEntity);
    }

    @Test
    void delete() {
        service.delete(bookDTO.getId());

        verify(repository, times(1)).deleteById(bookEntity.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deleteThrowExceptionIfIdNotFound() {
        Long invalidId = 10L;

        doThrow(new IllegalArgumentException())
                .when(repository)
                .deleteById(invalidId);

        assertThrows(IllegalArgumentException.class, () -> service.delete(invalidId));
    }

    @Test
    void update() {
        Long id = 10L;

        doReturn(bookEntity).when(mapper).toEntity(bookDTO);
        doReturn(bookEntity).when(repository).save(bookEntity);
        doReturn(bookDTO).when(mapper).toDTO(bookEntity);

        BookDTO expectedBook = service.update(id, bookDTO);

        assertNotNull(expectedBook);
        assertThat(expectedBook.getId()).isEqualTo(bookDTO.getId());
        assertThat(expectedBook.getName()).isEqualTo(bookDTO.getName());
        assertThat(expectedBook.getDescription()).isEqualTo(bookDTO.getDescription());
        assertThat(expectedBook.getAuthor()).isEqualTo(bookDTO.getAuthor());

        verify(mapper, times(1)).toEntity(bookDTO);
        verify(repository, times(1)).save(bookEntity);
        verify(mapper, times(1)).toDTO(bookEntity);
    }

    @Test
    void getAll() {
        List<BookEntity> entityList = List.of(bookEntity);
        List<BookDTO> dtoList = List.of(bookDTO);

        doReturn(bookDTO).when(mapper).toDTO(bookEntity);
        doReturn(entityList).when(repository).findAll();

        List<BookDTO> expectedList = service.getAll();

        assertThat(expectedList).containsAll(dtoList);
        verify(mapper, times(1)).toDTO(bookEntity);
        verify(repository, times(1)).findAll();
    }
}