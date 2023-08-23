package com.backend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class APIResponse {

    private String message;
}
