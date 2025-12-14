package com.team.cklob.gami.domain.post.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class PostInvalidImageExtensionException extends GlobalException {

    public PostInvalidImageExtensionException() {
        super(ErrorCode.INVALID_POST_IMAGE_EXTENSION);
    }
}
