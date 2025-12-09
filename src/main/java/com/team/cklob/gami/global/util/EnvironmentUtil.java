package com.team.cklob.gami.global.util;

import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil {

    public String getEnvironment() {
        if (System.getenv("DOTNET_RUNNING_IN_CONTAINER") != null
                || System.getenv("KUBERNETES_SERVICE_HOST") != null
                || System.getenv("container") != null) {
            return "docker";
        }

        if ("true".equals(System.getenv("RUN_IN_DOCKER"))) {
            return "docker";
        }

        return "local";
    }
}
