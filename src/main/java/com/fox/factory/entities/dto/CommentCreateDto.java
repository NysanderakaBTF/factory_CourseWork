package com.fox.factory.entities.dto;

import com.fox.factory.entities.Comment;
import com.fox.factory.entities.dto.ProductListDto;
import com.fox.factory.entities.dto.UserInCommentDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link Comment} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CommentCreateDto implements Serializable {
    private final String text;
    private final boolean isPublished;
    private final UserInCommentDto author;
    private final LocalDate publishDate;
    private final ProductListDto product;
    private final Integer rate;
}