package com.team.cklob.gami.domain.post.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotFoundPostPageException extends GlobalException {

    public NotFoundPostPageException() {
        super(ErrorCode.NOT_FOUND_POST_PAGE);
    }
}
