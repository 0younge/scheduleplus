package com.scheduleplus.comment.service;

import com.scheduleplus.comment.dto.CreateCommentRequest;
import com.scheduleplus.comment.dto.GetCommentResponse;
import com.scheduleplus.comment.dto.UpdateCommentRequest;
import com.scheduleplus.comment.entity.Comment;
import com.scheduleplus.comment.repository.CommentRepository;
import com.scheduleplus.common.SessionValue;
import com.scheduleplus.schedule.service.ScheduleService;
import com.scheduleplus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ScheduleService scheduleService;

    /**
     * 댓글 저장
     * @param request 저장할 댓글 내용
     * @param sessionValue 저장할 작성자 세션 값
     * @param scheduleId 작성할 댓글 위치
     */
    @Transactional
    public void save(CreateCommentRequest request, SessionValue sessionValue, Long scheduleId) {
        Comment comment = new Comment(
                request.getContent(),
                userService.findUserByIdElseThrow(sessionValue.getUserId()),
                scheduleService.findScheduleByIdElseThrow(scheduleId));

        commentRepository.save(comment);
    }

    /**
     * 특정 댓글 조회
     * @param commentId 조회할 댓글 아이디
     * @return 댓글 작성자, 조회할 일정 아이디, 내용, 작성/수정일 반환
     */
    @Transactional(readOnly = true)
    public GetCommentResponse getOne(Long commentId) {
        Comment comment = emptyVerificationByCommentId(commentId);

        return GetCommentResponse.from(comment);
    }

    /**
     * 전체 댓글 조회
     * @param scheduleId 조회할 댓글들의 일정 아이디
     * @return 특정 일정의 댓글 리스트
     */
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllBySchedule_scheduleId(scheduleId);

        return comments.stream().map(GetCommentResponse::from).toList();
    }

    /**
     * 댓글 수정
     * @param commentId 수정할 댓글 아이디
     * @param sessionValue 검증을 위한 세션 값
     * @param request 수정할 댓글 내용
     */
    @Transactional
    public void update(Long commentId, SessionValue sessionValue, UpdateCommentRequest request) {
        Comment comment = emptyVerificationByCommentId(commentId);
        comment.authorVerification(sessionValue.getName());

        comment.updateComment(request.getContent());
    }

    /**
     * 댓글 삭제
     * @param commentId 삭제할 댓글 아이디
     * @param sessionValue 검증을 위한 세션 값
     */
    @Transactional
    public void delete(Long commentId, SessionValue sessionValue) {
        Comment comment = emptyVerificationByCommentId(commentId);
        comment.authorVerification(sessionValue.getName());

        commentRepository.delete(comment);
    }

    /**
     * 댓글 아이디로 존재여부를 확인후 예외 처리를 마친 댓글 반환 매서드
     * @param commentId 찾고자하는 댓글 아이디
     * @return 예외처리를 마친 댓글 반환
     */
    public Comment emptyVerificationByCommentId(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."));
    }
}
