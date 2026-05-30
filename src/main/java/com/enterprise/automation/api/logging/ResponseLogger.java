package com.enterprise.automation.api.logging;

import com.enterprise.automation.api.ApiResponseWrapper;
import com.enterprise.automation.logging.FrameworkLogger;
import org.apache.logging.log4j.Logger;

public class ResponseLogger {
    private static final Logger LOGGER = FrameworkLogger.getLogger(ResponseLogger.class);

    public void log(ApiResponseWrapper response) {
        LOGGER.info("apiResponse status={} headers={}", response.status(), response.headers().keySet());
    }
}
