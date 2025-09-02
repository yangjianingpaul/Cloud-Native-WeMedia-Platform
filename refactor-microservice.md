refactor-microservice

This is a significantly larger and more complex project, which is excellent for
  showcasing your skills with a full microservice architecture. I have analyzed the file structure.

  The project is well-structured into Maven modules, separating concerns like services, gateways, and common
  utilities. This is a solid foundation.

  Here is a detailed plan to refactor this "We-Media" platform into a world-class portfolio project.

  The Refactoring Plan: micro-service project

  The core themes of this refactoring will be:
   1. Professional Presentation: English-only documentation and clear, consistent naming.
   2. Architectural Best Practices: Centralized configuration and robust API contracts.
   3. Production Readiness: Demonstrating resilience and testability.

  ---

  Phase 1: Documentation and Presentation (Highest Priority)

  An interviewer must be able to understand your project's architecture at a glance.

   1. Create a Master `README.md`:
       * Location: /home/Paul/leetcode/micro-service/heima-leadnews/README.md
       * Action: This file will become the central hub for the entire project. It must explain the entire system.
       * Required Content:
           * Project Title: "Cloud-Native We-Media Platform" or similar.
           * Overview: Describe the business purpose. Example: "A complete microservice platform for a modern news 
             and content-creation business. It handles everything from user authentication and article creation to 
             AI-powered content moderation and dynamic news feeds."
           * Architecture Diagram: This is the most critical part. Create a high-level diagram that shows all the
             major services (user, article, wemedia, search, schedule), the three API Gateways, Nacos, Kafka,
             MinIO, Elasticsearch, and the databases. This single diagram will prove you understand the
             architecture.
           * Module Breakdown: Create a table listing every module (e.g., heima-leadnews-user,
             heima-leadnews-article, heima-leadnews-common) with a one-sentence description of its responsibility.
           * Setup Instructions: Provide clear, step-by-step instructions on how to launch the entire system.

   2. Clean Up and Translate All Docs:
       * Location: /home/Paul/leetcode/micro-service/docs/
       * Action: This is a critical step for an international audience.
           * Translate all Chinese filenames (e.g., 功能实现.md) to English (feature-implementation.md).
           * Translate the content of all markdown files to professional English.
           * Move all these translated files and the images into a new
             /home/Paul/leetcode/micro-service/heima-leadnews/docs/ directory to keep all project artifacts
             together.

  ---

  Phase 2: Code and Configuration Refinements

   1. Unify the Package Name:
       * Action: This is a powerful signal of project ownership. In your IDE, perform a refactor-rename on the base
         package com.heima across all modules. Change it to a personal package name like com.paulyang.media.

   2. Centralize Configuration in Nacos:
       * Action: Your services use bootstrap.yml to connect to Nacos, which is correct. The next step is to ensure
         that only the Nacos connection info is in the local files. All other configurations (database URLs,
         Redis/Kafka addresses, etc.) should be managed in the Nacos UI.
       * Recommendation: Create a new markdown file in your new docs folder called nacos-config.md. In this file,
         document the configuration you have set up in Nacos for shared properties and for each individual service.

   3. Consolidate Gateway Logic:
       * Analysis: You have three separate gateway modules. Each has its own AuthorizeFilter.java.
       * Action: If the authorization logic in these filters is identical, refactor it. Move the shared JWT
         validation logic into a single utility class within your heima-leadnews-common module. The filters in each
         gateway can then call this common utility, reducing code duplication.

  ---

  Phase 3: API Contracts and Resilience

   1. Strengthen the `feign-api` Module:
       * Analysis: You have a heima-leadnews-feign-api module, which is excellent.
       * Action: Ensure that every single @FeignClient interface has a fallback class defined, like you have for
         IArticleClientFallback.java. This is critical for implementing the Circuit Breaker pattern and making your
         system resilient to failures in downstream services. If any clients are missing a fallback, add one.

  ---

  Phase 4: Professional Testing Strategy

  This is the biggest opportunity to impress. The project currently has very few tests.

   1. Write Unit Tests:
       * Action: In the heima-leadnews-wemedia service, find the WmNewsAutoScanServiceImpl.java. This class likely
         contains complex business logic for scanning articles. Write JUnit tests using Mockito to test this logic
         without needing a real database or other services.

   2. Write a Key Integration Test:
       * Action: In the heima-leadnews-article service, write a @SpringBootTest integration test for the
         HotArticleServiceImpl.java. This test should:
           1. Save several articles to the test database.
           2. Call the hot article computation logic.
           3. Assert that the correct articles are identified as "hot" and that their scores are calculated
              correctly.


  1. I want refactor the project of 'micro-service', please base on the file 'refactor-microservice.md' in 'micro-service' folder, step by step to refactor this project. Don't influence the project's function. 
  2. Change the folder 'micro-service' name to "Cloud-Native We-Media Platform" or similar. 
  3. help me check the project 'micro-service' commets. If there are no comments written for this function in some important files, please help me add them.
  4. Translate and exchange chinese letters into english correctly, include comments, return messages and so on. 
  5. I don't want the file or folder's name include 'heima' or 'leadnews'. please help me find those files or folders and exchange a proper name, preferably related to my project. 
  6. After refactor, ensurance this project can running correctly.