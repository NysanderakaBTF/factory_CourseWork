package com.fox.factory.service;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.entities.Comment;
import com.fox.factory.entities.User;
import com.fox.factory.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository repository;

    public Comment create(Comment a){
        a.setPublishDate(LocalDate.now());
        return repository.save(a);
    }

    public List<Comment> findByAuthor(User author){
        return repository.findAllByAuthor(author);
    }

    public List<Comment> findAllBetweenDate(@Nullable LocalDate from, @Nullable LocalDate to){
        if (from == null)
            from = LocalDate.MIN;
        if (to == null)
            to = LocalDate.MAX;
        return repository.findAllByPublishDateBetween(from, to);
    }

    public List<Comment> findByRating(Integer rating){
        return repository.findAllByRate(rating);
    }

    //for modetaror = admin

    public Comment publishComment(Long id){
        var a = repository.findById(id).orElse(null);
        if (a != null){
            a.setPublished(true);
            return repository.save(a);
        }
        return null;
    }

    public Comment addSubComment(Long id, Comment sub){
        var a = repository.findById(id).orElse(null);
        if (a != null){
            a.addToSubComments(sub);
            return repository.save(a);
        }
        return null;
    }

    public Comment edit(Long id, Comment upd){
        var a = repository.findById(id).orElse(null);
        if (a!=null) {
            a.setText(upd.getText());
            a.setRate(upd.getRate());
            return repository.save(a);
        }
        return null;
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Comment getById(Long id){
        return repository.findById(id).orElse(null);
    }
}
