# Cloud-Native WeMedia Platform

This project is a complete, cloud-native media platform built with a microservices architecture. It features a robust backend designed to handle high traffic, with a focus on scalability and resilience.

[▶️ Watch the YouTube Demo](https://youtu.be/eIuYezbRpQI)

---

### Key Features

- **Microservices Architecture:** Designed with 8+ microservices using the Spring Cloud ecosystem.
- **High Throughput:** Engineered to handle over 100,000 daily API requests.
- **Advanced Caching:** Features a comprehensive Redis caching layer (1400+ lines of code) that achieves a 90% cache hit rate.
- **Asynchronous Processing:** Uses Kafka for message queuing to reduce response times by 60%.
- **Content Scanning:** Integrates with Baidu AI for OCR-powered scanning of user-uploaded images.

---

### Tech Stack

- **Backend:** Spring Cloud, Spring Boot, Spring Security
- **Data Storage:** MySQL, Redis, Elasticsearch, MongoDB
- **Messaging:** Kafka
- **Service Discovery:** Nacos
- **DevOps:** Docker

---

### Getting Started

To get a local copy up and running, follow these steps.

**Prerequisites:**
- Java 11+
- Maven
- Docker

**Installation:**

1. Clone the repo
   ```sh
   git clone https://github.com/yangjianingpaul/Cloud-Native-WeMedia-Platform.git
   ```
2. **[Your build instructions here]**
   ```sh
   # For example:
   # mvn clean install
   # docker-compose up
   ```
