package com.enterprise.automation.api.logging;

import com.enterprise.automation.api.ApiRequest;
import com.enterprise.automation.logging.FrameworkLogger;
import org.apache.logging.log4j.Logger;

public class RequestLogger {
    private static final Logger LOGGER = FrameworkLogger.getLogger(RequestLogger.class);

    public void log(ApiRequest request) {
        LOGGER.info("apiRequest method={} path={} headers={}",
                request.getMethod(), request.getPath(), request.getHeaders().keySet());
    }
}
