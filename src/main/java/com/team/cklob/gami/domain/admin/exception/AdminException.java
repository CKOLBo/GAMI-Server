package com.team.cklob.gami.domain.admin.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AdminException extends RuntimeException {

    private final ErrorCode errorCode;

    public AdminException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}