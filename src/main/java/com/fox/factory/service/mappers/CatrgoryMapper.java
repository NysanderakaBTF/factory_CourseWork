package com.fox.factory.service.mappers;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.entities.Comment;
import com.fox.factory.entities.dto.CommentCreateDto;
import com.fox.factory.entities.dto.CatrgoryDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface CatrgoryMapper {
    Catrgory toEntity(CatrgoryDto catrgoryDto);

    CatrgoryDto toDto(Catrgory catrgory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Catrgory partialUpdate(CatrgoryDto catrgoryDto, @MappingTarget Catrgory catrgory);

}