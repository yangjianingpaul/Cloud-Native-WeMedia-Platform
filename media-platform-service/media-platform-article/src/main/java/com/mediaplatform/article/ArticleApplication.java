package com.mediaplatform.article;

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

/**
 * Article Service Application - Main entry point for the article management microservice
 * 
 * This application handles all article-related operations including:
 * - Article creation, publishing, and management
 * - Article collection and bookmarking
 * - Hot article ranking and recommendation
 * - Article content generation using Freemarker templates
 * - Static content generation for high-performance access
 * 
 * Key features:
 * - Asynchronous processing for performance optimization
 * - MyBatis Plus for enhanced database operations with pagination
 * - Integration with other microservices via OpenFeign
 * - Service discovery for cloud-native deployment
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@SpringBootApplication
@EnableDiscoveryClient  // Enable service registration and discovery
@MapperScan("com.mediaplatform.article.mapper")  // Scan MyBatis mapper interfaces
@EnableAsync  // Enable asynchronous method execution
@EnableFeignClients(basePackages = "com.mediaplatform.apis")  // Enable Feign clients for inter-service calls
public class ArticleApplication {

    /**
     * Main method to start the Article Service application
     * 
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }

    /**
     * Configure MyBatis Plus interceptor with pagination support
     * 
     * This bean configures MyBatis Plus to handle pagination queries automatically,
     * improving performance and reducing boilerplate code for paginated results.
     * 
     * @return configured MybatisPlusInterceptor with pagination support for MySQL
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
