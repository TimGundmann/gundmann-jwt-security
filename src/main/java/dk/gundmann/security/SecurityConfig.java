package dk.gundmann.security;

import java.util.Base64;

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

	public String getSecret() {
		if (this.secret != null) {
			return Base64.getEncoder().encodeToString(this.secret.getBytes());
		}
		return this.secret;
	}

}
