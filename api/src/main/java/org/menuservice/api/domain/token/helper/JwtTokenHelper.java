package org.menuservice.api.domain.token.helper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.menuservice.api.common.error.TokenErrorCode;
import org.menuservice.api.common.exception.ApiException;
import org.menuservice.api.domain.token.model.TokenDto;
import org.menuservice.api.domain.token.ifs.TokenHelperIfs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper implements TokenHelperIfs {

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    Long refreshTokenPlusHour;

    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {

        var expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);
        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()
        );

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var jwtToken = Jwts.builder()
                .signWith(key)  // SignatureAlgorithm.HS256 제거, key만 사용
                .claims(data)   // setClaims 대신 claims 사용
                .expiration(expiredAt)  // setExpiration 대신 expiration 사용
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();

//        var expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);
//        var expiredAt = Date.from(
//                expiredLocalDateTime.atZone(
//                        ZoneId.systemDefault()
//                ).toInstant()
//        );
//
//        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
//
//        var jwtToken = Jwts.builder()
//                .signWith(SignatureAlgorithm.HS256, key)
//                .setClaims(data)
//                .setExpiration(expiredAt)
//                .compact();
//
//
//        return TokenDto.builder()
//                .token(jwtToken)
//                .expiredAt(expiredLocalDateTime)
//                .build();
    }

    @Override
    public TokenDto issueeRefreshToken(Map<String, Object> data) {

//        var expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);
//        var expiredAt = Date.from(
//                expiredLocalDateTime.atZone(
//                        ZoneId.systemDefault()
//                ).toInstant()
//        );
//
//        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
//
//        var jwtToken = Jwts.builder()
//                .signWith(SignatureAlgorithm.HS256, key)
//                .setClaims(data)
//                .setExpiration(expiredAt)
//                .compact();
//
//
//        return TokenDto.builder()
//                .token(jwtToken)
//                .expiredAt(expiredLocalDateTime)
//                .build();
        var expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);
        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()
        );

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var jwtToken = Jwts.builder()
                .signWith(key)  // SignatureAlgorithm.HS256 제거, key만 사용
                .claims(data)   // setClaims 대신 claims 사용
                .expiration(expiredAt)  // setExpiration 대신 expiration 사용
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {

//        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
//        var parser = Jwts.parser().setSigningKey(key).build();
//
//        try{
//            var result = parser.parseClaimsJws(token);
//            return new HashMap<String, Object>(result.getBody());
//
//        }catch (Exception e){
//
//            if(e instanceof SignatureException){
//                throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
//            }
//            else if(e instanceof ExpiredJwtException){
//                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
//            }
//            else{
//                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
//            }
//        }
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var parser = Jwts.parser()
                .verifyWith(key)  // setSigningKey 대신 verifyWith 사용 (최신 API)
                .build();

        try {
            var result = parser.parseSignedClaims(token);  // parseClaimsJws 대신
            return new HashMap<String, Object>(result.getPayload());  // getBody 대신 getPayload

        } catch (Exception e) {
            if (e instanceof SignatureException) {
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
            }
            else if (e instanceof ExpiredJwtException) {
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
            }
            else {
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
            }
        }
    }
}
