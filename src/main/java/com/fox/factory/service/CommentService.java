package com.fox.factory.service;

import com.fox.factory.entities.Comment;
import com.fox.factory.entities.dto.CommentCreateDto;
import com.fox.factory.entities.dto.CommentsDto;
import com.fox.factory.entities.dto.SubCommentDto;
import com.fox.factory.entities.dto.UserInCommentDto;
import com.fox.factory.repositories.CommentRepository;
import com.fox.factory.security.JwtAuthenticationFilter;
import com.fox.factory.security.Role;
import com.fox.factory.service.mappers.CommentMapper;
import com.fox.factory.service.mappers.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final UserMapper userMapper;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Transactional
    public CommentsDto create(CommentCreateDto a){
        return mapper.toDto(repository.save(mapper.toEntityCreate(a)));
    }
    @Transactional
    public List<CommentsDto> findByAuthor(UserInCommentDto author){
        return repository.findAllByAuthor(userMapper.toEntityInComment(author)).stream().map(mapper::toDto).collect(Collectors.toList());
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
    public CommentsDto editPublishStatus(Long id, boolean isPublished){
        return repository.findById(id)
                .map(comment -> comment.setPublished(isPublished))
                .map(repository::save)
                .map(mapper::toDto)
                .orElse(null);

    }
    @Transactional
    public CommentsDto addSubComment(HttpServletRequest req,  Long id, SubCommentDto sub){
        var user = jwtAuthenticationFilter.get_user_from_req(req);
        var commen = mapper.fromSubCommentDto(sub);
        commen.setPublishDate(LocalDate.now());
        commen.setAuthor(user);
        final var a = repository.save(commen);
        return repository.findById(id)
                .map(comment -> comment.addToSubComments(a))
                .map(repository::save)
                .map(mapper::toDto)
                .orElse(null);

    }
    @Transactional
    public CommentsDto edit(Long id, CommentsDto upd){
        return repository.findById(id)
                .map(comment -> mapper.partialUpdate(upd, comment))
                .map(repository::save)
                .map(mapper::toDto)
                .orElse(null);
    }
    @Transactional
    public void delete(HttpServletRequest req,  Long id){
        var commnent = repository.findById(id);
        if (commnent.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No comment");
        }
        var c = commnent.get();

        var user = jwtAuthenticationFilter.get_user_from_req(req);

        if (!Objects.equals(c.getAuthor().getId(), user.getId()) && user.getRole() == Role.USER){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        repository.deleteById(id);
    }

    @Transactional
    public List<CommentsDto> findForProfuct(Long prod_id){return repository.findAllByProduct_Id(prod_id).stream().map(mapper::toDto).collect(Collectors.toList());}
    @Transactional
    public CommentsDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    public void delete_sub(HttpServletRequest req, Long id, Long subId) {
        var commnent = repository.findById(id);
        var sub_comment = repository.findById(subId);

        if (commnent.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No comment");
        }
        var c = commnent.get();

        if (sub_comment.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No comment");
        }
        var cc = sub_comment.get();

        var user = jwtAuthenticationFilter.get_user_from_req(req);

        if (!Objects.equals(cc.getAuthor().getId(), user.getId()) && user.getRole() == Role.USER){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        c.setSubComments(c.getSubComments().stream().filter(comment -> !Objects.equals(comment.getId(), cc.getId())).collect(Collectors.toSet()));
        repository.save(c);
        repository.delete(cc);
    }
}
