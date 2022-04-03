package com.mjanvier.ariadnexttp.config;

import com.mjanvier.ariadnexttp.service.social.SocialApiBinding;
import com.mjanvier.ariadnexttp.service.social.facebook.Facebook;
import com.mjanvier.ariadnexttp.service.social.google.Google;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class SocialConfig {

    @Bean
    @RequestScope
    public SocialApiBinding currentUserApiBinding(OAuth2AuthorizedClientService clientService) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getClass()
                .isAssignableFrom(OAuth2AuthenticationToken.class)) {
            final OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            final String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            final OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());

            final String accessToken = client.getAccessToken().getTokenValue();
            final String userInfoEndPoint = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();

            if (clientRegistrationId.equals("facebook")) {
                return new Facebook(accessToken, userInfoEndPoint);
            } else if (clientRegistrationId.equals("google")) {
                return new Google(accessToken, userInfoEndPoint);
            }
        }

        throw new IllegalStateException("Unable to get ApiBinding for current user");

    }
}
