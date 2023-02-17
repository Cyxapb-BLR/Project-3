package com.matskevich.springcourse.Project3.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorUtil {
    public static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError error : fieldErrors) {
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append(";");
        }
        throw new SensorOrMeasurementException(errorMsg.toString());
    }
}
