package com.pykost.rest.mapper;

import com.pykost.rest.dto.AuthorForBookDTO;
import com.pykost.rest.dto.BookDTO;
import com.pykost.rest.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(uses = AuthorForBookDTO.class,componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface BookMapper {

    BookDTO toDTO(BookEntity book);

    BookEntity toEntity(BookDTO bookDto);

}
