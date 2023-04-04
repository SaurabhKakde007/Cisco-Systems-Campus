package com.myblog2.service.impl;

import com.myblog2.entity.Comment;
import com.myblog2.entity.Lead;
import com.myblog2.exception.BlogApiException;
import com.myblog2.exception.ResponseNotFoundException;
import com.myblog2.payload.CommentDto;
import com.myblog2.payload.LeadResponse;
import com.myblog2.repository.CommentRepository;
import com.myblog2.repository.LeadRepository;
import com.myblog2.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private CommentRepository commentRepository;

    private LeadRepository leadRepository;
    public CommentServiceImpl(CommentRepository commentRepository, LeadRepository leadRepository) {
        this.commentRepository = commentRepository;
        this.leadRepository=leadRepository;
    }

    @Override
    public CommentDto CreateComment(long leadId, CommentDto commentDto) {

        Comment comment=mapToEntity(commentDto);

        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new ResponseNotFoundException("lead", "id", leadId));

        comment.setLead(lead);

        Comment newComment = commentRepository.save(comment);

        CommentDto dto=mapToDto(newComment);

        return dto;
    }

    @Override
    public List<CommentDto> getCommentByLeadId(long leadId) {
        List<Comment> comments = commentRepository.findByLeadId(leadId);

        return comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(long leadId, long commentId) {

        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new ResponseNotFoundException("lead", "id", leadId));


        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseNotFoundException("comment", "id", commentId));

        if(comment.getLead().getId()!=lead.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to post saurabh 400");

        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long leadId, long commentId, CommentDto commentDto) {
        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new ResponseNotFoundException("lead", "id", leadId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResponseNotFoundException("comment", "id", commentId));

        if(comment.getLead().getId()!=lead.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment Does not exit saurabh 400");

        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment saveComment = commentRepository.save(comment);

        return mapToDto(saveComment);

    }

    @Override
    public void deleteComment(long leadId, long commentId) {
        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new ResponseNotFoundException("lead", "leadId", leadId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseNotFoundException("comment", "commentId", commentId));

        if (comment.getLead().getId()!= lead.getId()) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment Does not exist saurabh 400");
        }
            commentRepository.delete(comment);


        }
    


    private CommentDto mapToDto(Comment newComment) {

        CommentDto commentDto=new CommentDto();

        commentDto.setId(newComment.getId());
        commentDto.setName(newComment.getName());
        commentDto.setEmail(newComment.getEmail());
        commentDto.setBody(newComment.getBody());

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment=new Comment();

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;

    }


}
