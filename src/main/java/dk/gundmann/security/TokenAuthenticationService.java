package dk.gundmann.security;

import static java.util.Collections.emptyList;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security
            .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;

class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "kirurgsik";
    public static final String HEADER_STRING = "Authorization";

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            return parseToken(token)
            		.map(user -> new UsernamePasswordAuthenticationToken(user, null, emptyList()))
            		.orElse(null);
        }
        return null;
    }

	private static Optional<String> parseToken(String token) {
		return Optional.ofNullable(Jwts.parser()
		        .setSigningKey(SECRET)
		        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
		        .getBody()
		        .getSubject());
	}
    
}