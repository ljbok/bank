package shop.mtcoding.bank.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

// static import 라 경로 손수 직접 타이핑 해서 넣은 것 ( 2 개)
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.*;

// mockito 테스트 : 통합테스트를 할 건데 실 환경이 아니라 가짜 환경에서 하겠다는 의미
@AutoConfigureMockMvc // 가짜 환경에 등록된 MockMvc를 DI 함
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SecurityConfigTest {

    // 가짜 환경에 등록된 MockMvc를 DI 함
    @Autowired
    private MockMvc mvc;

    /* [#1 SecurityConfig.java]=========================================================================================
    http.authorizeRequests()
        .antMatchers("/api/s/**").authenticated()
        //.antMatchers("api/admin/**").hasRole("ROLE_" + UserEnum.ADMIN); //-> 예전에는 ROLE_ 를 넣어줘야 햇는데
                                                                          // 최근 공식 문서에서는 자동으로 붙어서 안 붙여도 됨.
        .antMatchers("/api/admin/**").hasRole("" + UserEnum.ADMIN)
        .anyRequest().permitAll();

    return http.build();
   [#1]================================================================================================================*/

    // 서버는 일관성 있게 에러가 리턴되어야 한다.
    // 내가 모르는 에러가 프론트한테 날라가지 않게, 내가 다 직접 제어하자.
    @Test
    public void authentication_test() throws Exception{
        // #1 테스트 용도
        // given : 테스트 하기 위해 필요한 데이터
        // when
            // api를 테스트 하기 위해 필요한 데이터 resultActions
        ResultActions resultActions = mvc.perform(get("/api/s/hello"));
        String reponseBody = resultActions.andReturn().getResponse().getContentAsString();
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();

        System.out.println("테스트 : " + reponseBody);
        System.out.println("테스트 상태 코드 : " + httpStatusCode);

        // then
        assertThat(httpStatusCode).isEqualTo(401);
    }

    @Test
    public void authorization_test() throws Exception{
        // #1 테스트 용도
        // given : 테스트 하기 위해 필요한 데이터
        // when
        // api를 테스트 하기 위해 필요한 데이터 resultActions
        ResultActions resultActions = mvc.perform(get("/api/admin/hello"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();

        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 상태 코드 : " + httpStatusCode);

        // then
        assertThat(httpStatusCode).isEqualTo(401);
    }
}