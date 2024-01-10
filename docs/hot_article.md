# Hot article

## Criteria for judging hot articles
- Number of likes, number of comments, number of reads, number of favorites
- Put hot data into redis for display

## Timing computing hot articles
![](/resources/timing_calculate.png)

- Calculate the score of an article based on its behavior (likes, comments, reads, favorites) and complete the calculation once a day using a scheduled task

- The article data with high score value is stored in redis

- When the App user queries the article list, the article data with high popularity is preferentially queried from redis

## xxl-job Distributed task scheduling framework

- The current software architecture has begun to shift to distributed architecture, which divides the single structure into several services, and completes business processing through network interaction between services. Under the distributed architecture, a service often deploes multiple instances to run our business, and if task scheduling is run in this distributed system environment, we call it distributed task scheduling **.

- XXL-JOB is a distributed task scheduling platform, its core design goals are rapid development, easy to learn, lightweight, easy to expand. Now open source and access to several companies online product lines, out of the box.

- Source address：https://gitee.com/xuxueli0323/xxl-job

- Document address：https://www.xuxueli.com/xxl-job/

- Initializing the Scheduling Database
    - xxl job lock: indicates the task scheduling lock table.
    - xxl_job_group: indicates the actuator information table, which contains the actuator information of maintenance tasks.
    - xxl_job_info: Extended scheduling information table: used to save the extended information of XXL-JOB scheduling tasks, such as task group, task name, machine address, actuator, execution entry, alarm email, and so on.
    - xxl_job_log: Scheduling log table: stores historical scheduling information about XXL-JOB tasks, such as scheduling results, execution results, scheduling entries, scheduling machines, and actuators.
    - xxl_job_logglue: Task GLUE logs: used to save GLUE update history and support GLUE version tracing.
    - xxl_job_registry: The actuator registry maintains the online actuator and dispatch center machine address information.
    - xxl job user: indicates the system user list.

## Configure the deployment scheduling center -docker installation
- Create a mysql container and initialize the SQL script for xxl-job

```shell
docker run -p 3306:3306 --name mysql57 \
-v /opt/mysql/conf:/etc/mysql \
-v /opt/mysql/logs:/var/log/mysql \
-v /opt/mysql/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql:5.7
```

- Pull image

```shell
docker pull xuxueli/xxl-job-admin:2.3.0
```
- Create a container

```shell
docker run -e PARAMS="--spring.datasource.url=jdbc:mysql://192.168.31.125:3306/xxl_job?Unicode=true&characterEncoding=UTF-8 \
--spring.datasource.username=root \
--spring.datasource.password=root" \
-p 8888:8080 -v /tmp:/data/applogs \
--name xxl-job-admin --restart=always  -d xuxueli/xxl-job-admin:2.3.0
```

## Hot article timing calculation ideas
![](/resources/score_calculation.png)

## Hot articles real-time calculation
![](/resources/realtimeCalculation.png)

## kafka stream implements streaming computing

* Stream calculation test class：”KafkaStreamQuickStart“

## Real-time computing flow
![](/resources/realtimeCalculation.png)

- User behavior (read volume, comments, likes, favorites) sends messages
    - Integrate kafka producer configuration in the HeMI-Leadnews-Behavior microservice
    - Modify ApLikesBehaviorServiceImpl new send messages
    - Modify the reading behavior of the class ApReadBehaviorServiceImpl send messages
- Use kafkaStream to receive messages in real time and aggregate content
    - Integrate kafkaStream into the leadnews-article microservice
    - Define an entity class for the encapsulation of scores after aggregation
    - Define a stream, receive messages, and aggregate them
- Recalculate the score of the article and update it to the database and cache
    - Add methods in ApArticleService to update article score values in the database
    - The monitor is defined, the aggregated data is received, and the score of the article is recalculated