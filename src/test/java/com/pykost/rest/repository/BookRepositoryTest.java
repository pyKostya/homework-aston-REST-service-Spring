package com.pykost.rest.repository;

import com.pykost.rest.configuration.JpaConfiguration;
import com.pykost.rest.entity.AuthorEntity;
import com.pykost.rest.entity.BookEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(JpaConfiguration.class)
@Transactional
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    BookEntity book1;
    BookEntity book2;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:17-alpine")
            .withInitScript("schema.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        AuthorEntity author = new AuthorEntity(null, "Герберт Шилдт", new ArrayList<>());
        authorRepository.save(author);
        book1 = new BookEntity(null, "Java", "Базовые концепции языка", author);
        book2 = new BookEntity(null, "Java 2", "Основы нового платформно-независимого ООП", author);


        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("testcontainers.url", postgres::getJdbcUrl);
        registry.add("testcontainers.username", postgres::getUsername);
        registry.add("testcontainers.password", postgres::getPassword);
    }

    @Test
    void findAll() {
        List<BookEntity> list = bookRepository.findAll();

        assertThat(list).hasSize(2)
                .contains(book1)
                .contains(book2);
    }

    @Test
    void findById() {
        Optional<BookEntity> bookEntity = bookRepository.findById(book2.getId());

        assertThat(bookEntity).isPresent();
        assertThat(bookEntity.get().getName()).isEqualTo(book2.getName());
    }

    @Test
    void save() {
        BookEntity book =
                new BookEntity(null, "Доступный UNIX", "описани UNIXподобных ОС",
                        new AuthorEntity(null, "Алексей Федорчук", new ArrayList<>()));

        bookRepository.save(book);
        Optional<BookEntity> bookEntity = bookRepository.findById(book.getId());

        assertThat(bookEntity).isPresent();
        assertThat(bookEntity.get().getName()).isEqualTo(book.getName());
        assertThat(bookEntity.get().getDescription()).isEqualTo(book.getDescription());
    }

    @Test
    void update() {
        String newName = "Доступный unix";

        BookEntity book =
                new BookEntity(null, "Доступный UNIX", "описание UNIXподобных ОС",
                        new AuthorEntity(null, "Алексей Федорчук", new ArrayList<>()));
        BookEntity saved = bookRepository.save(book);

        book.setName(newName);
        BookEntity save = bookRepository.save(book);

        assertThat(save.getId()).isEqualTo(saved.getId());
        assertThat(save.getName()).isEqualTo(newName);
        assertThat(save.getDescription()).isEqualTo(book.getDescription());
        assertThat(save.getAuthor().getName()).isEqualTo(book.getAuthor().getName());
    }

    @Test
    void delete() {
        List<BookEntity> listAll = bookRepository.findAll();
        assertThat(listAll).hasSize(2);

        bookRepository.deleteById(book2.getId());
        List<BookEntity> resultDelete = bookRepository.findAll();

        assertThat(resultDelete).hasSize(1);
    }


}