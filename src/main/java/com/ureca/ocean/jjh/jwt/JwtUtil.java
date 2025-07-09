//package com.example.authbackend.jwt;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.List;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
////import com.mycom.myapp.config.MyUserDetailsService;
//
//import io.jsonwebtoken.Jwts;
//import jakarta.annotation.PostConstruct;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
////jwt 생성, 검증, ...
//@Component
//@RequiredArgsConstructor
//@Getter
//@Slf4j
//public class JwtUtil {
//    //application.propertiest에 있는 값을 여기에다가 hard coding해도 ㄱㄴ
//    @Value("${myapp.jwt.secret}") //application.properties에 있는 값을 가져올 때, springframework에서 제공
//    private String secretKeyStr;
//    private SecretKey secretKey;
//    private final long tokenValidDuration = 1000L * 60 * 60 * 24;//한시간, 즉, 1000L==1초,60초 * 60분
//
//    //security에서 사용하는 인증용 사용자 객체
//    private final MyUserDetailsService myUserDetailsService;
//
//    @PostConstruct //JwtUtil의 생성자가 호출되고 나면, 이것이 바로 호출되게 한다. jakarata에서 제공
//    private void init() {
//        System.out.println(secretKeyStr);
//
//        secretKey= new SecretKeySpec(secretKeyStr.getBytes(StandardCharsets.UTF_8), //utf_8로 푼다음에,
//                Jwts.SIG.HS256.key().build().getAlgorithm()
//        ); //이렇게 하면 암호화 알고리즘을 이용한 key가 생성이 된다. (그 무작위의 str을 이용한)
//        System.out.println(secretKey); //실제로 만들어지는 키
//    }
//
//    //jwt 생성
//    //username(subject),role
//    public String createToken(String username, List<String> roles) {
//        //발급일자, 만료일자
//        Date now = new Date();
//        log.debug("createToken");
//        return Jwts.builder()
//                .subject(username)
//                .claim("roles", roles)
//                .issuedAt(now)
//                .expiration(new Date(now.getTime() + tokenValidDuration))
//                .signWith(secretKey, Jwts.SIG.HS256)
//                .compact();
//    }
//
//    // UserDetailsService 를 통해 사용자 인증된 객체를 얻고
//    //이를 통해서 UsernamePasswordAuthenticationToken 객체를 만들어 리턴
//    //유효성 검증을 아래 메소드를 통해서 DB를 통한 검증을 진행하는 건 (많은 블로그 상의 코드)token 발급 기간이 길면 발급 시점의 UserDetails와  현재 UserDetails가 다를 수 있다는 점 강조
//    //반대로 client가 접속할 때마다, DB Access를 해야함. <= 이건 큰 부담.
//    //그래서 우리는 만료일자만 validateToken 함수로 만료일자만 검증하겠다는 것
//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = myUserDetailsService.loadUserByUsername(this.getUsernameFromToken(token)); //username은 실제 값은 email 사용
//        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),"",userDetails.getAuthorities()); //username, password, role을 이용해서 authenticationtoken을 가져와서 검증을 하겠다.
//    }
//
//    //jwt로 부터 username을 추출
//    public String getUsernameFromToken(String token) {
//        return Jwts.parser()
//                .verifyWith(secretKey)
//                .build()
//                .parseSignedClaims(token).getPayload()
//                .getSubject();
//    }
//
//    //request로 부터 token획득
//    //client가 header에"X-AUTH-TOKEN"
//    public String getTokenFromHeader(HttpServletRequest request) {
//        return request.getHeader("X-AUTH-TOKEN");
//    }
//}
