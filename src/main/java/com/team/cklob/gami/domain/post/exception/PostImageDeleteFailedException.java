package com.team.cklob.gami.domain.post.exception;

import com.team.cklob.gami.global.exception.ErrorCode;
import com.team.cklob.gami.global.exception.GlobalException;

public class PostImageDeleteFailedException extends GlobalException {

    public PostImageDeleteFailedException() {
        super(ErrorCode.POST_IMAGE_DELETE_FAILED);
    }
}
