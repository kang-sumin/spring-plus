package org.example.expert.domain.comment.repository;

import org.example.expert.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * N+1 문제를 해결하기 위해 FETCH JOIN 방식을 사용하였습니다.
     * 해당 쿼리에서 Comment 엔티티와 연관된 User엔티티를 FETCH JOIN으로 함께 조회해서 가져오면서 N+1 문제를 해결하였습니다.
     */
    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.todo.id = :todoId")
    List<Comment> findByTodoIdWithUser(@Param("todoId") Long todoId);
}
