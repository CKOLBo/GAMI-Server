package com.team.cklob.gami.domain.mentoring.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotFoundRandomMentorException extends GlobalException {
    public NotFoundRandomMentorException() {
        super(ErrorCode.RANDOM_MENTOR_NOT_FOUND);
    }
}
