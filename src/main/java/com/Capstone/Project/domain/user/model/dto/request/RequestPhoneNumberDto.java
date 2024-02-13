package com.Capstone.Project.domain.user.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RequestPhoneNumberDto {

    @NotBlank
    @Pattern(regexp = "01[0-1|6-9]-?\\d{4}-?\\d{4}")
    @Schema(description = "휴대폰 번호", example = "010-1111-2222")
    private String phoneNumber;

    public RequestPhoneNumberDto() {
    }

    public RequestPhoneNumberDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
