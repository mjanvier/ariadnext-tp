package com.mjanvier.ariadnexttp.service.social.google;

import com.mjanvier.ariadnexttp.service.social.SocialApiBinding;

public class Google extends SocialApiBinding {

    public Google(String accessToken, String userInfoEndPoint) {
        super(accessToken, userInfoEndPoint);
    }

    @Override
    public boolean isRealUser() {
        // TODO implémenter la vérification de personne réelle
        return false;
    }

}
