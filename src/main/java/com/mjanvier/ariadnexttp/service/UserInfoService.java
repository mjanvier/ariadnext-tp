package com.mjanvier.ariadnexttp.service;

import com.mjanvier.ariadnexttp.service.social.SocialApiBinding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final SocialApiBinding currentUserSocialApiBinding;

    public String getUserName() {
        return currentUserSocialApiBinding.getUserName();
    }
}
