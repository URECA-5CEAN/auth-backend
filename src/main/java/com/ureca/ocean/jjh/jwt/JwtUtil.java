package com.ureca.ocean.jjh.jwt;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

//import com.mycom.myapp.config.MyUserDetailsService;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//jwt 생성, 검증, ...
@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtUtil {
    //application.propertiest에 있는 값을 여기에다가 hard coding해도 ㄱㄴ
    @Value("${myapp.jwt.secret}") //application.properties에 있는 값을 가져올 때, springframework에서 제공
    private String secretKeyStr;
    private SecretKey secretKey;
    private final long tokenValidDuration = 1000L * 60 * 60 * 24;//한시간, 즉, 1000L==1초,60초 * 60분

    @PostConstruct //JwtUtil의 생성자가 호출되고 나면, 이것이 바로 호출되게 한다. jakarata에서 제공
    private void init() {
        System.out.println(secretKeyStr);

        secretKey= new SecretKeySpec(secretKeyStr.getBytes(StandardCharsets.UTF_8), //utf_8로 푼다음에,
                Jwts.SIG.HS256.key().build().getAlgorithm()
        ); //이렇게 하면 암호화 알고리즘을 이용한 key가 생성이 된다. (그 무작위의 str을 이용한)
        System.out.println(secretKey); //실제로 만들어지는 키
    }

    //jwt 생성
    //username(subject),role
    public String createToken(String username) {
        //발급일자, 만료일자
        Date now = new Date();
        log.debug("createToken");
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + tokenValidDuration))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }
}
