package com.ems.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.ems.common.constant.SecurityConstants;
import com.ems.common.exception.BadRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.CollectionUtils;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static javax.crypto.Cipher.SECRET_KEY;
import static net.sf.jsqlparser.util.validation.metadata.NamedObject.role;

/**
 * @program: ems-admin-boot
 * @description: this is a class
 * @author: starao
 * @create: 2021-11-27 12:47
 **/
@Slf4j
public class JwtUtil {

    private static final String secretKey = SecurityConstants.JWT_SECRET_KEY;

    private static final String refreshKey = SecurityConstants.JWT_REFRESH_KEY;

    private JwtUtil(){
        throw new IllegalStateException("禁止创建当前对象");
    }

    /**
     * 生成 SecretKey
     * @return
     */
    private static SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
    * @Description: 根据用户和用户角色生成 token
    * @Param: [userName, roles, isRemember]
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static String generateToken(String userId, String userName, List<String> roles, Boolean isRemember){
        try {
            //  过期时间
            long expirationTime = isRemember ? SecurityConstants.TOKEN_EXPIRATION_REMEMBER_TIME : SecurityConstants.TOKEN_EXPIRATION_TIME;

            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + expirationTime);

            // 创建自定义声明
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", userName);
            claims.put(SecurityConstants.TOKEN_ROLE_CLAIM, roles);
            //  生成token
            return Jwts.builder()
                    .subject(userId)
                    .claims(claims)                            // 批量添加声明
                    .issuer(SecurityConstants.TOKEN_ISSUER)                   // 签发者
                    .audience().add(SecurityConstants.TOKEN_AUDIENCE).and()     // 受众
                    .issuedAt(now)                             // 签发时间
                    .expiration(expiryDate)                    // 过期时间
                    .notBefore(now)                            // 生效时间
                    .id(java.util.UUID.randomUUID().toString()) // JWT ID
                    .signWith(getSigningKey())                 // 使用密钥签名
                    .compact();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
    * @Description: 生成refresh_token
    * @Param: [userName]
    * @return: java.lang.String
    * @Author: starao
    * @Date: 2022/10/5
    */
    public static String getRefreshToken(String userId, String userName){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + SecurityConstants.TOKEN_EXPIRATION_REMEMBER_TIME * 1000);
        return Jwts.builder()
                .subject(userId)
                .claim("username", userName)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
    * @Description: 校验token是否有效
    * @Param: [token]
    * @return: boolean
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static boolean verifyToken(String token){
        try {
            getTokenBody(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("token 已过期 : {} failed : {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("token 不支持 : {} failed : {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("token 格式不正确 : {} failed : {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (SignatureException e) {
            log.warn("token 签名无效 : {} failed : {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("token 不能为空 : {} failed : {}", token, e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
    * @Description: 根据token获取用户认证信息
    * @Param: [token]
    * @return: org.springframework.security.core.Authentication
    * @Author: starao
    * @Date: 2021/11/27
    */
    public static Authentication getAuthentication(String token) {
        Claims claims;
        try {
            claims = getTokenBody(token);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            claims = e.getClaims();
        }
        //  获取用户角色
        String string = claims.get(SecurityConstants.TOKEN_ROLE_CLAIM).toString();
        List<String> roles = JSON.parseObject(string, new TypeReference<>() {});
        List<SimpleGrantedAuthority> authorities =
                CollectionUtils.isEmpty(roles) ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) :
                        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        //  获取用户名
        User principal = new User(claims.getSubject(), "******", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
    * @Description: 从token中获取用户信息
    * @Param: [token]
    * @return: io.jsonwebtoken.Claims
    * @Author: starao
    * @Date: 2021/11/27
    */
    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    /**
    * @Description: 从refreshToken中获取用户信息
    * @Param: [refreshToken]
    * @return: io.jsonwebtoken.Claims
    * @Author: starao
    * @Date: 2022/10/5
    */
    public static Claims getRefreshTokenBody(String refreshToken){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();
    }
}
