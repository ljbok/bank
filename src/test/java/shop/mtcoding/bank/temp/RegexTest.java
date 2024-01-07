package shop.mtcoding.bank.temp;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class RegexTest {

    @Test
    public void 한글만된다_test() throws Exception { // ^[가-힣]+&
        String value = "";
        //boolean result = Pattern.matches("정규표현식",value);
        boolean result = Pattern.matches("^[가-힣]+$",value); // [가-힣] : 한글 , ^a : a로 시작하는 거, a$ : a로 끝나는 거
        System.out.println("테스트 : " + result);
    }

    @Test
    public void 한글은안된다_test() throws Exception {
        String value = "홍길동";
        //boolean result = Pattern.matches("정규표현식",value);
        boolean result = Pattern.matches("^[^ㄱ-ㅎ가-힣]*$",value); // [^가-힣] : 한글 or 한글초성의 부정 , ^a : a로 시작하는 거, a$ : a로 끝나는 거
        System.out.println("테스트 : " + result);
    }

    @Test
    public void 영어만된다_test() throws Exception {
        String value = "홍길동";
        //boolean result = Pattern.matches("정규표현식",value);
        boolean result = Pattern.matches("^[a-zA-Z]+$",value); // [가-힣] : 한글 , ^a : a로 시작하는 거, a$ : a로 끝나는 거
        System.out.println("테스트 : " + result);
    }

    @Test
    public void 영어는안된다_test() throws Exception {
        String value = "";
        //boolean result = Pattern.matches("정규표현식",value);
        boolean result = Pattern.matches("^[^a-zA-Z]*$",value); // [^가-힣] : 한글 or 한글초성의 부정 , ^a : a로 시작하는 거, a$ : a로 끝나는 거
        System.out.println("테스트 : " + result);
    }

    @Test
    public void 영어와숫자만된다_test() throws Exception {
        String value = "ssar12가";
        //boolean result = Pattern.matches("정규표현식",value);
        boolean result = Pattern.matches("^[a-zA-Z0-9]+$",value); // [가-힣] : 한글 , ^a : a로 시작하는 거, a$ : a로 끝나는 거
        System.out.println("테스트 : " + result);
    }

    @Test
    public void 영어만되고_길이는최소2최대4이다_test() throws Exception {
        String value = "abcd";
        //boolean result = Pattern.matches("정규표현식",value);
        boolean result = Pattern.matches("^[a-zA-Z]{2,4}$",value); // [가-힣] : 한글 , ^a : a로 시작하는 거, a$ : a로 끝나는 거
                                                                        // {2,4} 길이가 2에서 4만 된다.
        System.out.println("테스트 : " + result);
    }

    // username, email, fullname 정도 테스트 해보자

    // #1 username : 영문으로 2~20자
    @Test
    public void user_username_test() throws Exception {
        String username = "ssar";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,20}$", username);
        System.out.println("테스트 : " + result);
    }

    // #2 fullname : 영문, 한글 1 ~ 20자
    @Test
    public void user_fullname_test() throws Exception {
        String fullname = "쌀qwe1";
        boolean result = Pattern.matches("^[a-zA-z가-힣]{1,20}$", fullname);
        System.out.println("테스트 : " + result);
    }

    // #3 email 이메일 형식 .com만 가능하게 했음 나는
    @Test
    public void user_email_test() throws Exception {
        String email = "ssar@nate.com";
        boolean result = Pattern.matches("^[a-zA-z0-9]{2,10}@[a-zA-z0-9]{2,6}\\.[a-zA-z]{2,3}$", email); // '.' 이 메타문자이기 때문에
                                                                                                            // \\. 로 메타문자가 아님을 표시해야 한다.
        System.out.println("테스트 : " + result);
    }
}
