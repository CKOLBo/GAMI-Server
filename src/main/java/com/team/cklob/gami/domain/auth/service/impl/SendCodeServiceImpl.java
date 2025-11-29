package com.team.cklob.gami.domain.auth.service.impl;

import com.team.cklob.gami.domain.auth.exception.EmailAlreadyExistsException;
import com.team.cklob.gami.domain.auth.presentation.dto.request.SendVerificationCodeRequest;
import com.team.cklob.gami.domain.auth.service.SendCodeService;
import com.team.cklob.gami.domain.auth.exception.TooManyRequestsException;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import com.team.cklob.gami.global.redis.RedisUtil;
import com.team.cklob.gami.global.security.exception.InternalServerErrorException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendCodeServiceImpl implements SendCodeService {

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;

    private static final String EMAIL_AUTH_PREFIX = "auth:email:";
    private static final long EXPIRE_MINUTES = 5L;

    private static final String RATE_LIMIT_PREFIX = "email:ratelimit:";
    private static final long RATE_LIMIT_TIME = 30L;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void execute(SendVerificationCodeRequest request) throws MessagingException {

        if (memberRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        String code = createVerificationCode();
        MimeMessage message = createMail(request.email(), code);

        String key = EMAIL_AUTH_PREFIX +  request.email();
        String limitKey = RATE_LIMIT_PREFIX +  request.email();

        if (redisUtil.hasKey(key)) {
            throw new TooManyRequestsException();
        }

        try {
            javaMailSender.send(message);
            redisUtil.setCode(key, code, EXPIRE_MINUTES);
            redisUtil.pendingCode(limitKey, "1", RATE_LIMIT_TIME);
        } catch (MailException e) {
            log.error("Error sending verification code.", e);
            throw new InternalServerErrorException();
        }
    }

    private MimeMessage createMail(String mail, String verificationCode) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, mail);
        message.setSubject("GAMI 이메일 인증");
        message.setText("인증번호: " + verificationCode);

        return message;
    }

    private String createVerificationCode() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        return random.ints(6, 0, characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
