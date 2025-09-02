package com.mediaplatform.admin.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Admin Gateway Application - Main entry point for the administrative portal gateway
 * 
 * This gateway serves as the dedicated entry point for administrative operations
 * and management interfaces of the media platform. It provides:
 * - Centralized routing to administrative backend services
 * - Enhanced security and authentication for admin operations
 * - Request filtering and authorization validation
 * - Administrative API access control
 * - Monitoring and audit trail capabilities
 * 
 * The admin gateway is separate from the main app gateway to provide:
 * - Isolated security policies for administrative functions
 * - Different rate limiting and throttling rules
 * - Dedicated monitoring and logging for admin operations
 * - Fine-grained access control for sensitive operations
 * 
 * Key responsibilities:
 * - Route admin requests to admin service, content management, etc.
 * - Enforce strict authentication and authorization policies
 * - Provide comprehensive audit logging
 * - Implement role-based access control (RBAC)
 * - Handle administrative service discovery and load balancing
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@SpringBootApplication
@EnableDiscoveryClient  // Enable service discovery for admin service routing
public class AdminGatewayApplication {
    
    /**
     * Main method to start the Admin Gateway application
     * 
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(AdminGatewayApplication.class, args);
    }
}
