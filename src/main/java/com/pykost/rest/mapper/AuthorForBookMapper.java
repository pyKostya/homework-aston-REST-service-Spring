package com.pykost.rest.mapper;

import com.pykost.rest.dto.AuthorForBookDTO;
import com.pykost.rest.entity.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface AuthorForBookMapper {
    AuthorEntity toEntity(AuthorForBookDTO authorDTO);

    AuthorForBookDTO toDTO(AuthorEntity author);
}
