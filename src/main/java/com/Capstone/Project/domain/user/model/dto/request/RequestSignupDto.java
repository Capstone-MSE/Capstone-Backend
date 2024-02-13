package com.Capstone.Project.domain.user.model.dto.request;

import com.Capstone.Project.domain.user.model.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestSignupDto {

    @NotBlank
    @Schema(description = "이름", example = "홍길동")
    private final String name;

    @NotBlank
    @Size(min = 3, max = 16)
    @Pattern(regexp = "^(?!.*\\s{2,})[A-Za-z\\dㄱ-ㅎㅏ-ㅣ가-힣_ ]{3,16}$")
    @Schema(description = "닉네임", example = "user1")
    private final String nickname;

    @NotBlank
    @Schema(description = "나이", example = "20")
    private final String age;

    @Schema(description = "성별", example = "MALE")
    private final Gender gender;

    @NotBlank
    @Size(min = 11, max = 11)
    @Schema(description = "전화번호", example = "01012345678")
    private final String phone;

    @NotBlank
    @Size(min = 3, max = 200)
    @Schema(description = "비밀번호", example = "12345678")
    private final String password;
}
