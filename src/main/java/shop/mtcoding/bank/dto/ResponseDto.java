package shop.mtcoding.bank.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {

    private final Integer code; // 1 : 성공 |  -1 : 실패
    private final String msg;
    private final T data; // 돌려줄 데이터는 다양할 수도 있으니깐 제네릭 필요
}
