package com.pykost.rest.controller;

import com.pykost.rest.dto.AuthorDTO;
import com.pykost.rest.exception.NoSuchException;
import com.pykost.rest.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthorController {
    private final Service<AuthorDTO, Long> service;

    @Autowired
    public AuthorController(Service<AuthorDTO, Long> authorService) {
        this.service = authorService;
    }

    @GetMapping("/authors")
    public List<AuthorDTO> getAllAuthors() {
        return service.getAll();
    }

    @GetMapping("/authors/{id}")
    public AuthorDTO getAuthor(@PathVariable("id") Long id) {
        return service.getById(id)
                .orElseThrow(() -> new NoSuchException("Author not found with id: " + id));
    }

    @PostMapping("/authors")
    public AuthorDTO addNewAuthor(@RequestBody AuthorDTO authorDTO){
        return service.create(authorDTO);
    }

    @PutMapping("/authors/{id}")
    public AuthorDTO updateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDTO authorDTO){
        return service.update(id, authorDTO);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
