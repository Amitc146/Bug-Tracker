package com.amit.bugtracker.security;

import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;

    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        // Place user in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // Forward to home page

        response.sendRedirect(request.getContextPath() + "/");
    }

}