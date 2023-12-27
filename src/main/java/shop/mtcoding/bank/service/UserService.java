package shop.mtcoding.bank.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserReqDto;
import shop.mtcoding.bank.dto.user.UserRespDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import java.util.Optional;

// 서비스는 DTO를 요청받고, DTO로 응답한다.
@RequiredArgsConstructor // 이 어노테이션을 불이면 UserRepository, BCryptPasswordEncoder에 대한 Dependency Injection을 자동으로 수행한다.
@Service
public class UserService {
    // 로그 선언은 항상 해주도록 하자
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    // password 인코더
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional // 트랜잭션이 메소드 시작할 때 시작되고 종료될 때 함께 종료
    public UserRespDto.JoinRespDto 회원가입(UserReqDto.JoinReqDto joinReqDto){
        // username, password, email, password 4개 받아오면 된다.

        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());
        if (userOP.isPresent()){
            // 유저네임 중복되었다는 뜻
            throw new CustomApiException("동일한 username이 존재합니다");
        }
        // 2. 패스워드 인코딩
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        // 3. dto 응답 (회원가입이 잘 되었다는 것을 응답해주기 위함)
        return new UserRespDto.JoinRespDto(userPS);

    } //-------------------------------회원가입()


    // 이 내부 클래스들은 내가 개인 프로젝트에서 Request와 Reponse를 따롤 분기 했던 것을
    // 이 강의에서는 Service 내부에다가 만든 것으로 보면 된다. (--> 내 생각에는 그냥 따로 빼주는 것이 좋을 듯)
    // 안에다 내부 클래스로 작성하니깐 지저분함 그리고 원래 dto라 하더라도 setter는 만들어주면 안됨 생성자로 처리해야 하는데
    // 이 강사는 왜 그렇게 하는지 모르겠다 나중에는 이해가 되지 않으려나 기다려봐야겠다;;
/*  // ==> 리팩토링 하면서 이 부분 UserReqDto와 UserRespDto로 이동
    @Getter
    @Setter
    public static class JoinRespDto{
        private Long id;
        private String username;
        private String fullname;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullname = user.getFullname();
        }
    }

    @Getter
    @Setter
    public static class JoinReqDto{
        // 유효성 검사
        private String username;
        private String password;
        private String email;
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
*/


}