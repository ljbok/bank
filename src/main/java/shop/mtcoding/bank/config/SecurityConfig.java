package shop.mtcoding.bank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.util.CustomResponseUtil;


@Configuration // --> 설정 파일로서 IOC에 빈을 등록해주는 과정
public class SecurityConfig {

    // --> 등록된 빈을 로그로 확인하기 위해 작성한 구문 import 하는 패키지 주의!
    // @Slf4j 로 대체가능하지만 unit 테스트할 때 문제가 생기므로 아래처럼 작성해주도록 하자.
    private final Logger log = LoggerFactory.getLogger(getClass());

    // @Bean 어노테이션의 유의점 이 어노테이션은 @Cofiguration 클래스 안에서
    // 사용하는 경우에만 동작하며 @Configuration 이 붙어있지 않은 클래스에서 사용하는 경우
    // @Bean 어노테이션은 동작하지 않는다.

    @Bean // Ioc 컨테이너에 BCryptPasswordEncoder() 객체가 등록됨.
    public BCryptPasswordEncoder passwordEncoder(){
        log.info("디버그 : BCryptPassEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    // JWT 필터 등록이 필요함


    // JWT 서버를 만들 예정 --> session 사용 안 함
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("디버그 : filterChain 빈 등록됨");
        http.headers().frameOptions().disable(); // iframe 허용 안함.
        http.csrf().disable(); // enable 이면 postman 작동 안함 (메타코딩 유튜브에 시큐리티 강의)
        http.cors().configurationSource(configurationSource()); // cors api 요청 중자 바스크립트 요청을 거절하는 것

        // jSessionId를 서버쪽에서 관리 안 하겠다는 뜻!
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // react, 앱 에서 요청 하는 방식을 쓸 것이므로
        // 폼 로그인 방식을 쓰지 않겠다 ↓
        http.formLogin().disable();

        // Exception 가로채기
         http.exceptionHandling().authenticationEntryPoint((request, response, authenticationException) -> {
            // 원래 있던 부분 util 패키지의 CustomReponseUtil.java 로 분리시켰음
             CustomResponseUtil.unAuthentication(response, "로그인을 진행해 주세요");
         });

        // httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다.
        http.httpBasic().disable(); // 만약 disable을 하지 않으면 갑가지 브라우저가 인증하라고 팝업창을 띄울것이다 그것을 해제한것

        http.authorizeRequests()
                .antMatchers("/api/s/**").authenticated()
                //.antMatchers("api/admin/**").hasRole("ROLE_" + UserEnum.ADMIN); //-> 예전에는 ROLE_ 를 넣어줘야 햇는데
                                                                                  // 최근 공식 문서에서는 자동으로 붙어서 안 붙여도 됨.
                .antMatchers("/api/admin/**").hasRole("" + UserEnum.ADMIN)
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource(){
        log.info("디버그 : configurationSource cors 설정이 SercurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");    // GET,POST,PUT,DELETE (Javascript 요청을 허용하겠다는 뜻)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (==> 프론트엔드(react) ip만 허용 한다는 의미와 유사)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); //--> 위에서 설정한 것들을 모든 url 요청에 적용해주겠다라는 의미
        return source;
    }
}