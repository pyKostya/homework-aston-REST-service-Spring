package com.pykost.rest.service;

import com.pykost.rest.repository.BookRepository;
import com.pykost.rest.dto.BookDTO;
import com.pykost.rest.entity.BookEntity;
import com.pykost.rest.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class BookServiceImpl implements Service<BookDTO, Long> {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public Optional<BookDTO> getById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO);
    }

    @Override
    @Transactional
    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        BookEntity entity = bookMapper.toEntity(bookDTO);
        BookEntity save = bookRepository.save(entity);
        return bookMapper.toDTO(save);
    }

    @Override
    @Transactional
    public BookDTO update(Long id, BookDTO bookDTO) {
        BookEntity entity = bookMapper.toEntity(bookDTO);
        entity.setId(id);
        BookEntity bookEntity = bookRepository.save(entity);
        return bookMapper.toDTO(bookEntity);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        boolean existsById = bookRepository.existsById(id);
        bookRepository.deleteById(id);
       return existsById;
    }




}
