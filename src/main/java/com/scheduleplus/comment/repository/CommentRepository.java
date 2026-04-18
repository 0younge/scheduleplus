package com.scheduleplus.comment.repository;

import com.scheduleplus.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllBySchedule_scheduleId(Long scheduleId);
}
