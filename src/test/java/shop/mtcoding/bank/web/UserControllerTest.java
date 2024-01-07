package shop.mtcoding.bank.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserReqDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest extends shop.mtcoding.bank.config.dummy.DummyObject {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach // 실행 시점 : 모든 메소드가 실행되기 전에 실행됨
    public void setUp() {
        dataSetting();
    }

    @Test
    public void join_success_test() throws Exception {
        // given
        UserReqDto.JoinReqDto joinReqDto = new UserReqDto.JoinReqDto();
        joinReqDto.setUsername("love");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("love@nate.com");
        joinReqDto.setFullname("러브");

        String requestBody = om.writeValueAsString(joinReqDto); // dto 객체를 json으로 변환하는 과정★★★★★★
        //System.out.println("테스트_requestBody : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/api/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)); // test로 url에 data와 함께 요청을 전달하는 방식 ★★★★★

        //String reponseBody = resultActions.andReturn().getResponse().getContentAsString(); // 요청에 따른 응답의
        // return 타입이 있으면 반환받는 데이터를 가져온다(String).
        //System.out.println("테스트_responseBody : " + reponseBody); // 응답 받은 데이터를 확인해본다!

        // then
        resultActions.andExpect(status().isCreated()); //--> status가 isCreated() 가 나오면 정상이라는 뜻
    }


    // 이미 존재하는 회원이 있을 때 유효성 검사 일부러 실패하게 만들기 위해 위에서 @BeforeEach 메소드로 동일한 회원정보 등록해놨음
    @Test
    public void join_fail_test() throws Exception {
        // given
        UserReqDto.JoinReqDto joinReqDto = new UserReqDto.JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("쌀");

        String requestBody = om.writeValueAsString(joinReqDto); // dto 객체를 json으로 변환하는 과정★★★★★★
        //System.out.println("테스트_requestBody : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/api/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)); // test로 url에 data와 함께 요청을 전달하는 방식 ★★★★★

        String reponseBody = resultActions.andReturn().getResponse().getContentAsString(); // 요청에 따른 응답의
        // return 타입이 있으면 반환받는 데이터를 가져온다(String).
        System.out.println("테스트_responseBody : " + reponseBody); // 응답 받은 데이터를 확인해본다!

        // then
        resultActions.andExpect(status().isBadRequest()); // status가 isBadRequest() 가 나오면 정상이라는 뜻
    }                                                   // 개발자(나) 가 실패를 예상하고 테스트를 구성했다는 것!
    
    
    // 위에서 @BeforeEach 로 가장 먼저 실행되게끔 설정해놨음
    // 여기서 userRepository 로 데이터를 강제로 세이브 해줄 거임
    private void dataSetting() {
        userRepository.save(newUser("ssar","쌀")); // 강제 데이터 저장
    }

}
