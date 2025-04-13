package com.pykost.rest.mapper;


import com.pykost.rest.dto.AuthorForBookDTO;
import com.pykost.rest.entity.AuthorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorForBookMapperTest {

    private AuthorForBookMapper authorMapper;
    private AuthorEntity authorEntity;
    private AuthorForBookDTO authorDTO;

    @BeforeEach
    void setUp() {
        authorMapper = new AuthorForBookMapperImpl();

        authorEntity = new AuthorEntity(1L, "Author");

        authorDTO = new AuthorForBookDTO(1L, "Author");
    }
    @Test
    void toDTO() {
        AuthorForBookDTO expected = authorMapper.toDTO(authorEntity);

        assertThat(authorDTO.getId()).isEqualTo(expected.getId());
        assertThat(authorDTO.getName()).isEqualTo(expected.getName());
    }

    @Test
    void toEntity() {
        AuthorEntity expected = authorMapper.toEntity(authorDTO);

        assertThat(authorEntity.getId()).isEqualTo(expected.getId());
        assertThat(authorEntity.getName()).isEqualTo(expected.getName());
    }
}