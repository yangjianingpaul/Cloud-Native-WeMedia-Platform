package com.mediaplatform.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * User Service Application - Main entry point for the user management microservice
 * 
 * This application provides comprehensive user management functionality including:
 * - User registration and authentication
 * - User profile management
 * - User relationship management (following/followers)
 * - Real name verification services
 * 
 * The service integrates with the cloud-native media platform ecosystem through:
 * - Service discovery via Eureka/Nacos
 * - OpenFeign for inter-service communication
 * - MyBatis for database operations
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@SpringBootApplication
@EnableDiscoveryClient  // Enable service registration and discovery
@MapperScan("com.mediaplatform.user.mapper")  // Scan MyBatis mapper interfaces
@EnableFeignClients(basePackages = "com.mediaplatform.apis")  // Enable Feign client for API calls
public class UserApplication {

    /**
     * Main method to start the User Service application
     * 
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
