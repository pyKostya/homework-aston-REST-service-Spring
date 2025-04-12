package com.pykost.rest.repository;

import com.pykost.rest.entity.BookEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ListCrudRepository<BookEntity, Long> {}
