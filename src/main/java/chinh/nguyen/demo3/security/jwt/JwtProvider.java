package chinh.nguyen.demo3.security.jwt;

import chinh.nguyen.demo3.security.userprincal.Userprinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret = "jwtChinhNguyenSecretKey";
    private int jwtExpiration = 86400;
    public String createToken(Authentication authentication){
        Userprinciple userprinciple = (Userprinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userprinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e){
            logger.error("Invalid JWT signature -> Message: {}", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message {}", e);
        } catch (ExpiredJwtException e){
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e){
            logger.error("JWT string is empty -> Message {}", e);
        }
        return false;
    }
    public String getUserNameFromJwtToken(String token){
        String userName = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }
}
