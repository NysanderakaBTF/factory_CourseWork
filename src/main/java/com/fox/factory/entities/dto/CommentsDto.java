package com.fox.factory.entities.dto;

import com.fox.factory.entities.Comment;
import com.fox.factory.entities.dto.UserInCommentDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link Comment} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CommentsDto implements Serializable {
    private  Long id;
    private  String text;
    private  UserInCommentDto author;
    private  LocalDate publishDate;
    private  Integer rate;
    private Set<CommentsDto> subComments;
}