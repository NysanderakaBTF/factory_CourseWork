package com.fox.factory.service;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.entities.Comment;
import com.fox.factory.entities.User;
import com.fox.factory.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    @Transactional
    public Comment create(Comment a){
        a.setPublishDate(LocalDate.now());
        return repository.save(a);
    }
    @Transactional
    public List<Comment> findByAuthor(User author){
        return repository.findAllByAuthor(author);
    }
    @Transactional
    public List<Comment> findAllBetweenDate(@Nullable LocalDate from, @Nullable LocalDate to){
        if (from == null)
            from = LocalDate.MIN;
        if (to == null)
            to = LocalDate.MAX;
        return repository.findAllByPublishDateBetween(from, to);
    }
    @Transactional
    public List<Comment> findByRating(Integer rating){
        return repository.findAllByRate(rating);
    }

    //for modetaror = admin
    @Transactional
    public Comment publishComment(Long id){
        var a = repository.findById(id).orElse(null);
        if (a == null)
            return null;
        a.setPublished(true);
        return repository.save(a);

    }
    @Transactional
    public Comment addSubComment(Long id, Comment sub){
        var a = repository.findById(id).orElse(null);
        if (a != null){
            a.addToSubComments(sub);
            return repository.save(a);
        }
        return null;
    }
    @Transactional
    public Comment edit(Long id, Comment upd){
        var a = repository.findById(id).orElse(null);
        if (a!=null) {
            a.setText(upd.getText());
            a.setRate(upd.getRate());
            return repository.save(a);
        }
        return null;
    }
    @Transactional
    public void delete(Long id){
        repository.deleteById(id);
    }
    @Transactional
    public Comment getById(Long id){
        return repository.findById(id).orElse(null);
    }
}
