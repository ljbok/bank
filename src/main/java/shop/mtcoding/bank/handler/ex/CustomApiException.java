package shop.mtcoding.bank.handler.ex;

// 내가 흔히 쓰턴 IlligalException("전송할 메시지 내용") --> 이런 런타임예외를 내가 커스텀해서 만든 것!
public class CustomApiException extends RuntimeException{
    public CustomApiException(String message) {
        super(message);
    }
}
