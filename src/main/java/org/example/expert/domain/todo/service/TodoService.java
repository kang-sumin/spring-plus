package org.example.expert.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.client.WeatherClient;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.manager.repository.ManagerRepository;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final WeatherClient weatherClient;
    private final ManagerRepository managerRepository;  // todo가 저장, 업데이트, 삭제 될때 manager 조회를 고려한 repository 생성자 주입

    /**
     * [Level1] 1. 코드 개선 퀴즈 - @Transactional의 이해
     * TodoService 클래스에  @Transactional(readOnly = true) 애노테이션으로 데이터베이스 작업을 읽기 전용처리해 두었습니다.
     * 읽기 전용으로 처리하면 데이터베이스의 데이터 무결성 제약을 보호할 수 있고, 데이터 쓰기를 방지하고, 캐시를 효율적으로 사용하여 성능 최적화에 좋습니다.
     * 하지만 이 트랜잭션 내에서 데이터베이스의 수정(삽입, 업데이트, 삭제) 가 발생하게 되면 예외가 발생하게 됩니다.
     * 클래스에 @Transactional(readOnly = true)가 설정되어 있을때 수정 작업 메서드를 수행하게 하려면
     * 해당 메서드에 @Transactional(readOnly = false)를 적용하여 수정 작업이 가능하도록 메서드 레벨에서 상위 설정을 덮어써서 수정 작업을 수행 할 수 있습니다.
     * 참고로 readOnly 설정의 default 값이 false 여서 생략 가능합니다.
      */
    @Transactional
    public TodoSaveResponse saveTodo(AuthUser authUser, TodoSaveRequest todoSaveRequest) {
        User user = User.fromAuthUser(authUser);

        String weather = weatherClient.getTodayWeather();

        Todo newTodo = new Todo(
                todoSaveRequest.getTitle(),
                todoSaveRequest.getContents(),
                weather,
                user
        );
        Todo savedTodo = todoRepository.save(newTodo);

        return new TodoSaveResponse(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getContents(),
                weather,
                new UserResponse(user.getId(), user.getEmail(), user.getNickname())
        );
    }

    public Page<TodoResponse> getTodos(int page, int size, String  weather, String searchStartDate, String searchEndDate) {
        Pageable pageable = PageRequest.of(page - 1, size);

        // 입력된 날짜가 비었을 때
        // 해당값이 null인지 먼저 확인해야함 -> null 처리를 먼저하지 않으면 NullPointException 발생함, null 처리후 다음 프로세스 진행하도록 해야 예외 발생 안함
        if(searchStartDate == null || searchStartDate.isEmpty()){
            searchStartDate = "00010101";
        }
        if(searchEndDate == null || searchEndDate.isEmpty()){
            searchEndDate = "99991231";
        }


        // String으로 들어온 날짜 데이터형 변환
        LocalDateTime startDate = LocalDate.parse(searchStartDate, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0,0,0);
        LocalDateTime endDate = LocalDate.parse(searchEndDate, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(23,59,59);


        Page<Todo> todos = todoRepository.findAllByWeatherAndBetweenDate(pageable, weather, startDate, endDate);

        return todos.map(todo -> new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(todo.getUser().getId(), todo.getUser().getEmail(), todo.getUser().getNickname()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        ));
    }

    public TodoResponse getTodo(long todoId) {
        Todo todo = todoRepository.findByIdWithUserFromQueryDsl(todoId)
                .orElseThrow(() -> new InvalidRequestException("Todo not found"));

        User user = todo.getUser();

        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(user.getId(), user.getEmail(), user.getNickname()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }
}
