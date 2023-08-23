package com.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class LoginDto {

    @NotEmpty(message = "Email is required")
    @Email(message = "Malformed email")
    private String email;

    @NotEmpty(message = "Password is required")

    private String password;

}
