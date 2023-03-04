package com.fox.factory.repositories;

import com.fox.factory.entities.Comment;
import com.fox.factory.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByAuthor(User u);

    List<Comment> findAllByPublishDateBetween(LocalDate d1, LocalDate d2);
    List<Comment> findAllByRate(Integer mark);
}