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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {
    @Mock
    private AuthorRepository repository;
    @Mock
    private AuthorMapper mapper;
    @InjectMocks
    private AuthorServiceImpl service;

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
        doReturn(authorEntity).when(mapper).toEntity(authorDTO);
        doReturn(authorEntity).when(repository).save(authorEntity);
        doReturn(authorDTO).when(mapper).toDTO(authorEntity);

        AuthorDTO expectedAuthor = service.create(authorDTO);

        assertNotNull(expectedAuthor);
        assertThat(expectedAuthor.getId()).isEqualTo(authorDTO.getId());
        assertThat(expectedAuthor.getName()).isEqualTo(authorDTO.getName());

        verify(mapper, times(1)).toEntity(authorDTO);
        verify(repository, times(1)).save(authorEntity);
        verify(mapper, times(1)).toDTO(authorEntity);
    }

    @Test
    void getById() {
        doReturn(Optional.of(authorEntity)).when(repository).findById(authorEntity.getId());
        doReturn(authorDTO).when(mapper).toDTO(authorEntity);

        Optional<AuthorDTO> expectedAuthor = service.getById(authorDTO.getId());

        assertThat(expectedAuthor).isPresent();
        assertThat(expectedAuthor.get().getId()).isEqualTo(authorDTO.getId());
        assertThat(expectedAuthor.get().getName()).isEqualTo(authorDTO.getName());

        verify(mapper, times(1)).toDTO(authorEntity);
        verify(repository, times(1)).findById(authorEntity.getId());
    }

    @Test
    void delete() {
        service.delete(authorDTO.getId());

        verify(repository, times(1)).deleteById(authorEntity.getId());
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
        Long id = 1L;

        doReturn(authorEntity).when(mapper).toEntity(authorDTO);
        doReturn(authorEntity).when(repository).save(authorEntity);
        doReturn(authorDTO).when(mapper).toDTO(authorEntity);


        AuthorDTO expectedAuthor = service.update(id, authorDTO);

        assertNotNull(expectedAuthor);
        assertThat(expectedAuthor.getId()).isEqualTo(authorDTO.getId());
        assertThat(expectedAuthor.getName()).isEqualTo(authorDTO.getName());

        verify(mapper, times(1)).toEntity(authorDTO);
        verify(repository, times(1)).save(authorEntity);
        verify(mapper, times(1)).toDTO(authorEntity);
    }

    @Test
    void getAll() {
        List<AuthorEntity> entityList = List.of(authorEntity);
        List<AuthorDTO> actualDtoList = List.of(authorDTO);

        doReturn(authorDTO).when(mapper).toDTO(authorEntity);
        doReturn(entityList).when(repository).findAll();

        List<AuthorDTO> expectedList = service.getAll();

        assertThat(expectedList).containsAll(actualDtoList);
        verify(mapper, times(1)).toDTO(authorEntity);
        verify(repository, times(1)).findAll();
    }
}