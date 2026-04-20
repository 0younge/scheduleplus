package com.scheduleplus.comment.controller;

import com.scheduleplus.comment.dto.CreateCommentRequest;
import com.scheduleplus.comment.dto.GetCommentResponse;
import com.scheduleplus.comment.dto.UpdateCommentRequest;
import com.scheduleplus.comment.service.CommentService;
import com.scheduleplus.common.BaseController;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController extends BaseController {

    private final CommentService commentService;

    /**
     * 댓글 추가
     * @param request 작성할 댓글내용
     * @param session 검증을 위한 세션
     * @param scheduleId 작성할 댓글 위치
     * @return 상태코드
     */
    @PostMapping
    public ResponseEntity<Void> createComment(
            @RequestBody @Valid CreateCommentRequest request,
            HttpSession session,
            @PathVariable Long scheduleId) {
        commentService.save(request, authSession(session), scheduleId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 특정 댓글 조회
     * @param commentId 조회할 댓글 아이디
     * @return 댓글 작성자, 조회할 일정 아이디, 내용, 작성/수정일 반환
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<GetCommentResponse> getOneComment(@PathVariable Long commentId) {
        return ResponseEntity.ok().body(commentService.getOne(commentId));
    }

    /**
     * 전체 댓글 조회
     * @param scheduleId 조회할 댓글의 일정 아이디
     * @return 특정 일정의 전체 댓글 리스트 반환
     */
    @GetMapping
    public ResponseEntity<List<GetCommentResponse>> getAllComment(@PathVariable Long scheduleId) {
        return ResponseEntity.ok().body(commentService.getAll(scheduleId));
    }

    /**
     * 댓글 수정
     * @param commentId 수정할 댓글 아이디
     * @param session 검증을 위한 세션
     * @param request 수정할 댓글 내용
     * @return 상태코드
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long commentId,
            HttpSession session,
            @RequestBody @Valid UpdateCommentRequest request) {
        commentService.update(commentId, authSession(session), request);

        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 삭제
     * @param commentId 삭제할 댓글 아이디
     * @param session 검증을 위한 세션
     * @return 상태코드
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, HttpSession session) {
        commentService.delete(commentId, authSession(session));

        return ResponseEntity.noContent().build();
    }

}
