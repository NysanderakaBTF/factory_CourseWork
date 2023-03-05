package com.fox.factory.service.mappers;

import com.fox.factory.entities.Comment;
import com.fox.factory.entities.dto.CommentsDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface CommentMapper {
    Comment toEntity(CommentsDto commentsDto);

    CommentsDto toDto(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate(CommentsDto commentsDto, @MappingTarget Comment comment);

    Comment toEntity1(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate1(Comment comment, @MappingTarget Comment comment);
}