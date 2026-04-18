package com.scheduleplus.comment.service;

import com.scheduleplus.comment.dto.CreateCommentRequest;
import com.scheduleplus.comment.dto.GetCommentResponse;
import com.scheduleplus.comment.dto.UpdateCommentRequest;
import com.scheduleplus.comment.entity.Comment;
import com.scheduleplus.comment.repository.CommentRepository;
import com.scheduleplus.common.SessionValue;
import com.scheduleplus.schedule.service.ScheduleService;
import com.scheduleplus.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ScheduleService scheduleService;

    @Transactional
    public void save(CreateCommentRequest request, HttpSession session, Long scheduleId) {
        SessionValue sessionValue = (SessionValue)session.getAttribute("sessionId");
        Comment comment = new Comment(request.getContent(),
                userService.getUser(sessionValue.getUserId()),
                scheduleService.getSchedule(scheduleId));
        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public GetCommentResponse getOne(Long commentId) {
        Comment comment = getComment(commentId);
        return new GetCommentResponse(
                comment.getUser().getUserId(),
                comment.getSchedule().getScheduleId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllBySchedule_scheduleId(scheduleId);
        List<GetCommentResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            dtos.add(new GetCommentResponse(
                    comment.getUser().getUserId(),
                    comment.getSchedule().getScheduleId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()));
        }
        return dtos;
    }

    @Transactional
    public void update(Long commentId, HttpSession session, UpdateCommentRequest request) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        if (!commentRepository.findById(commentId).get().getUser().getUserId().equals(sessionValue.getUserId())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다."));
        comment.updateComment(request.getContent());
    }

    @Transactional
    public void delete(Long commentId, HttpSession session) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        if (!commentRepository.findById(commentId).get().getUser().getUserId().equals(sessionValue.getUserId())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다."));
        commentRepository.delete(comment);
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다."));
    }
}
