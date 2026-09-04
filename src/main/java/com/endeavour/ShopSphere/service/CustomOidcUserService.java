package com.endeavour.ShopSphere.service;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser>
{
    private final OidcUserService oidcUserService = new OidcUserService();
    private final UserService userService;

    public CustomOidcUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest)
            throws OAuth2AuthenticationException
    {
        OidcUser oidcUser = oidcUserService.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        userService.registerOrUpdateUser(provider, oidcUser);

        return oidcUser;
    }
}