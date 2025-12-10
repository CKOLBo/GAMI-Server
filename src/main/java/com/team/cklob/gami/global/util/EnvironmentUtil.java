package com.team.cklob.gami.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil {

    @Value("${app.env:}")
    private String configuredEnv;

    public String getEnvironment() {

        if (configuredEnv != null && !configuredEnv.isBlank()) {
            return configuredEnv.trim();
        }

        if (System.getenv("CT_SERVICE") != null
                || System.getenv("CT_PROJECT_ID") != null
                || System.getenv("CT_VERSION") != null) {
            return "cloudtype";
        }

        if (System.getenv("DOTNET_RUNNING_IN_CONTAINER") != null
                || System.getenv("KUBERNETES_SERVICE_HOST") != null
                || System.getenv("container") != null
                || "true".equals(System.getenv("RUN_IN_DOCKER"))) {
            return "docker";
        }

        return "local";
    }
}
