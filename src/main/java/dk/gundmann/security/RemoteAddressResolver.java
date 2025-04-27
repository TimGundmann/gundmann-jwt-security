package dk.gundmann.security;

import jakarta.servlet.http.HttpServletRequest;


public class RemoteAddressResolver {

	public String remoteAdress(HttpServletRequest request) {
		String xForward = request.getHeader("x-forwarded-for");
		if (xForward != null && !xForward.isEmpty()) {
			return xForward; 
		}
		return request.getRemoteAddr();
	}
	
}
