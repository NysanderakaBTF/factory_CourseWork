package com.fox.factory.controllres;

import com.fox.factory.entities.dto.CommentCreateDto;
import com.fox.factory.entities.dto.CommentsDto;
import com.fox.factory.entities.dto.SubCommentDto;
import com.fox.factory.service.CommentService;
import com.fox.factory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
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

/**
 * Delete a comment by id
 * 
 * @param id The id of the comment to be deleted
 * @return A ResponseEntity with no content.
 */
    @Operation(summary = "Delete commnet")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteComment( HttpServletRequest req, @PathVariable Long id){
        service.delete(req, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete sub comment")
    @DeleteMapping("/{id}/{sub_id}")
    public ResponseEntity<Void> deleteSubCommnet(HttpServletRequest req, @PathVariable Long id, @PathVariable Long sub_id){
        service.delete_sub(req, id, sub_id);
        return ResponseEntity.noContent().build();
    }

/**
 * Find comments between dates
 * 
 * @param from the start date
 * @param to 2020-01-01
 * @return A list of comments between two dates.
 */
    @Operation(summary = "find comments between dates")
    @GetMapping("/filter/dates")
    public ResponseEntity<List<CommentsDto>> filterCommentsByDate(@Nullable @RequestParam LocalDate from,
                                                                  @Nullable @RequestParam LocalDate to){
        return ResponseEntity.ok(service.findAllBetweenDate(from, to));
    }

/**
 * View comment
 * 
 * @param id The id of the comment to be viewed
 * @return A comment with the given id.
 */
    @Operation(summary = "View comment")
    @GetMapping("/{id}")
    public ResponseEntity<CommentsDto> wiewComment(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

/**
 * Ass subcomment for existing one
 * 
 * @param id the id of the comment to which the subcomment is added
 * @param dto 
 * @return ResponseEntity<CommentsDto>
 */
    @Operation(summary = "ass subcomment for existing one")
    @PostMapping("/{id}/add_sub")
    public ResponseEntity<CommentsDto> addSub(HttpServletRequest req,  @PathVariable long id, @RequestBody SubCommentDto dto){
        return ResponseEntity.ok(service.addSubComment(req, id, dto));
    }

/**
 * Change comment visibiity (for moderator)
 * 
 * @param id the id of the comment
 * @param status true or false
 * @return A ResponseEntity with a CommentsDto object.
 */
    @Operation(summary = "change comment visibiity (for moderator)")
    @PutMapping("/{id}/publish")
    public ResponseEntity<CommentsDto> changeVisibility(@PathVariable Long id, @RequestParam Boolean status){
        return ResponseEntity.ok(service.editPublishStatus(id, status));
    }

/**
 * Edit comment
 * 
 * @param id The id of the comment to be edited
 * @param dto The object that will be edited.
 * @return A ResponseEntity with a body of CommentsDto
 */
    @Operation(summary = "Edit comment")
    @PutMapping("/{id}/edit")
    public ResponseEntity<CommentsDto> edit(@PathVariable Long id, @RequestBody CommentsDto dto){
        return ResponseEntity.ok(service.edit(id, dto));
    }


}
