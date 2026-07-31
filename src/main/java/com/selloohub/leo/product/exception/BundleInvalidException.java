package com.selloohub.leo.product.exception;

import com.selloohub.leo.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class BundleInvalidException extends ApiException {
    public BundleInvalidException(String message) {
        super("BUNDLE_INVALID", HttpStatus.BAD_REQUEST, message);
    }
}
