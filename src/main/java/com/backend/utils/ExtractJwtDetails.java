package com.backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Base64;

@Component
public class ExtractJwtDetails {

    @Autowired
    private ObjectMapper mapper;

    public JwtDecoder Decoder(String token) throws JsonProcessingException {
        String plainToken = token.substring(7, token.length());
        String threeParts[] = plainToken.split("\\.");
        System.out.println(Arrays.stream(threeParts).toList().toString());
        String payLoad = new String(Base64.getUrlDecoder().decode(threeParts[1]));
        JwtDecoder jwtDecoder = mapper.readValue(payLoad, JwtDecoder.class);
        return jwtDecoder;

    }
}


