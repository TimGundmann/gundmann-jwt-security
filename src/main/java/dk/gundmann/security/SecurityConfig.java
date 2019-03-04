package dk.gundmann.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "dk.gundmann.security")
@Data
public class SecurityConfig {

	private String secret;
	private String tokenPrefix;
	private String headerString;
	private String mailPassword;
	private String syspassword;

}
