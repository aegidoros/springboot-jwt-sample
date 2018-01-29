package com.aer.security;

import com.aer.common.TimeProvider;
import com.aer.model.ApiClient;
import com.aer.model.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.cas.jackson2.CasJackson2Module;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.web.jackson2.WebJackson2Module;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by fan.jin on 2016-10-19.
 */

@Component
public class TokenHelper {

    @Value("${app.name}")
    private String APP_NAME;

    @Value("${jwt.secret}")
    public String SECRET;

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.header}")
    private String AUTH_HEADER;


    @Autowired
    TimeProvider timeProvider;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public LdapUserDetails getLdapUserDetailFromToken(String token) {
        final Claims claims = this.getAllClaimsFromToken(token);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.registerModule(new CoreJackson2Module());
        objectMapper.registerModule(new CasJackson2Module());
        objectMapper.registerModule(new WebJackson2Module());
        LdapUserDetailsImpl user = null;
        try {
            user = objectMapper.readValue((String) claims.get("user"), LdapUserDetailsImpl.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public UserDetails getUserDetailFromToken(String apiToken) {
        final Claims claims = this.getAllClaimsFromToken(apiToken);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.registerModule(new CoreJackson2Module());
        objectMapper.registerModule(new CasJackson2Module());
        objectMapper.registerModule(new WebJackson2Module());
        ApiClient user = null;
        try {
            user = objectMapper.readValue((String) claims.get("user"), ApiClient.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String refreshToken(String token) {
        String refreshedToken;
        Date a = timeProvider.now();
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.setIssuedAt(a);
            refreshedToken = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate())
                    .signWith(SIGNATURE_ALGORITHM, SECRET)
                    .compact();
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public String generateToken(LdapUserDetailsImpl user) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
            objectMapper.registerModule(new CoreJackson2Module());
            objectMapper.registerModule(new CasJackson2Module());
            objectMapper.registerModule(new WebJackson2Module());
            String userDetailAsString = objectMapper.writeValueAsString(user);

            return Jwts.builder()
                    .claim("user", userDetailAsString)
                    .setIssuer(APP_NAME)
                    .setSubject(user.getUsername())
                    .setIssuedAt(timeProvider.now())
                    .setExpiration(generateExpirationDate())
                    .signWith(SIGNATURE_ALGORITHM, SECRET)
                    .compact();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(timeProvider.now().getTime() + EXPIRES_IN * 1000);
    }

    public int getExpiredIn() {
        return EXPIRES_IN;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        //LdapUserDetailsImpl user = (LdapUserDetailsImpl) userDetails;
        final String username = getUsernameFromToken(token);
        return (
                username != null &&
                        username.equals(userDetails.getUsername()));
    }

//    public Boolean validateToken(String token, UserDetails userDetails) {
//        ApiClient user = (ApiClient) userDetails;
//        final String username = getUsernameFromToken(token);
//        return (
//                username != null &&
//                        username.equals(user.getUsername()));
//    }


    public String getToken(HttpServletRequest request) {
        /**
         *  Getting the token from Authentication header
         *  e.g Bearer your_token
         */
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else {
            return request.getHeader("Api-Token");
        }

        //  return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

    public String generateToken(ApiClient apiClient) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
            objectMapper.registerModule(new CoreJackson2Module());
            objectMapper.registerModule(new CasJackson2Module());
            objectMapper.registerModule(new WebJackson2Module());
            String apliClient = objectMapper.writeValueAsString(apiClient);

            return Jwts.builder()
                    .claim("user", apliClient)
                    .setIssuer(APP_NAME)
                    .setSubject(apiClient.getUsername())
                    .signWith(SIGNATURE_ALGORITHM, SECRET)
                    .compact();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public boolean isApiToken(String username) {
        //String pattern= "[a-f_0-9]{8}-([a-f_0-9]{4}-){3}[a-f_0-9]{8}";
        Pattern pattern = Pattern.compile("[a-f_0-9]{8}-([a-f_0-9]{4}-){3}[a-f_0-9]{8}");
        Matcher matcher = pattern.matcher(username);
        return matcher.find();
    }

}