package com.mediaplatform.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * App Gateway Application - Main entry point for the mobile/web application gateway
 * 
 * This gateway serves as the single entry point for all client applications (mobile apps, web clients)
 * accessing the media platform services. It provides:
 * - Centralized routing to backend microservices
 * - Request/response filtering and transformation
 * - Authentication and authorization enforcement
 * - Rate limiting and security policies
 * - Load balancing and circuit breaker patterns
 * 
 * The gateway acts as a reverse proxy, routing incoming requests to appropriate backend services
 * while applying cross-cutting concerns such as security, monitoring, and resilience patterns.
 * 
 * Key responsibilities:
 * - Route user requests to user service, article service, etc.
 * - Implement authentication filters for protected endpoints
 * - Apply rate limiting to prevent abuse
 * - Provide centralized logging and monitoring
 * - Handle service discovery and load balancing
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@SpringBootApplication
@EnableDiscoveryClient  // Enable service discovery for routing to backend services
public class AppGatewayApplication {

    /**
     * Main method to start the App Gateway application
     * 
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(AppGatewayApplication.class, args);
    }
}
