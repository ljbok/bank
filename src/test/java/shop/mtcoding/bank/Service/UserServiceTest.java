package shop.mtcoding.bank.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;  // 임포트 경로 유의
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.service.UserService;

// mockito 사용을 위해서 내가 수동으로 import 한 static 패키지 , alt + enter 하면 할 수 있음
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// 이 환경에서는 spring 관련 bean 들이 하나도 없는 환경 따라서 직접 private UserRepository userRepsotiory 이런식으로 만들어야 한다.
@ExtendWith(MockitoExtension.class) // service 테스트 하기 위해 붙여야 하는 어노테이션 : mockito 환경에서 테스트 해보겠다
public class UserServiceTest {

    @InjectMocks // 가짜 환경에다가 넣어줘야 하기 때문에 @Autowired 가 아닌 @InjectMocks로 어노테이션을 작성한다.
    private UserService userService;

    @Mock // UserRepository를 가짜로 메모리에 띄우는 형태 여기써 띄운 것을 @InjectMocks 가 붙어있는 UserService에 주입해준다.
    private UserRepository userRepository;

    @Spy // 기존에 실제 ioc 컨테이너에 등록된 빈(spring 자체에서 제공하는 빈)을 가짜 환경인 UserService에 넣을 때 쓰는 어노테이션
    private BCryptPasswordEncoder passwordEncoder;


    @Test
    public void 회원가입_test() throws Exception{
        // given
        UserService.JoinReqDto joinReqDto = new UserService.JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("쌀");

        // sutb : 일종의 가정법
        // 이 상태로 테스트를 돌리면 UserRepository 클래스를 가짜 환경을 통해 UserService에 주입했지만
        // 메소드까지는 주입받지 못했기 때문에 stub 라는 처리가 추가적으로 필요하게 된다.
        //when(null).thenReturn(null);

        // stub 1
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty()); // 등록된 사용자인지 조회
        // └→ userRepository의 findByUsername 이 실행되면 return을 bean optional 객체를 할 것이다
        // 이렇게 해아지 userService의  Optional<User> userOP if (userOP.isPresent()){ 구문을 탈 수 있으니깐
        // any() 처리한 것은 가짜 환경에는 db가 없기 때문에 모든 데이터가 존재하지 않는다.
        // 그래서 어떠한 데이터가 들어오더라도 Optional.empty를 반환하여 transaction이 테스트 가능하도록 만들어 준 것이다.

        //when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        // stub 2
        User ssar = User.builder()
                .id(1L)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .fullname("쌀")
                .role(UserEnum.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(userRepository.save(any())).thenReturn(ssar);

        // when
        UserService.JoinRespDto joinRespDto = userService.회원가입(joinReqDto); // 회원가입 go
        System.out.println("테스트 회원가입 된 ssar 정보 : " + joinRespDto.toString() );

        // then : assertThat() 안에 있는 값이 isEqualTo() 안에 있는 값(예상값)과 다르다면 에러를 던진다.
        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("ssar");
    }
}
