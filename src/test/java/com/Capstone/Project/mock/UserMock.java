package com.Capstone.Project.mock;


import com.Capstone.Project.domain.user.model.Gender;
import com.Capstone.Project.domain.user.model.UserStatus;
import com.Capstone.Project.domain.user.model.entity.User;
import com.Capstone.Project.global.auth.role.UserRole;
import com.Capstone.Project.util.EntityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMock {

    public static final String NICKNAME = "nickname";

    public static final String NAME = "username";

    public static final String PASSWORD = "password";

    public static User createDummyUser() {
        return createDummyUser(RandomGen.nextLong());
    }

    public static User createDummyUser(Long userId) {
        return create(userId, NAME, NICKNAME, UserRole.USER, null);
    }

    public static User create(Long userId, String name, String nickname, UserRole userRole, PasswordEncoder passwordEncoder) {
        String password = PASSWORD;

        if (passwordEncoder != null) {
            password = passwordEncoder.encode(password);
        }

        User user = User.builder()
                .name(name)
                .nickname(nickname)
                .password(password)
                .phone("01011112222")
                .age("24")
                .gender(Gender.MALE)
                .userRole(userRole)
                .status(UserStatus.ACTIVE)
                .build();

        EntityUtil.injectId(User.class, user, userId);
        return user;
    }

}
