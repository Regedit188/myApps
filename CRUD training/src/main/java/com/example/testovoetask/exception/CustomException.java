package com.example.testovoetask.exception;


import com.example.testovoetask.enums.CommonErrorCode;
import lombok.Getter;

public class CustomException extends IllegalStateException {

    @Getter
    private final CommonErrorCode commonErrorCode;

    public CustomException(CommonErrorCode commonErrorCode) {
        super(commonErrorCode.getMessage());
        this.commonErrorCode = commonErrorCode;
    }


}
