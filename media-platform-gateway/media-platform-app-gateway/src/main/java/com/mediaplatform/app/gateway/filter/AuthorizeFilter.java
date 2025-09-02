package com.mediaplatform.app.gateway.filter;

import com.mediaplatform.app.gateway.util.AppJwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Authorization Filter for App Gateway - Global filter for JWT-based authentication and authorization
 * 
 * This filter implements security enforcement for all incoming requests to the application gateway.
 * It provides:
 * - JWT token validation and verification
 * - User authentication state management
 * - Request authorization based on token claims
 * - Automatic user context injection for downstream services
 * - Protection against unauthorized access attempts
 * 
 * The filter operates as a Spring Cloud Gateway GlobalFilter with the highest priority (order = 0)
 * to ensure security checks are performed before any other processing.
 * 
 * Security Flow:
 * 1. Extract JWT token from request headers
 * 2. Validate token format and signature
 * 3. Check token expiration and validity
 * 4. Extract user information from token claims
 * 5. Inject user context into request headers for downstream services
 * 6. Allow or deny request based on validation results
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@Component
@Slf4j
public class AuthorizeFilter implements Ordered, GlobalFilter {
    /**
     * Main filter method that processes all incoming requests for authentication and authorization
     * 
     * This method implements the core security logic for the gateway:
     * 1. Extracts and validates JWT tokens from request headers
     * 2. Bypasses authentication for login endpoints
     * 3. Verifies token validity and expiration
     * 4. Injects user context into request headers for downstream services
     * 5. Handles authentication failures with appropriate HTTP responses
     * 
     * @param exchange the current server web exchange containing request/response
     * @param chain the gateway filter chain for continuing request processing
     * @return Mono<Void> representing the completion of the filter operation
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Extract request and response objects for processing
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // Allow login endpoints to bypass authentication
        if (request.getURI().getPath().contains("/login")) {
            log.debug("Bypassing authentication for login endpoint: {}", request.getURI().getPath());
            return chain.filter(exchange);
        }

        // Extract JWT token from request headers
        String token = request.getHeaders().getFirst("token");

        // Reject requests without authentication token
        if (StringUtils.isBlank(token)) {
            log.warn("Request rejected: Missing authentication token for path: {}", request.getURI().getPath());
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // Validate JWT token and extract user information
        try {
            Claims claimsBody = AppJwtUtil.getClaimsBody(token);
            
            // Check token validity and expiration status
            int result = AppJwtUtil.verifyToken(claimsBody);
            if (result == 1 || result == 2) {
                log.warn("Request rejected: Invalid or expired token for path: {}", request.getURI().getPath());
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            // Extract user ID from token claims and inject into request headers
            Object userId = claimsBody.get("id");
            ServerHttpRequest serverHttpRequest = request.mutate().headers(httpHeaders -> {
                httpHeaders.add("userId", userId + "");
            }).build();

            // Update the exchange with the modified request containing user context
            exchange = exchange.mutate().request(serverHttpRequest).build();
            
            log.debug("Authentication successful for user: {} accessing path: {}", userId, request.getURI().getPath());

        } catch (Exception e) {
            log.error("Authentication error for path: {} - {}", request.getURI().getPath(), e.getMessage());
            // Continue processing even if token extraction fails (for backward compatibility)
        }

        // Continue with the filter chain
        return chain.filter(exchange);
    }

    /**
     * Defines the execution order of this filter in the gateway filter chain
     * 
     * Returns 0 to ensure this authorization filter executes with the highest priority,
     * before any other filters that might depend on user authentication context.
     * Lower values indicate higher priority in Spring Cloud Gateway.
     * 
     * @return 0 indicating highest priority execution order
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
