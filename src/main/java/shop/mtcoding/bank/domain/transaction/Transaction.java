package shop.mtcoding.bank.domain.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.mtcoding.bank.domain.account.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor // --> 넣어야 하는 이유 : 스프링이 user 객체 생성할 때 빈 생성자로 new 하기 떄문!!
@Getter
@EntityListeners(AuditingEntityListener.class) //--> 이 구문을 작성해야 날짜가 자동으로 입력될 수 있다.
// db의 테이블 이름 별도 지동 entity 클래스명과 동일할 경우는 생략 가능
@Table(name = "transaction_tb")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account withdrawAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account depositAccount;

    private Long amount;

    private Long withdrawAccountBalance; // ex) 1111 계좌 -> 1000원!! => 500원 ==> 200원 등 그 순간 순간 마다의 최종 잔액,
    private Long depositAccountBalance;

    // TransactionEnum은 따로 또 만들어줘야 하는 클래스
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionEnum gubun; // WITHDRAW, DEPOSIT, TRANSFER, ALL : 출금, 입금, 이체, 전체

    // 계좌가 사라져도 로그는 남아야 한다.
    private String sender;
    private String receiver;
    private String tel;

    @CreatedDate // 이 설정 --> Insert 할 때 날짜가 자동으로 들어감
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 이 설정 --> Insert, Update 할 때 날짜가 자동으로 들어감
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Transaction(Long id, Account withdrawAccount, Account depositAccount, Long amount,
                       Long withdrawAccountBalance, Long depositAccountBalance, TransactionEnum gubun,
                       String sender, String receiver, String tel, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.withdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.withdrawAccountBalance = withdrawAccountBalance;
        this.depositAccountBalance = depositAccountBalance;
        this.gubun = gubun;
        this.sender = sender;
        this.receiver = receiver;
        this.tel = tel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
