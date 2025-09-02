package com.mediaplatform.user.config;

import com.mediaplatform.user.interceptor.AppInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC Configuration for User Service - Spring MVC customization and interceptor setup
 * 
 * This configuration class customizes Spring MVC behavior for the user service module,
 * primarily focusing on request/response interceptor management and request processing
 * enhancements for the user management functionality.
 * 
 * Key configurations:
 * - HTTP request/response interceptors for cross-cutting concerns
 * - User context extraction and management
 * - Request logging and monitoring setup
 * - Authentication context handling (if applicable)
 * 
 * The AppInterceptor configured here handles:
 * - User ID extraction from request headers (injected by gateway)
 * - Request context setup for downstream service calls
 * - User session management and context propagation
 * - Performance monitoring and request tracking
 * 
 * Pattern matching:
 * - "/**": Applies interceptor to all endpoints in the user service
 * - Ensures consistent request processing across all user operations
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    /**
     * Registers custom interceptors for request processing
     * 
     * Configures the AppInterceptor to handle all incoming requests to the user service.
     * This interceptor is responsible for:
     * - Extracting user context from gateway-injected headers
     * - Setting up request-scoped user information
     * - Enabling proper context propagation to service layers
     * - Facilitating user-specific operations and data access
     * 
     * @param registry InterceptorRegistry for registering custom interceptors
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AppInterceptor()).addPathPatterns("/**");
    }
}
