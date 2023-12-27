package shop.mtcoding.bank.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import shop.mtcoding.bank.handler.ex.CustomValidationException;

import java.util.HashMap;
import java.util.Map;

@Component // 빈으로 등록시켜야 하는데 설정 파일이 아니므로 @Configuration 이 아니라 @Component로 등록한다.
@Aspect // 관점
public class CustomValidationAdvice {

    // pointcut 만들기 - " 어디에 적용시킬지 "
    // 우선 이 어플리케이션에서는 유효성 검사가 바디 데이터로 묶이는 곳에만 필요하다.
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() {
    }

    // 이제 어드바이스가 필요하다 포인트컷에 어떤 동작을 시킬 것인지
    // 적용 형식에는 @Before, @After 가 있다 하지만 @Around 로 걸자
    // Before는 그 메소드가 실행되기 전, After는 그 메소드가 실행된 후, Around 은 전-후 제어가 가능
    @Around("postMapping() || putMapping()") // @Pointcut 이 붙어있는 메소드 중 postMapping 과 putMapping 메소드에
    // 적용할 기능 정의하는 메소드이다 라는 의미
    public Object validationAdObject(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs(); // joinpoint(실행시킨 회원가입등의 메소드)의 매개변수 가지고옴 , proceeding 조인 포인트를 받아서
        for (Object arg : args) { // 그 매개변수에 BindingResult가 있는데 유효성 검사 에러 발생시 그 에러가 BindingResult에 담기게 됨
            if (arg instanceof BindingResult) { // arg의 인스턴스에 BindingResult 라는게 만약 있다면
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {    // bindingResult 가 에러를 가지고 있다면
                    Map<String, String> errorMap = new HashMap<>();

                    // 묶인 bindingResult 이 fieldError 들을 for문 돌리면서 errorMap에 필드와 메시지를 담는다.
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패", errorMap);
                } // if2

            } // if1
        } // for

        // return 코드 필요
        return proceedingJoinPoint.proceed(); // if2(에러발견 조건문)을 타지 않으면
        // 이 return으로 넘어오게 되는데 proceedingJoinPoint.proceed() => 정상적으로 해당 메소드를 실행하라
    }
}

/*
    http 신호 종류
    get, delete, post(body), put(body)
*/