package com.team.cklob.gami.domain.mentoring.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class AlreadyRegisteredMentorException extends GlobalException {
    public AlreadyRegisteredMentorException() {
        super(ErrorCode.ALREADY_REGISTERED_MENTOR);
    }
}
