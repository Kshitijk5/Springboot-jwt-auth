package com.backend.controller;

import com.backend.dto.LoginDto;
import com.backend.dto.RefreshDto;
import com.backend.dto.RegisterDto;
import com.backend.model.APIResponse;
import com.backend.model.JwtResponse;
import com.backend.model.Role;
import com.backend.model.User;
import com.backend.repository.RoleRepository;
import com.backend.repository.UserRepository;
import com.backend.service.AuthService;
import com.backend.utils.ExtractJwtDetails;
import com.backend.utils.JwtDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Jwt;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {


    private AuthService authService;
    private ExtractJwtDetails extractJwtDetails;

    @PostMapping(path = {"/login", "/sign-in"})
    public ResponseEntity<JwtResponse> loginRequestHandler(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.loginService(loginDto));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<APIResponse> registerRequestHandler(@Valid @RequestBody RegisterDto registerDto) throws Exception {
        return new ResponseEntity<APIResponse>(authService.registerService(registerDto), HttpStatus.CREATED);

    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshTokenRequestHandler(
                                                                  @Valid @RequestBody RefreshDto refreshDto)
            throws JsonProcessingException {


        return new ResponseEntity<>(authService.refreshService(refreshDto),HttpStatus.OK);

    }
}
