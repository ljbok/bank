package shop.mtcoding.bank.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shop.mtcoding.bank.dto.ResponseDto;

import javax.servlet.http.HttpServletResponse;

public class CustomResponseUtil {
    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    // 401과 403 차이 401은 인증이 없는 것 403은 권한이 없는 것
    // 사용자 로그인 시 --> 401 에러가 떠야함
    public static void unAuthentication(HttpServletResponse response, String msg) {
        try {
            ObjectMapper om = new ObjectMapper(); // json 타입 데이터를 만들기 위해 생성한 변수
            ResponseDto<?> responseDto = new ResponseDto<>(-1, msg,null);
            // 아래 처럼 구성하면 om.writeValueAsString() 메소드에 의해서 ResponseDto 타입의 responseDto가
            // json 타입으로 변환된다.
            String responseBody = om.writeValueAsString(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(401);
            response.getWriter().println(responseBody);
        }catch (Exception e) {
            log.error("서버 파싱 에러"); // 사실상 거의 절대적으로 파싱에러가 날 일이 없는 코드이지만 try-catch로 예외처리 해둠
        }
    }

    /*
    // 관리자 로그인 시 --> 403 에러가 떠야함 (나중에 리팩토링 할 거면 활용 하는 용도로 코드 작성)
    public static void unAuthorization(HttpServletResponse response, String msg) {
        try {
            ObjectMapper om = new ObjectMapper(); // json 타입 데이터를 만들기 위해 생성한 변수
            ResponseDto<?> responseDto = new ResponseDto<>(-1, msg,null);
            // 아래 처럼 구성하면 om.writeValueAsString() 메소드에 의해서 ResponseDto 타입의 responseDto가
            // json 타입으로 변환된다.
            String responseBody = om.writeValueAsString(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(403);
            response.getWriter().println(responseBody);
        }catch (Exception e) {
            log.error("서버 파싱 에러"); // 사실상 거의 절대적으로 파싱에러가 날 일이 없는 코드이지만 try-catch로 예외처리 해둠
        }
    }
    */

}
