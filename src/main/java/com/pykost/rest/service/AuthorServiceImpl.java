package com.pykost.rest.service;

import com.pykost.rest.repository.AuthorRepository;
import com.pykost.rest.dto.AuthorDTO;
import com.pykost.rest.entity.AuthorEntity;
import com.pykost.rest.mapper.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class AuthorServiceImpl implements Service<AuthorDTO, Long> {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorDAO, AuthorMapper authorMapper) {
        this.authorRepository = authorDAO;
        this.authorMapper = authorMapper;
    }

    @Override
    @Transactional
    public AuthorDTO create(AuthorDTO authorDTO) {
        AuthorEntity entity = authorMapper.toEntity(authorDTO);
        AuthorEntity save = authorRepository.save(entity);
        return authorMapper.toDTO(save);
    }

    @Override
    @Transactional
    public Optional<AuthorDTO> getById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDTO);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AuthorDTO update(Long id, AuthorDTO authorDTO) {
        AuthorEntity entity = authorMapper.toEntity(authorDTO);
        entity.setId(id);
        AuthorEntity authorEntity = authorRepository.save(entity);
        return authorMapper.toDTO(authorEntity);
    }

    @Override
    @Transactional
    public List<AuthorDTO> getAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDTO)
                .toList();
    }

}
