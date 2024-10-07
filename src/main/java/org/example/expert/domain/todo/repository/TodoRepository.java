package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearchRepository{

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    /**
     * weather이 NULL일수도 있고, 특정값이 있을 수도 있기때문에
     * :weather IS NULL OR t.weather = :weather 조건을 사용하였다.
     * weather이 NULL인 경우 해당 조건을 무시하고, 값이 있을 때만 해당 조건으로 검색하게 만드는 조건문이다.
     * 1. weather 이 NULL일 경우 :weather IS NULL 이 true가 되면서 그 이후의 조건을 무시하게 된다. 즉, weather 컬럼을 아예 조건에 넣지 않는다.
     * 2. weather 이 특정값일 경우 :weather IS NULL 이 false 가 되면서 t.weather = :weather 조건이 수행되게 된다.
     * IS NULL OR 조건문은 특정 컬럼값이 NULL이거나 특정 값과 일치하는지를 검사하는 선택적인 필터링을 할 때 사용한다.
     */
    @Query("SELECT t FROM Todo t " +
            "WHERE (:weather IS NULL OR t.weather = :weather)" +
            "AND (t.modifiedAt BETWEEN :startDate AND :endDate)" +
            "ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByWeatherAndBetweenDate(Pageable pageable,
                                              @Param("weather") String weather,
                                              @Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);
}
