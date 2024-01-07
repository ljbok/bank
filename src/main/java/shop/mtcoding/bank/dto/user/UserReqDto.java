package shop.mtcoding.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserReqDto {

    @Getter
    @Setter
    public static class JoinReqDto{
        // 유효성 검사
        // (공통) @NotEmpty --> 공백일 수 없다

        // 영문, 숫자는 되고, 길이는 최소 2~20자 이내, 중간 공백 안되게
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2~20자 이내로 작성해주세요")
        @NotEmpty
        private String username;
        // 길이 4 ~ 20
        @NotEmpty
        @Size(min = 4, max = 20) // @Size는 String 에만 쓸 수 있다. int 형이나 다른형들은 다른 걸 써야된다.
        private String password;

        // 이메일 형식
        @NotEmpty
        @Pattern(regexp = "^[a-zA-z0-9]{2,10}@[a-zA-z0-9]{2,6}\\.[a-zA-z]{2,3}$", message = "이메일 형식으로 작성해주세요.")
        private String email;

        // 영어, 한글 가능, 길이 1 ~ 20
        @NotEmpty
        @Pattern(regexp = "^[a-zA-z가-힣]{1,20}$", message = "한글/영문 1~20자 이내로 작성해주세요.")
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