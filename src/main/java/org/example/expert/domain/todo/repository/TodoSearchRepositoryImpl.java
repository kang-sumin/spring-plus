package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// QueryDSL이 자동 생성해주는 Q 클래스를 이용해 Todo와 User 참조
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;


@Repository
@RequiredArgsConstructor
public class TodoSearchRepositoryImpl implements TodoSearchRepository {

    // JPAQueryFactory 생성자 주입 : JPAQueryFactory는 QueryDSL에서 쿼리를 생성하는 팩토리 클래스
    private final JPAQueryFactory q;

    @Override
    public Optional<Todo> findByIdWithUserFromQueryDsl(long todoId) {
        Todo getTodo = q
                .select(todo)
                .from(todo)
                .leftJoin(todo.user, user).fetchJoin()  // N+1 문제를 해결하기 위해서 FETCH JOIN 사용하여 한번에 들고옴
                .where(
                        todoIdEq(todoId)    // 조건문 BooleanExpression으로 정의
                ).fetchOne();   // 단일 결과 가져오기
        return Optional.ofNullable(getTodo);    // 조회된 결과가 null일 수도 있으므로 Optional로 감싸서 처리
    }

    private BooleanExpression todoIdEq(Long todoId) {
        return todoId != null ? todo.id.eq(todoId) : null;
    }
}
