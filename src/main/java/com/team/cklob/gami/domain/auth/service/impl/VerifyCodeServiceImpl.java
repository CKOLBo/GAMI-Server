package com.team.cklob.gami.domain.auth.service.impl;

import com.team.cklob.gami.domain.auth.exception.NotFoundVerifyCodeException;
import com.team.cklob.gami.domain.auth.exception.NotMatchedCodeException;
import com.team.cklob.gami.domain.auth.presentation.dto.request.VerifyCodeRequest;
import com.team.cklob.gami.domain.auth.service.VerifyCodeService;
import com.team.cklob.gami.global.redis.RedisUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyCodeServiceImpl implements VerifyCodeService {

    private final RedisUtil redisUtil;

    private static final String EMAIL_AUTH_PREFIX = "auth:email:";
    private static final String VERIFY_TIME_PREFIX = "auth:verify:time:";
    private static final String RATE_LIMIT_PREFIX = "email:ratelimit:";
    private static final long EXPIRE_MINUTES = 30L;


    @Override
    @Transactional
    public void execute(VerifyCodeRequest request) {
        String key = EMAIL_AUTH_PREFIX +  request.email();
        String limitKey = RATE_LIMIT_PREFIX +  request.email();

        if (!redisUtil.hasKey(key)) {
            throw new NotFoundVerifyCodeException();
        }

        if (!redisUtil.getValue(limitKey).equals(request.code())) {
            throw new NotMatchedCodeException();
        }

        redisUtil.deleteValue(key);

        String verifyKey = VERIFY_TIME_PREFIX + request.email();
        redisUtil.setVerifyStatus(verifyKey, request.code(), EXPIRE_MINUTES);
    }
}
