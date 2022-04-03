package com.mjanvier.ariadnexttp.service.social.facebook;

import com.mjanvier.ariadnexttp.service.social.SocialApiBinding;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class Facebook extends SocialApiBinding {

    private static final String GRAPH_API_BASE_URL =
            "https://graph.facebook.com/";

    public Facebook(String accessToken, String userInfoEndpointUri) {
        super(accessToken, userInfoEndpointUri);
    }

    @Override
    public boolean isRealUser() {
        return !getFeed().isEmpty();
    }

    public List<Post> getFeed() {
        return Optional.ofNullable(
                restTemplate.getForObject(GRAPH_API_BASE_URL + "/me/feed", Feed.class))
                .map(Feed::getData)
                .orElse(Collections.emptyList());
    }

}
