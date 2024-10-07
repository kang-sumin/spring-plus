package org.example.expert.domain.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "todos")
public class Todo extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String contents;
    private String weather;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    /**
     * CascadeType.PERSIST VS CascadeType.ALL
     * 할일을 생성한 유저가 담당자로 자동 등록(저장)만 되면 된다면 CascadeType.PERSIST를 사용하는게 옳습니다.
     * CascadeType.PERSIST는 저장(Persist)작업에만 적용되기 때문입니다. 하지만 나중에 삭제(Remove), 병합(Merge) 등과 같은
     * 다른 작업이 필요할 경우 영속성이 전이되지 않아 이후의 리팩토링을 고려하여 CascadeType.ALL을 적용하였습니다.
     * todo가 생성, 업데이트, 삭제 등의 작업을 수행할 때 연관된 manager 엔티티에도 동일한 작업이 적용되어야 한다고 생각하였습니다.
     *
     * CascadeType.ALL: 이 옵션은 모든 유형의 작업에 대해 연관된 엔티티에 전파를 허용합니다. 즉, PERSIST, MERGE, REMOVE, REFRESH, DETACH를 포함하여 모든 작업을 전파합니다.
     * PERSIST: 부모 엔티티가 저장될 때 자식 엔티티도 함께 저장됩니다.
     * MERGE: 부모 엔티티가 병합될 때 자식 엔티티도 병합됩니다.
     * REMOVE: 부모 엔티티가 삭제될 때 자식 엔티티도 삭제됩니다.
     * REFRESH: 부모 엔티티가 새로고침될 때 자식 엔티티도 새로고침됩니다.
     * DETACH: 부모 엔티티가 영속성 컨텍스트에서 분리될 때 자식 엔티티도 분리됩니다.
     */
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private List<Manager> managers = new ArrayList<>();

    public Todo(String title, String contents, String weather, User user) {
        this.title = title;
        this.contents = contents;
        this.weather = weather;
        this.user = user;
        this.managers.add(new Manager(user, this));
    }
}
