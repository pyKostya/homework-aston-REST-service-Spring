package com.pykost.rest.mapper;

import com.pykost.rest.dto.AuthorDTO;
import com.pykost.rest.entity.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;


@Mapper(uses = BookMapper.class ,componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface AuthorMapper {
    AuthorDTO toDTO(AuthorEntity author);

    AuthorEntity toEntity(AuthorDTO authorDto);
}
