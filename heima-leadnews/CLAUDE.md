# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot microservices project for a news platform (Heima Lead News) built with Java 11 and Spring Boot 2.3.9. The project follows a modular architecture with separate services for different business domains.

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
cd heima-leadnews-service/heima-leadnews-article
mvn clean package
```

### Test Commands

```bash
# Run all tests
mvn test

# Run tests for specific module
mvn test -pl heima-leadnews-service/heima-leadnews-article

# Run specific test class
mvn test -Dtest=ArticleFreemarkerTest
```

## Architecture Overview

### Module Structure

**Core Modules:**
- `heima-leadnews-model`: Data models and DTOs
- `heima-leadnews-common`: Shared utilities, constants, and configurations  
- `heima-leadnews-utils`: Common utility classes
- `heima-leadnews-feign-api`: Feign client interfaces for inter-service communication
- `heima-leadnews-basic`: Basic starter configurations

**Service Modules (`heima-leadnews-service/`):**
- `heima-leadnews-user`: User management service (port 51801)
- `heima-leadnews-article`: Article management and content service (port 51802) 
- `heima-leadnews-wemedia`: Media/content creation service (port 51803)
- `heima-leadnews-admin`: Admin management service (port 51804)
- `heima-leadnews-search`: Search service with Elasticsearch (port 51805)
- `heima-leadnews-behavior`: User behavior tracking service (port 51806)
- `heima-leadnews-schedule`: Scheduled task service (port 51807)
- `heima-leadnews-comment`: Comment service (port 51808)

**Gateway Modules (`heima-leadnews-gateway/`):**
- `heima-leadnews-app-gateway`: Mobile app gateway (port 51601)
- `heima-leadnews-admin-gateway`: Admin panel gateway (port 51602)  
- `heima-leadnews-wemedia-gateway`: Media management gateway (port 51603)

**Test Modules (`heima-leadnews-test/`):**
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

- Services use Feign clients defined in `heima-leadnews-feign-api` for inter-service communication
- All services register with Nacos for service discovery
- Configuration is externalized to Nacos Config Center

### Running Services

Each service can be run independently using their main Application classes:

```bash
# Example: Run article service
cd heima-leadnews-service/heima-leadnews-article
mvn spring-boot:run

# Or run the compiled JAR
java -jar target/heima-leadnews-article-1.0-SNAPSHOT.jar
```

### Configuration

- Services use `bootstrap.yml` for Nacos configuration
- Profiles are used for environment-specific configurations (dev, prod)
- Configuration is centralized in Nacos Config Center
- Database and external service configurations are externalized

### Important Constants

Key business constants are defined in `heima-leadnews-common/src/main/java/com/heima/common/constants/`:
- `ArticleConstants`: Article-related constants
- `UserConstants`: User management constants  
- `BehaviorConstants`: User behavior tracking constants
- `ScheduleConstants`: Task scheduling constants
- `WemediaConstants`: Media management constants

### Development Notes

- All services follow the same package structure: `com.heima.{service-name}`
- Mappers are typically in `{service}.mapper` package with corresponding XML files in `src/main/resources/mapper/`
- Services use `@MapperScan` to scan mapper interfaces
- MyBatis Plus is configured with MySQL pagination interceptor
- Feign clients are enabled with base package scanning
- Async processing is enabled where needed
- Token-based authentication is implemented with interceptors