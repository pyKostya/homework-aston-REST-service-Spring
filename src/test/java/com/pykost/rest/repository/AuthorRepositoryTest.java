package com.pykost.rest.repository;


import com.pykost.rest.configuration.JpaConfiguration;
import com.pykost.rest.entity.AuthorEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringJUnitConfig(JpaConfiguration.class)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;
    private AuthorEntity author1;
    private AuthorEntity author2;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:17-alpine")
            .withInitScript("schema.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        author1 = new AuthorEntity(null, "Roderick Johnson", new ArrayList<>());
        author2 = new AuthorEntity(null, "Joshua Bloch", new ArrayList<>());

        repository.save(author1);
        repository.save(author2);
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("db.url", postgres::getJdbcUrl);
        registry.add("db.username", postgres::getUsername);
        registry.add("db.password", postgres::getPassword);
    }

    @Test
    void findAll() {
        List<AuthorEntity> list = repository.findAll();

        assertThat(list).hasSize(2)
                .contains(author1)
                .contains(author2);
    }

    @Test
    void findById() {
        Optional<AuthorEntity> authorEntity = repository.findById(author2.getId());

        assertThat(authorEntity).isPresent();
        assertThat(author2.getId()).isEqualTo(authorEntity.get().getId());
        assertThat(authorEntity.get().getName()).isEqualTo(author2.getName());
    }

    @Test
    void save() {
        AuthorEntity author = new AuthorEntity(null, "Лев Толстой", new ArrayList<>());

        AuthorEntity save = repository.save(author);
        Optional<AuthorEntity> authorEntity = repository.findById(author.getId());

        assertThat(authorEntity).isPresent();
        assertThat(save.getId()).isEqualTo(authorEntity.get().getId());
        assertThat(authorEntity.get().getName()).isEqualTo(author.getName());
    }

    @Test
    void update() {
        String newName = "Фёдор Достоевский";

        AuthorEntity author = new AuthorEntity(null, "Лев Толстой", new ArrayList<>());
        AuthorEntity saved = repository.save(author);

        author.setName(newName);
        AuthorEntity save = repository.save(author);

        assertThat(saved.getId()).isEqualTo(save.getId());
        assertThat(save.getName()).isEqualTo(newName);
    }

    @Test
    void delete() {
        List<AuthorEntity> list = repository.findAll();
        assertThat(list).hasSize(2);

        repository.deleteById(author1.getId());
        List<AuthorEntity> list2 = repository.findAll();

        assertThat(list2).hasSize(1);
    }


}

