package com.learning.payment.exception;

import java.util.Map;

public record ErrorResponse(
    Map<String, String> errors
) {

}
