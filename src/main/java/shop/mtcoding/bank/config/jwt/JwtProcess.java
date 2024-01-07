package shop.mtcoding.bank.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shop.mtcoding.bank.config.outh.LoginUser;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import java.util.Date;

public class JwtProcess {
    private final Logger log = LoggerFactory.getLogger(getClass());

    // 토큰 생성, 토큰 검증을 하려면 간단하게 클래스 하나를 만들어놔야 한다.
    // 토큰은 암호화가 되어있는 것이 아니기 때문에 많은 정보를 담으면 좋지 않다.
    // 따라서 우리는 토큰을 생성할 때 user의 id와 user의 role 만 저장한다!

    // 토큰 생성
    public static String create(LoginUser loginUser){
        String jwtToken = JWT.create()
                .withSubject("bank") // 토큰의 제목이라 아무렇게나 써도 됨.
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRATION_TIME)) // 토큰 생성 시간 + 7일 (JwtVO에서 설정해둔)
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("role",loginUser.getUser().getRole().name()) // getRole().name() 꼭 name() 으로 반화놔는 거 유의하자!
                .sign(Algorithm.HMAC512(JwtVO.SECRET));

        return JwtVO.TOKEN_PREFIX+jwtToken;
    }

    // 토큰 검증 (return 되는 LoginUser 객체를 강제로 시큐리티 세션에 직접 주입할 예정)
    public static LoginUser verify(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVO.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.builder().id(id).role(UserEnum.valueOf(role)).build(); // role 문자열이기 때문에 Enum 타입으로 바꿔줘야 한다.
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }

}
