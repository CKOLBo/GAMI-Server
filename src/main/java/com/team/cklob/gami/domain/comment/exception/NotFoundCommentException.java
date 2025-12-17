package com.team.cklob.gami.domain.comment.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotFoundCommentException extends GlobalException {

    public NotFoundCommentException() {
        super(ErrorCode.NOT_FOUND_COMMENT);
    }
}
