package com.team.cklob.gami.domain.post.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotFoundPostException extends GlobalException {

    public NotFoundPostException() {
        super(ErrorCode.NOT_FOUND_POST);
    }
}
