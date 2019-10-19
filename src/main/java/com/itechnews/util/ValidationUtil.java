package com.itechnews.util;


import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class ValidationUtil {
    public static Map<String, Object> getMessageModelFromBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> message = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError ->
                message.put(fieldError.getObjectName() + "." +fieldError.getField(), fieldError.getDefaultMessage())
            );
            return message;
        }
        return null;
    }
}
