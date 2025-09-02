package com.mediaplatform.admin.gateway.filter;

import com.mediaplatform.admin.gateway.util.AppJwtUtil;
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
 * Admin Authorization Filter - Enhanced security filter for administrative gateway
 * 
 * This filter provides strict authentication and authorization enforcement for administrative
 * operations in the media platform. It implements enhanced security measures compared to
 * the regular app gateway filter due to the sensitive nature of admin operations.
 * 
 * Key security features:
 * - Mandatory JWT token validation for all non-login requests
 * - Strict token expiration and signature verification
 * - Enhanced error handling and security logging
 * - Immediate rejection of invalid or missing tokens
 * - Protection of administrative endpoints and operations
 * 
 * The filter ensures that only authenticated administrators can access:
 * - Content management operations
 * - User administration functions
 * - System configuration changes
 * - Platform analytics and reporting
 * - Sensitive data operations
 * 
 * Security Flow:
 * 1. Bypass authentication only for login endpoints
 * 2. Require valid JWT token for all other requests
 * 3. Perform comprehensive token validation
 * 4. Immediately reject any authentication failures
 * 5. Log security events for audit purposes
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@Component
@Slf4j
public class AuthorizeFilter implements Ordered, GlobalFilter {
    
    /**
     * Main filter method for processing admin authentication requests
     * 
     * This method implements enhanced security validation for administrative access:
     * - Allows only login endpoints to bypass authentication
     * - Requires valid JWT tokens for all administrative operations
     * - Performs strict token validation with immediate rejection on failure
     * - Provides comprehensive security logging for audit trails
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
        if(request.getURI().getPath().contains("/login")){
            log.debug("Bypassing authentication for admin login endpoint: {}", request.getURI().getPath());
            return chain.filter(exchange);
        }

        // Extract JWT token from request headers
        String token = request.getHeaders().getFirst("token");

        // Reject requests without authentication token (strict policy for admin)
        if(StringUtils.isBlank(token)){
            log.warn("Admin access denied: Missing authentication token for path: {}", request.getURI().getPath());
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // Validate JWT token with enhanced error handling for admin security
        try {
            Claims claimsBody = AppJwtUtil.getClaimsBody(token);
            
            // Perform strict token validation (expiration and signature)
            int result = AppJwtUtil.verifyToken(claimsBody);
            if(result == 1 || result == 2){
                log.warn("Admin access denied: Invalid or expired token for path: {}", request.getURI().getPath());
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            
            log.debug("Admin authentication successful for path: {}", request.getURI().getPath());
            
        } catch (Exception e){
            log.error("Admin authentication error for path: {} - {}", request.getURI().getPath(), e.getMessage());
            // Strict security: reject any token processing errors for admin access
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // Continue with filter chain after successful authentication
        return chain.filter(exchange);
    }

    /**
     * Defines the execution order of this filter in the admin gateway filter chain
     * 
     * Returns 0 to ensure this authorization filter executes with the highest priority,
     * providing security enforcement before any other administrative operations.
     * 
     * @return 0 indicating highest priority execution order
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
