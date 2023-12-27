package shop.mtcoding.bank.domain.user;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor // --> 넣어야 하는 이유 : 스프링이 user 객체 생성할 때 빈 생성자로 new 하기 떄문!!
@Getter
@EntityListeners(AuditingEntityListener.class) //--> 이 구문을 작성해야 날짜가 자동으로 입력될 수 있다.
// db의 테이블 이름 별도 지동 entity 클래스명과 동일할 경우는 생략 가능
@Table(name = "user_tb")
@Entity
public class User { // extends 시간설정 (상속) 하는 방식은 junit 테스트 할 때 복잡해지므로 비권장

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제약조건 유니크 부여, not null 처리, 길이 지정
    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 60) // 패스워드 인코딩 (BCrype)하게 되면 길이가 늘어나기 때문에 여유있게 준 것
    private String password;

    @Column(nullable = false, length = 20)
    private String email;

    @Column(nullable = false, length = 20)
    private String fullname;

    // UserEnum은 따로 또 만들어줘야 하는 클래스
    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // 실제 유저테이블에는 없으니깐 not null만
    private UserEnum role;  //  --> ADMIN, CUSTOMER

    @CreatedDate // 이 설정 --> Insert 할 때 날짜가 자동으로 들어감
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 이 설정 --> Insert, Update 할 때 날짜가 자동으로 들어감
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public User(Long id, String username, String password, String email, String fullname, UserEnum role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
