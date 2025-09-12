package org.menuservice.api.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var req = new ContentCachingRequestWrapper( (HttpServletRequest) servletRequest);
        var res = new ContentCachingResponseWrapper( (HttpServletResponse) servletResponse);

        filterChain.doFilter(req, res);

        var requestHeaderValues = new StringBuilder();
        var responseHeaderValues = new StringBuilder();
        req.getHeaderNames().asIterator().forEachRemaining(headerkey -> {
            var headerValue = req.getHeader(headerkey);
            requestHeaderValues.append(headerkey).append(" : ").append(headerValue).append(" , ");
        });
        res.getHeaderNames().forEach(headerkey -> {
            var headerValue = res.getHeader(headerkey);
            responseHeaderValues.append(headerkey).append(" : ").append(headerValue).append(" , ");
        });


        var requestBody = new String(req.getContentAsByteArray());
        var responseBody = new String(res.getContentAsByteArray());


        log.info(">>>>> request-header : {}, request-body : {}", requestHeaderValues, requestBody);
        log.info(">>>>> response-header : {}, response-body : {}", responseHeaderValues, responseBody);


        res.copyBodyToResponse();
    }
}
