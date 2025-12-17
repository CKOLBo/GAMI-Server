package com.team.cklob.gami.domain.post.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class ForbiddenPostAccessException extends GlobalException {

    public ForbiddenPostAccessException() {
        super(ErrorCode.FORBIDDEN_POST_ACCESS);
    }
}
