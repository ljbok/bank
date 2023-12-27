package shop.mtcoding.bank.domain.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.mtcoding.bank.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor // --> 넣어야 하는 이유 : 스프링이 user 객체 생성할 때 빈 생성자로 new 하기 떄문!!
@Getter
@EntityListeners(AuditingEntityListener.class) //--> 이 구문을 작성해야 날짜가 자동으로 입력될 수 있다.
// db의 테이블 이름 별도 지동 entity 클래스명과 동일할 경우는 생략 가능
@Table(name = "account_tb")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 20)
    private Long number;  // 계좌 번호
    @Column(nullable = false, length = 4)
    private Long password; // 계좌 비밀번호
    @Column(nullable = false)
    private Long balance; // 잔액 (기본 값 1000 원으로 두기로 하자)

    // ★ 계좌는 유저와의 관계가 있다 그래서 추가해야 할 부분이 있다 ★
    // 항상 ORM에서 fk의 주인은 Many Entity 쪽이다.
    // 여기 클래스인 계좌가 Many, 변수 선언한 User 가 One
    @ManyToOne(fetch = FetchType.LAZY) // account.getUser().아무필드호출() == Lazy 발동
    private User user;// user_id 

    @CreatedDate // 이 설정 --> Insert 할 때 날짜가 자동으로 들어감
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 이 설정 --> Insert, Update 할 때 날짜가 자동으로 들어감
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Account(Long id, Long number, Long password, Long balance, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.balance = balance;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
