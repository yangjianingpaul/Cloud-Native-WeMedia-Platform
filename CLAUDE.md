# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot microservices project for a Cloud Native WeMedia Platform built with Java 11 and Spring Boot 2.3.9. The project follows a modular architecture with separate services for different business domains.

## Build System

- **Build Tool**: Maven (multi-module project)
- **Java Version**: 11
- **Spring Boot Version**: 2.3.9.RELEASE
- **Spring Cloud Version**: Hoxton.SR10
- **Spring Cloud Alibaba**: 2.2.5.RELEASE

### Build Commands

```bash
# Build entire project
mvn clean compile

# Package all modules
mvn clean package

# Install to local repository
mvn clean install

# Skip tests during build
mvn clean package -DskipTests

# Build specific module (run from module directory)
cd media-platform-service/media-platform-article
mvn clean package
```

### Test Commands

```bash
# Run all tests
mvn test

# Run tests for specific module
mvn test -pl media-platform-service/media-platform-article

# Run specific test class
mvn test -Dtest=ArticleFreemarkerTest
```

## Architecture Overview

### Module Structure

**Core Modules:**
- `media-platform-model`: Data models and DTOs
- `media-platform-common`: Shared utilities, constants, and configurations  
- `media-platform-utils`: Common utility classes
- `media-platform-feign-api`: Feign client interfaces for inter-service communication
- `media-platform-basic`: Basic starter configurations

**Service Modules (`media-platform-service/`):**
- `media-platform-user`: User management service (port 51801)
- `media-platform-article`: Article management and content service (port 51802) 
- `media-platform-wemedia`: Media/content creation service (port 51803)
- `media-platform-admin`: Admin management service (port 51804)
- `media-platform-search`: Search service with Elasticsearch (port 51805)
- `media-platform-behavior`: User behavior tracking service (port 51806)
- `media-platform-schedule`: Scheduled task service (port 51807)
- `media-platform-comment`: Comment service (port 51808)

**Gateway Modules (`media-platform-gateway/`):**
- `media-platform-app-gateway`: Mobile app gateway (port 51601)
- `media-platform-admin-gateway`: Admin panel gateway (port 51602)  
- `media-platform-wemedia-gateway`: Media management gateway (port 51603)

**Test Modules (`media-platform-test/`):**
- Various demo applications for testing integrations (Kafka, MongoDB, Elasticsearch, etc.)

### Key Technologies

- **Service Discovery**: Nacos
- **Configuration Management**: Nacos Config
- **Database**: MySQL with MyBatis Plus
- **Message Queue**: Kafka with Kafka Streams
- **Search**: Elasticsearch
- **Cache**: Redis
- **File Storage**: FastDFS/MinIO
- **Template Engine**: Freemarker
- **Content Security**: Baidu AI for text/image scanning
- **OCR**: Tesseract (Tess4j)
- **Job Scheduling**: XXL-Job
- **Documentation**: Swagger/Knife4j
- **Monitoring**: Spring Boot Admin

### Service Communication

- Services use Feign clients defined in `media-platform-feign-api` for inter-service communication
- All services register with Nacos for service discovery
- Configuration is externalized to Nacos Config Center

### Running Services

Each service can be run independently using their main Application classes:

```bash
# Example: Run article service
cd media-platform-service/media-platform-article
mvn spring-boot:run

# Or run the compiled JAR
java -jar target/media-platform-article-1.0-SNAPSHOT.jar
```

### Configuration

- Services use `bootstrap.yml` for Nacos configuration
- Profiles are used for environment-specific configurations (dev, prod)
- Configuration is centralized in Nacos Config Center
- Database and external service configurations are externalized

### Important Constants

Key business constants are defined in `media-platform-common/src/main/java/com/mediaplatform/common/constants/`:
- `ArticleConstants`: Article-related constants
- `UserConstants`: User management constants  
- `BehaviorConstants`: User behavior tracking constants
- `ScheduleConstants`: Task scheduling constants
- `WemediaConstants`: Media management constants

### Development Notes

- All services follow the same package structure: `com.mediaplatform.{service-name}`
- Mappers are typically in `{service}.mapper` package with corresponding XML files in `src/main/resources/mapper/`
- Services use `@MapperScan` to scan mapper interfaces
- MyBatis Plus is configured with MySQL pagination interceptor
- Feign clients are enabled with base package scanning
- Async processing is enabled where needed
- Token-based authentication is implemented with interceptors