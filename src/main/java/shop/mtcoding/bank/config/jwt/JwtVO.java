package shop.mtcoding.bank.config.jwt;

/*
   SECRET은 노출되면 안된다. (환경변수, 클라우드AWS 에 등록한 후 꺼내쓰는 것이 좋다 또는 파일에 있는 거 읽을 수도 있고)
   리플래시 토큰 (지금은 구현하지 않는다.)
*/
public interface JwtVO {
    public static final String SECRET = "메타코딩"; // HS256 (대칭키)
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;  // 만료시간 일주일 (1000 이 1초)
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER = "Authorization";

}
