package com.fox.factory.service.mappers;

import com.fox.factory.entities.Comment;
import com.fox.factory.entities.dto.SubCommentDto;
import com.fox.factory.entities.dto.CommentCreateDto;
import com.fox.factory.entities.dto.CommentsDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, ProductMapper.class})
public interface CommentMapper {
    Comment toEntity(CommentsDto commentsDto);

    CommentsDto toDto(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate(CommentsDto commentsDto, @MappingTarget Comment comment);




    Comment fromSubCommentDto(SubCommentDto subCommentDto);

    SubCommentDto toSubCommentDto(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdateSubComment(SubCommentDto subCommentDto, @MappingTarget Comment comment);




    Comment toEntityCreate(CommentCreateDto commentCreateDto);

    CommentCreateDto toDtoCreate(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdateCreate(CommentCreateDto commentCreateDto, @MappingTarget Comment comment);
}