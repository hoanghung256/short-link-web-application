package com.example.shortlinkapplication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oAuth2 = new OAuth2();
    public static class Auth {
        private String tokenSecret;
        private long tokenExpiration;
    }

    public static class OAuth2 {
        private List<String> authorizedRedirectUri = new ArrayList<>();
    }
}
