package shop.mtcoding.bank.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

@RestControllerAdvice
public class CustomExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e){
        log.error(e.getMessage());
        // 실패했으니깐 code 는 -1 메시지는 e.getMessage(), 돌려줄 데이터는 없으니깐 null
        return new ResponseEntity<>(new ResponseDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
    }
}