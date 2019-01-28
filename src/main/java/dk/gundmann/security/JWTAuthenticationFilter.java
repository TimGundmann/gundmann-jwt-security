package dk.gundmann.security;

import static java.util.Collections.emptyList;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;

public class JWTAuthenticationFilter extends GenericFilterBean {

	private SecurityConfig securityConfig;

	public JWTAuthenticationFilter(SecurityConfig securityConfig) {
		this.securityConfig = securityConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(getAuthentication((HttpServletRequest) request));
		filterChain.doFilter(request, response);
	}
	
    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(securityConfig.getHeaderString());
        if (token != null) {
            return parseToken(token)
            		.map(user -> new UsernamePasswordAuthenticationToken(user, null, emptyList()))
            		.orElse(null);
        }
        return null;
    }

	private Optional<String> parseToken(String token) {
		return Optional.ofNullable(Jwts.parser()
		        .setSigningKey(securityConfig.getSecret())
		        .parseClaimsJws(token.replace(securityConfig.getTokenPrefix(), ""))
		        .getBody()
		        .getSubject());
	}

}