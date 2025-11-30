package com.team.cklob.gami.domain.comment.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class ForbiddenCommentAccessException extends GlobalException {

    public ForbiddenCommentAccessException() {
        super(ErrorCode.FORBIDDEN_COMMENT_ACCESS);
    }
}
