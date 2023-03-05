package com.fox.factory.service;

import com.fox.factory.entities.dto.CommentCreateDto;
import com.fox.factory.entities.dto.CommentsDto;
import com.fox.factory.entities.dto.SubCommentDto;
import com.fox.factory.entities.dto.UserInCommentDto;
import com.fox.factory.repositories.CommentRepository;
import com.fox.factory.service.mappers.CommentMapper;
import com.fox.factory.service.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final UserMapper userMapper;
    @Transactional
    public CommentsDto create(CommentCreateDto a){
        return mapper.toDto(repository.save(mapper.toEntityCreate(a)));
    }
    @Transactional
    public List<CommentsDto> findByAuthor(UserInCommentDto author){
        return repository.findAllByAuthor(userMapper.toEntity(author)).stream().map(mapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public List<CommentsDto> findAllBetweenDate(@Nullable LocalDate from, @Nullable LocalDate to){
        if (from == null)
            from = LocalDate.MIN;
        if (to == null)
            to = LocalDate.MAX;
        return repository.findAllByPublishDateBetween(from, to).stream().map(mapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public List<CommentsDto> findByRating(Integer rating){
        return repository.findAllByRate(rating).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    //for modetaror = admin
    @Transactional
    public CommentsDto publishComment(Long id){
        var a = repository.findById(id).orElse(null);
        if (a == null)
            return null;
        a.setPublished(true);
        return mapper.toDto(repository.save(a));

    }
    @Transactional
    public CommentsDto addSubComment(Long id, SubCommentDto sub){
        var a = repository.findById(id).orElse(null);
        if (a != null){
            a.addToSubComments(mapper.fromSubCommentDto(sub));
            return mapper.toDto(repository.save(a));
        }
        return null;
    }
    @Transactional
    public CommentsDto edit(Long id, CommentsDto upd){
        var a = repository.findById(id).orElse(null);
        if (a!=null) {
            return mapper.toDto(repository.save(mapper.partialUpdate(upd, a)));
        }
        return null;
    }
    @Transactional
    public void delete(Long id){
        repository.deleteById(id);
    }
    @Transactional
    public CommentsDto getById(Long id){
        return mapper.toDto(repository.findById(id).orElse(null));
    }
}
