package hr.igvu.expressionevaluator.inf.log;

import hr.igvu.expressionevaluator.ctrl.exception.AppException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Log4j2
@Component
@Order(1)
public class CustomResponseLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Wrap the response in ContentCachingResponseWrapper
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        // Proceed with the filter chain
        filterChain.doFilter(request, responseWrapper);

        log.debug("RESPONSE for endpoint={} ENDED with status={}, body={}", request.getRequestURI(), responseWrapper.getStatus(), getResponseBody(responseWrapper));

        // Copy the response body back to the original response
        responseWrapper.copyBodyToResponse();
    }

    private String getResponseBody(ContentCachingResponseWrapper responseWrapper) {
        try {
            byte[] responseBodyBytes = responseWrapper.getContentAsByteArray();
            if (responseBodyBytes.length > 0) {
                return new String(responseBodyBytes, responseWrapper.getCharacterEncoding());
            }
            return "[empty]";
        } catch (IOException e) {
            logger.error("Error reading response body", e);
            throw AppException.builder()
                    .time(LocalDateTime.now())
                    .http(INTERNAL_SERVER_ERROR)
                    .errors(List.of("Error reading response body.", e.getMessage()))
                    .build();
        }
    }
}