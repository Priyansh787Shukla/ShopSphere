package com.endeavour.ShopSphere.config;

import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.service.JwtService;
import com.endeavour.ShopSphere.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler
{
    private final JwtService jwtService;
    private final UserService userService;

    public OAuth2LoginSuccessHandler(JwtService jwtService, UserService userService)
    {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        User user = userService.getUserByEmail(email);
        String token = jwtService.generateToken(user);
        response.setContentType("application/json");
        response.getWriter().write("{\n" +
                "    \"token\" : "+token+"\n" +
                "}");
    }
}
