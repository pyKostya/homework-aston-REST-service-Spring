package com.pykost.rest.controller;

import com.pykost.rest.dto.BookDTO;
import com.pykost.rest.exception.NoSuchException;
import com.pykost.rest.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    private final Service<BookDTO, Long> service;

    @Autowired
    public BookController(Service<BookDTO, Long> service) {
        this.service = service;
    }

    @GetMapping("/books")
    public List<BookDTO> getAllBooks() {
        return service.getAll();
    }

    @GetMapping("/books/{id}")
    public BookDTO getBooks(@PathVariable("id") Long id) {
        return service.getById(id)
                .orElseThrow(() -> new NoSuchException("Book not found with id: " + id));
    }

    @PostMapping("books")
    public BookDTO addNewBook(@RequestBody BookDTO bookDTO) {
        return service.create(bookDTO);
    }

    @PutMapping("/books/{id}")
    public BookDTO updateBook(@PathVariable("id") Long id, @RequestBody BookDTO bookDTO) {
        return service.update(id, bookDTO);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
