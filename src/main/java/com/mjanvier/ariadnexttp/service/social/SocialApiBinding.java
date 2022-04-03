package com.mjanvier.ariadnexttp.service.social;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

public abstract class SocialApiBinding {

    protected RestTemplate restTemplate;
    protected String userInfoEndpointUri;

    protected SocialApiBinding(String accessToken, String userInfoEndpointUri) {
        this.userInfoEndpointUri = userInfoEndpointUri;
        this.restTemplate = new RestTemplate();
        if (accessToken != null) {
            this.restTemplate.getInterceptors()
                    .add(getBearerTokenInterceptor(accessToken));
        } else {
            this.restTemplate.getInterceptors().add(getNoTokenInterceptor());
        }
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        return (request, bytes, execution) -> {
            request.getHeaders().add(
                    "Authorization", "Bearer " + accessToken);
            return execution.execute(request, bytes);
        };
    }

    private ClientHttpRequestInterceptor getNoTokenInterceptor() {
        return (request, bytes, execution) -> {
            throw new IllegalStateException(
                    "Can't access the API without an access token");
        };
    }

    public String getUserName() {
        final Map userAttributes = restTemplate.getForObject(userInfoEndpointUri, Map.class);
        return Optional.ofNullable(userAttributes)
                .map(u -> (String) u.get("name"))
                .orElseThrow(() -> new IllegalStateException("Unable to get user info from the Social network"));
    }

    public abstract boolean isRealUser();

}
