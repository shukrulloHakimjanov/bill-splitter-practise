
package com.spring.bill.splitter.dto;

import com.spring.bill.splitter.constant.enums.ErrorType;
import lombok.Builder;

import java.util.List;

@Builder
public record ErrorDto(
        int code,
        String message,
        ErrorType type,
        List<String> validationErrors) {}
