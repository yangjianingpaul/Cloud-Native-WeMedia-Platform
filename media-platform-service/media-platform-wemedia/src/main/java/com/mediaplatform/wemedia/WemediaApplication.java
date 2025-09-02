package com.mediaplatform.wemedia;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * WeMedia Service Application - Main entry point for the content creator management microservice
 * 
 * This application serves as the backend for content creators and media publishers, providing:
 * - News article creation and management
 * - Media material (images, videos) upload and organization  
 * - Content review and auto-scanning capabilities
 * - Channel and category management
 * - Sensitive content filtering and monitoring
 * - Scheduled publishing and automated content processing
 * 
 * Key capabilities:
 * - Automated content scanning using AI/ML algorithms
 * - Scheduled tasks for content processing and publishing
 * - Asynchronous operations for improved performance
 * - Integration with third-party content analysis services
 * - MyBatis Plus for enhanced database operations
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@SpringBootApplication
@EnableDiscoveryClient  // Enable service registration and discovery
@MapperScan("com.mediaplatform.wemedia.mapper")  // Scan MyBatis mapper interfaces
@EnableFeignClients(basePackages = "com.mediaplatform.apis")  // Enable Feign clients for inter-service calls
@EnableAsync  // Enable asynchronous method execution for performance optimization
@EnableScheduling  // Enable scheduled tasks for automated content processing
public class WemediaApplication {

    /**
     * Main method to start the WeMedia Service application
     * 
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(WemediaApplication.class, args);
    }

    /**
     * Configure MyBatis Plus interceptor with pagination support
     * 
     * This configuration enables automatic pagination handling for database queries,
     * essential for efficiently managing large datasets of news articles and media content.
     * 
     * @return configured MybatisPlusInterceptor with MySQL pagination support
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
