package com.selloohub.leo.product.exception;

import com.selloohub.leo.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ComboInvalidException extends ApiException {
    public ComboInvalidException(String message) {
        super("COMBO_INVALID", HttpStatus.BAD_REQUEST, message);
    }
}
