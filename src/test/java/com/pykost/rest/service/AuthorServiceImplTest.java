package com.pykost.rest.service;

import com.pykost.rest.dto.AuthorDTO;
import com.pykost.rest.entity.AuthorEntity;
import com.pykost.rest.mapper.AuthorMapper;
import com.pykost.rest.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private AuthorEntity authorEntity;
    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        authorEntity = new AuthorEntity(1L, "Author");

        authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("Author");
    }

    @Test
    void create() {
        when(authorMapper.toEntity(authorDTO)).thenReturn(authorEntity);
        when(authorRepository.save(authorEntity)).thenReturn(authorEntity);
        when(authorMapper.toDTO(authorEntity)).thenReturn(authorDTO);

        AuthorDTO expectedAuthor = authorService.create(authorDTO);

        assertNotNull(expectedAuthor);
        assertEquals(expectedAuthor.getId(), authorDTO.getId());
        assertEquals(expectedAuthor.getName(), authorDTO.getName());

        verify(authorMapper, times(1)).toEntity(authorDTO);
        verify(authorRepository, times(1)).save(authorEntity);
        verify(authorMapper, times(1)).toDTO(authorEntity);
    }

    @Test
    void getById() {

    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void getAll() {
    }
}