package com.fox.factory.controllres;

import com.fox.factory.entities.dto.CommentCreateDto;
import com.fox.factory.entities.dto.CommentsDto;
import com.fox.factory.entities.dto.SubCommentDto;
import com.fox.factory.service.CommentService;
import com.fox.factory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService service;

    @Operation(summary = "View comment")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "find comments between dates")
    @GetMapping("/filter/dates")
    public ResponseEntity<List<CommentsDto>> filterCommentsByDate(@Nullable @RequestParam LocalDate from,
                                                                  @Nullable @RequestParam LocalDate to){
        return ResponseEntity.ok(service.findAllBetweenDate(from, to));
    }

    @Operation(summary = "View comment")
    @GetMapping("/{id}")
    public ResponseEntity<CommentsDto> wiewComment(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "ass subcomment for existing one")
    @PostMapping("/{id}/add_sub")
    public ResponseEntity<CommentsDto> addSub(@PathVariable long id, @RequestBody SubCommentDto dto){
        return ResponseEntity.ok(service.addSubComment(id, dto));
    }

    @Operation(summary = "change comment visibiity (for moderator)")
    @PutMapping("/{id}/publish")
    public ResponseEntity<CommentsDto> changeVisibility(@PathVariable Long id, @RequestParam Boolean status){
        return ResponseEntity.ok(service.editPublishStatus(id, status));
    }

    @Operation(summary = "Edit comment")
    @PutMapping("/{id}/edit")
    public ResponseEntity<CommentsDto> edit(@PathVariable Long id, @RequestBody CommentsDto dto){
        return ResponseEntity.ok(service.edit(id, dto));
    }


}
