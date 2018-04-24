package com.mbsystem.rest.error.handling.dto;

import lombok.Data;

@Data
public class ValidationError {

    private String code;
    private String message;
}
