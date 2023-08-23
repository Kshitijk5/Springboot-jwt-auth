package com.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RegisterDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
    @Email
    @NotEmpty
    private String email;
}
