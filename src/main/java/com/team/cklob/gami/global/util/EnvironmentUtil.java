package com.team.cklob.gami.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil {

    @Value("${app.env:local}")
    private String environment;

    public String getEnvironment() {
        return environment;
    }
}
