package com.basic.crud.security.jwt;

import com.basic.crud.security.dto.AuthInfo;
import com.basic.crud.security.dto.UserData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
    private SecretKey secretKey;

    // 시크릿키 준비
    @PostConstruct
    void init() {
        byte[] decodedKeyByteList = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKeyByteList);
    }

    // 토큰 발급 기능
    public String createToken(UserData userData) {

        // 데이터 준비
        Long memberId = userData.getMemberId();
        String role = userData.getRole();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 60);

        // 토큰 만들기
        String jwt = Jwts.builder()
                .issuer("localhost:8080")
                .subject(memberId.toString()) // 유저 아이디
                .expiration(expiration) // 만료 시간
                .issuedAt(now) // 발급 시간
                .claim("role", role)
                .signWith(secretKey)
                .compact();

        return jwt;
    }

    // 토큰 검증 기능
    public AuthInfo verifyToken(String authorization) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization 헤더가 올바르지 않습니다.");
        }

        String token = authorization.substring(7); // "Bearer " 제거

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            Claims claims = claimsJws.getPayload();

            Long memberId = Long.valueOf(claims.getSubject());
            String role = claims.get("role", String.class);

            AuthInfo authInfo = new AuthInfo(memberId, role);
            return authInfo;

        } catch (JwtException ex) {
            throw ex;
        }
    }
}
