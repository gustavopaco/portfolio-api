package com.pacoprojects.portfolio.exception;

import lombok.Builder;

@Builder
public record ExceptionObject(String message, int code) {
}
