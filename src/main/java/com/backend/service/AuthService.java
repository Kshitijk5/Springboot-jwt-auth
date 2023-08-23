package com.backend.service;

import com.backend.dto.LoginDto;
import com.backend.dto.RefreshDto;
import com.backend.dto.RegisterDto;
import com.backend.model.APIResponse;
import com.backend.model.JwtResponse;
import jakarta.validation.Valid;

public interface AuthService {

    public JwtResponse loginService(LoginDto loginDto);
    public APIResponse registerService(RegisterDto registerDto) throws Exception;

    public JwtResponse refreshService( RefreshDto refreshDto,String email);
}
