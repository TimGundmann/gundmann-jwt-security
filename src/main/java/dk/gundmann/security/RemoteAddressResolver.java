package dk.gundmann.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;


@Component
public class RemoteAddressResolver {

	public String remoteAdress(HttpServletRequest request) {
		String xForward = request.getHeader("x-forwarded-for");
		if (xForward != null && !xForward.isEmpty()) {
			return xForward; 
		}
		return request.getRemoteAddr();
	}
	
}
