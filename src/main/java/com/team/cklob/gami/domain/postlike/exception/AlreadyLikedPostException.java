package com.team.cklob.gami.domain.postlike.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class AlreadyLikedPostException extends GlobalException {
    public AlreadyLikedPostException() {
        super(ErrorCode.ALREADY_LIKED_POST);
    }
}
