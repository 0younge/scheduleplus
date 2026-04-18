package com.scheduleplus.comment.controller;

import com.scheduleplus.comment.dto.CreateCommentRequest;
import com.scheduleplus.comment.dto.GetCommentResponse;
import com.scheduleplus.comment.dto.UpdateCommentRequest;
import com.scheduleplus.comment.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CreateCommentRequest request, HttpSession session, @PathVariable Long scheduleId) {
        commentService.save(request, session, scheduleId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<GetCommentResponse> getOneComment(@PathVariable Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getOne(commentId));
    }

    @GetMapping
    public ResponseEntity<List<GetCommentResponse>> getAllComment(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll(scheduleId));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, HttpSession session, @RequestBody UpdateCommentRequest request) {
        commentService.update(commentId, session, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, HttpSession session) {
        commentService.delete(commentId, session);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
