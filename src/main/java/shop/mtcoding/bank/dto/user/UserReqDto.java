package shop.mtcoding.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import javax.validation.constraints.NotEmpty;

public class UserReqDto {

    @Getter
    @Setter
    public static class JoinReqDto{
        // 유효성 검사
        // @NotEmpty --> 공백일 수 없다 ||
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
        @NotEmpty
        private String email;
        @NotEmpty
        private String fullname;

        // 패스워드 인코딩을 위해 requestDto(JoinReqDto) 를 Entity로 변환하는 과정에서
        // 매개 변수에 "BCryptPasswordEncoder passwordEncoder" 를 추가한 것
        public User toEntity(BCryptPasswordEncoder passwordEncoder){
            // User 엔티티 클래스의 생성자 부분에 @Builder 어노테이션을 붙였어서
            // 이렇게 쉽게 User 타입으로 빌드할 수 있는 것임.
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER) // 사용자 회원가입 이모로 roll 에 customer 부여
                    .build();
        }
    }
}