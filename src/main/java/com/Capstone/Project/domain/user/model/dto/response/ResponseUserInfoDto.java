package com.Capstone.Project.domain.user.model.dto.response;

import com.Capstone.Project.domain.user.model.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseUserInfoDto {

    private final String name;
    private final String nickname;
    private final String phone;
    private final String age;
    private final Gender gender;
    private final boolean isAdmin;

}
