package com.team.cklob.gami.domain.member.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class NotFoundMemberDetail extends GlobalException {
    public NotFoundMemberDetail() {
        super(ErrorCode.NOT_FOUND_MEMBER_DETAIL);
    }
}
