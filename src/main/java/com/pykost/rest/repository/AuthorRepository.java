package com.pykost.rest.repository;

import com.pykost.rest.entity.AuthorEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorRepository extends ListCrudRepository<AuthorEntity, Long> {

}
