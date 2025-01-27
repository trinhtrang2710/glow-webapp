package trangtt.glow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class TokenProvider implements Serializable {
    @Value("${security.access_token_validity_seconds}")
    private String accessTokenValiditySeconds;

    @Value("${security.signing-key}")
    private String signingKey ;

    @Value("${security.authorities_key}")
    private String authoritiesKey;

    public String getUsernameFromToken(String token) {
        return "";
    }
}
