# Nacos Configuration Center

![](/resources/nacos配置中心.png)

## 1.leadnews-user

```yaml
spring:
  redis:
    host: 192.168.31.58
    password: leadnews
    port: 6379
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.31.58:3306/leadnews_user?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
    username: 'your name'
    password: 'your password'
# 设置Mapper接口所对应的XML文件位置，如果你在Mapper接口中有自定义方法，需要进行该配置
mybatis-plus:
  mapper-locations: classpath*:mapper/*.xml
  # 设置别名包扫描路径，通过该属性可以给包中的类注册别名
  type-aliases-package: com.heima.model.user.pojos
```

## 2.leadnews-app-gateway

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        add-to-simple-url-handler-mapping: true
        corsConfigurations:
          '[/**]':
            allowedHeaders: "*"
            allowedOrigins: "*"
            allowedMethods:
              - GET
              - POST
              - DELETE
              - PUT
              - OPTION
      routes:
      # platform management
        - id: user
          uri: lb://leadnews-user
          predicates:
            - Path=/user/**
          filters:
            - StripPrefix= 1
      # Article microservices
        - id: article
          uri: lb://leadnews-article
          predicates:
            - Path=/article/**
          filters:
            - StripPrefix= 1
      # Search microservices
        - id: search
          uri: lb://leadnews-search
          predicates:
            - Path=/search/**
          filters:
            - StripPrefix= 1
      # Behavioral microservice
        - id: behavior
          uri: lb://leadnews-behavior
          predicates:
            - Path=/behavior/**
          filters:
            - StripPrefix= 1
```

## 3.leadnews-article

```yaml
spring:
  redis:
    host: 192.168.31.58
    password: leadnews
    port: 6379
  kafka:
    bootstrap-servers: 192.168.31.125:9092
    # send message to search service
    producer:
      retries: 10
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    # listen message from we-media service 
    consumer:
      group-id: ${spring.application.name}
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.31.58:3306/leadnews_article?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC
    username: 'your name'
    password: 'your password'
# 设置Mapper接口所对应的XML文件位置，如果你在Mapper接口中有自定义方法，需要进行该配置
mybatis-plus:
  mapper-locations: classpath*:mapper/*.xml
  # 设置别名包扫描路径，通过该属性可以给包中的类注册别名
  type-aliases-package: com.heima.model.article.pojos
  global-config:
    datacenter-id: 1
    workerId: 1
minio:
  accessKey: 'your access key'
  secretKey: 'your secret key'
  bucket: leadnews
  endpoint: http://192.168.31.125:9000
  readPath: http://192.168.31.125:9000
xxl:
  job:
    admin:
      addresses: http://192.168.31.125:8888/xxl-job-admin
    executor:
      appname: leadnews-hot-article-executor
      port: 9999
kafka:
  hosts: 192.168.31.125:9092
  group: ${spring.application.name}
```

## 4.leadnews-wemedia

```yaml
spring:
  kafka:
    bootstrap-servers: 192.168.31.125:9092
    # send up or down message to article service
    producer:
      retries: 10
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.31.58:3306/leadnews_wemedia?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
    username: 'your name'
    password: 'your password'
# 设置Mapper接口所对应的XML文件位置，如果你在Mapper接口中有自定义方法，需要进行该配置
mybatis-plus:
  mapper-locations: classpath*:mapper/*.xml
  # 设置别名包扫描路径，通过该属性可以给包中的类注册别名
  type-aliases-package: com.heima.model.media.pojos
minio:
  accessKey: 'your access key'
  secretKey: 'your secret key'
  bucket: leadnews
  endpoint: http://192.168.31.125:9000
  readPath: http://192.168.31.125:9000
baidu:
  APP_ID: your ID
  API_KEY: your key
  SECRET_KEY: your secret key
feign:
  # Enable feign support for hystrix meltdown degradation
  hystrix:
    enabled: true
  # Change the call timeout period
  client:
    config:
      default:
        connectTimeout: 2000
        readTimeout: 2000
tess4j:
  data-path: /Users/paulyang/Desktop/tessdata
  language: chi_sim
```

## 5.leadnews-wemedia-gateway

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        cors-configurations:
          '[/**]': # Match all requests
            allowedOrigins: "*" # Cross-domain processing allows all domains
            allowedMethods: # Supported method
              - GET
              - POST
              - PUT
              - DELETE
      routes:
        # platform management
        - id: wemedia
          uri: lb://leadnews-wemedia
          predicates:
            - Path=/wemedia/**
          filters:
            - StripPrefix= 1
```

## 6.leadnews-schedule

```yaml
spring:
  redis:
    host: 192.168.31.58
    password: leadnews
    port: 6379
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.31.58:3306/leadnews_schedule?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC
    username: 'your name'
    password: 'your password'
# 设置Mapper接口所对应的XML文件位置，如果你在Mapper接口中有自定义方法，需要进行该配置
mybatis-plus:
  mapper-locations: classpath*:mapper/*.xml
  # 设置别名包扫描路径，通过该属性可以给包中的类注册别名
  type-aliases-package: com.heima.model.schedule.pojos
```

## 7.leadnews-search

```yaml
spring:
  data:
    mongodb:
      host: 192.168.31.58
      port: 27017
      database: leadnews-history
  kafka:
    bootstrap-servers: 192.168.31.125:9092
    # listen message from article service
    consumer:
      group-id: ${spring.application.name}
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
  autoconfigure:
    exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
elasticsearch:
  host: 192.168.31.125
  port: 9200
```

## 8.leadnews-admin-gateway

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        cors-configurations:
          '[/**]': # Match all requests
            allowedOrigins: "*" # Cross-domain processing allows all domains
            allowedMethods: # Supported method
              - GET
              - POST
              - PUT
              - DELETE
      routes:
        # platform management
        - id: admin
          uri: lb://leadnews-admin
          predicates:
            - Path=/admin/**
          filters:
            - StripPrefix= 1
        - id: wemedia
          uri: lb://leadnews-wemedia
          predicates:
            - Path=/wemedia/**
          filters:
            - StripPrefix= 1
        - id: user
          uri: lb://leadnews-user
          predicates:
            - Path=/user/**
          filters:
            - StripPrefix= 1
```

## 9.leadnews-admin

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.31.58:3306/leadnews_admin?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
    username: 'your name'
    password: 'your password'
# 设置Mapper接口所对应的XML文件位置，如果你在Mapper接口中有自定义方法，需要进行该配置
mybatis-plus:
  mapper-locations: classpath*:mapper/*.xml
  # 设置别名包扫描路径，通过该属性可以给包中的类注册别名
  type-aliases-package: com.heima.model.admin.pojos
```

## 10.leadnews-behavior

```yaml
spring:
  redis:
    host: 192.168.31.58
    password: leadnews
    port: 6379
  kafka:
    bootstrap-servers: 192.168.31.125:9092
    producer:
      retries: 10
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```