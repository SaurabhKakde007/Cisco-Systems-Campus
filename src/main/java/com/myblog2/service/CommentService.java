package com.myblog2.service;

import com.myblog2.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto CreateComment(long leadId, CommentDto commentDto);

    List<CommentDto> getCommentByLeadId(long leadId);

    CommentDto getCommentById(long leadId, long commentId);

    CommentDto updateComment(long leadId, long commentId, CommentDto commentDto);

    void deleteComment(long leadId, long commentId);
}
