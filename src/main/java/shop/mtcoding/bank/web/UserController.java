package shop.mtcoding.bank.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.user.UserReqDto;
import shop.mtcoding.bank.dto.user.UserRespDto;
import shop.mtcoding.bank.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor // 빈 자동 주입 private fianl 로 생성한 클래스들
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    /*
    [ReponseEntity 가 스프링이 제공하는 어떤 기본 클래스인지 보기 위한 메모]
    public ResponseEntity(@Nullable T body, HttpStatus status) {
		this(body, null, status);
	}
    */

    // 회원강비 자체는 인증이 필요없이 때문에 "/api/s/**" 이런 꼴이 아니라 "/api/join" 이런 꼴이 될 것이다.
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserReqDto.JoinReqDto joinReqDto, BindingResult bindingResult){
        // 회원가입 시 넘어오는 데이터(폼데이터)를 json으로 받으려면

       /*
       // --> aop/CustomValidationAdvice.java 로 이동함
        if (bindingResult.hasErrors()) {    // bindingResult 가 에러를 가지고 있다면
            Map<String, String> errorMap = new HashMap<>();

            // 묶인 bindingResult 이 fieldError 들을 for문 돌리면서 errorMap에 필드와 메시지를 담는다.
            for (FieldError error: bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            // -1 이란 에러 발생 식별 코드, 유효성 검사 실패했다는 메시지, 에러 묶음 데이터를 반환한다.
            return new ResponseEntity<>(new ResponseDto<>(-1, "유효성 검사 실패" ,errorMap), HttpStatus.BAD_REQUEST);
        }
        */
        // @RequestBody 필요
        UserRespDto.JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinRespDto), HttpStatus.CREATED);
        // └→ 성공 여유 식별용 커스텀 코드 , 메시지, requestDto 객체, 상태 코드  이렇게 반환한다.
    }
}