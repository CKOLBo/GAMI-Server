package com.team.cklob.gami.global.security.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class InvalidMemberPrincipalException extends GlobalException {
    public InvalidMemberPrincipalException() {
        super(ErrorCode.INVALID_MEMBER_PRINCIPAL);
    }
}
