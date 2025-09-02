package com.mediaplatform.article.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Streams Configuration for Article Service - Real-time data processing setup
 * 
 * This configuration class sets up Apache Kafka Streams for the article service,
 * enabling real-time stream processing capabilities for article-related events
 * and data transformations within the media platform ecosystem.
 * 
 * Key functionalities:
 * - Real-time article popularity scoring based on user interactions
 * - Live analytics and metrics computation for article performance
 * - Event-driven updates to article metadata and statistics
 * - Stream processing for recommendation engine data feeds
 * - Hot article detection and trending topic identification
 * 
 * Stream Processing Applications:
 * - Article view count aggregation and updates
 * - User behavior analytics for personalization
 * - Content engagement metrics calculation
 * - Real-time ranking and popularity scoring
 * - Event-driven cache invalidation and updates
 * 
 * Configuration features:
 * - Custom application and client ID generation for service identification
 * - Retry configuration for fault tolerance and reliability
 * - String serialization/deserialization for message processing
 * - Bootstrap server configuration from application properties
 * - Maximum message size limits for large content processing
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@Setter
@Getter
@Configuration
@EnableKafkaStreams
@ConfigurationProperties(prefix="kafka")
public class KafkaStreamConfig {
    
    /** Maximum message size for handling large article content and metadata */
    private static final int MAX_MESSAGE_SIZE = 16 * 1024 * 1024; // 16MB
    
    /** Kafka broker hosts configuration from application properties */
    private String hosts;
    
    /** Consumer group identifier for stream processing applications */
    private String group;
    
    /**
     * Creates and configures the default Kafka Streams configuration bean
     * 
     * This method sets up the core Kafka Streams configuration with optimized
     * settings for the article service's real-time processing requirements.
     * 
     * Configuration includes:
     * - Bootstrap servers for cluster connection
     * - Unique application and client IDs for service identification
     * - Retry policies for resilient message processing
     * - Default serialization strategies for key-value pairs
     * - Performance and reliability optimizations
     * 
     * @return KafkaStreamsConfiguration with optimized settings for article processing
     */
    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration defaultKafkaStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        
        // Core connection configuration
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, hosts);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, this.getGroup() + "_stream_aid");
        props.put(StreamsConfig.CLIENT_ID_CONFIG, this.getGroup() + "_stream_cid");
        
        // Reliability and performance settings
        props.put(StreamsConfig.RETRIES_CONFIG, 10); // Retry failed operations up to 10 times
        
        // Default serialization configuration for message keys and values
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        
        return new KafkaStreamsConfiguration(props);
    }
}