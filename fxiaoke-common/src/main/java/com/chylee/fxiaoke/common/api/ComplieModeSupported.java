package com.chylee.fxiaoke.common.api;

import org.springframework.beans.factory.annotation.Value;

public class ComplieModeSupported {
    @Value("${spring.profiles.active:}")
    protected String profiles;

    protected boolean isDevMode() {
        return "dev".equals(profiles);
    }
}
