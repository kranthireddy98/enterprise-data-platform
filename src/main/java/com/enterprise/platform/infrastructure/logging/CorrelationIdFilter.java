package com.enterprise.platform.infrastructure.logging;

import com.enterprise.platform.config.AppProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private final AppProperties appProperties;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        String requestId = request.getHeader(REQUEST_ID_HEADER);

        if (requestId == null || requestId.isBlank()){
            requestId = UUID.randomUUID().toString();
        }
        MDC.put("requestId",requestId);
        MDC.put("environment",appProperties.getEnvironment());

        try {
            response.setHeader(REQUEST_ID_HEADER,requestId);
            filterChain.doFilter(request,response);
        }finally {
            MDC.clear();
        }
    }
}
