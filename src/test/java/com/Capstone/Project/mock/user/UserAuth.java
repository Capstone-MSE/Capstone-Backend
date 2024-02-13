package com.Capstone.Project.mock.user;

import com.Capstone.Project.global.auth.jwt.JwtAuthentication;
import com.Capstone.Project.global.auth.role.UserRole;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuth {
    public static void withUser(Long userId) {
        SecurityContextHolder.getContext()
                .setAuthentication(new JwtAuthentication(userId, UserRole.USER));
    }

    public static void withAdmin(Long userId) {
        SecurityContextHolder.getContext()
                .setAuthentication(new JwtAuthentication(userId, UserRole.ADMIN));
    }
}
