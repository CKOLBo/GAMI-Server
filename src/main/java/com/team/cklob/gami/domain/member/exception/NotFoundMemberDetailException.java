package com.team.cklob.gami.domain.member.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotFoundMemberDetailException extends GlobalException {
    public NotFoundMemberDetailException() {
        super(ErrorCode.NOT_FOUND_MEMBER_DETAIL);
    }
}
