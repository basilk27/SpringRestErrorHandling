package com.mbsystem.rest.error.handling.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorDetail {

    private String title;
    private int status;
    private String detail;
    private long timestamp;
    private String developerMessage;
    private Map<String, List<ValidationError>> errorMap = new HashMap<>();
}
