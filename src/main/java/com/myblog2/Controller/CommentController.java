package com.myblog2.Controller;

import com.myblog2.payload.CommentDto;
import com.myblog2.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/leads/1/comments

    @PostMapping("/leads/{leadId}/comments")
    public ResponseEntity<CommentDto> CreateComment(@PathVariable("leadId") long leadId, @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.CreateComment(leadId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    //http://localhost:8080/api/leads/1/comments
    @GetMapping("/leads/{leadId}/comments")
    public List<CommentDto>getCommentByLeadId(@PathVariable("leadId") long leadId) {
        return commentService.getCommentByLeadId(leadId);

    }
    //http://localhost:8080/api/leads/1/comments/1
    @GetMapping("/leads/{leadId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("leadId") long leadId,@PathVariable("commentId") long commentId){
        CommentDto dto = commentService.getCommentById(leadId, commentId);

        return new ResponseEntity<>(dto,HttpStatus.OK);

    }

    //http://localhost:8080/api/leads/1/comments/2
    @PutMapping("/leads/{leadId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("leadId") long leadId,
                  @PathVariable("commentId") long commentId,
                  @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.updateComment(leadId, commentId, commentDto);

        return new ResponseEntity<>(dto,HttpStatus.OK);

    }
    //http://localhost:8080/api/leads/1/comments/2
    @DeleteMapping("/leads/{leadId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("leadId") long leadId,
                  @PathVariable("commentId") long commentId){

        commentService.deleteComment(leadId,commentId);

        return new ResponseEntity<>("comment is deleted",HttpStatus.OK);
    }

}
