# Operating Environment Configuration
## Website front-end environment configuration: nginx configures front-end static resources to achieve reverse proxy
![](/resources/nginxConfiguration.png)

```shell
upstream  heima-admin-gateway{
    server localhost:51603;
}

server {
	listen 8803;
	location / {
		root html/admin-web/;
		index index.html;
	}
	
	location ~/service_6001/(.*) {
		proxy_pass http://heima-admin-gateway/$1;
		proxy_set_header HOST $host;  # Does not change the value of the source request header
		proxy_pass_request_body on;  #The fetch request body is enabled
		proxy_pass_request_headers on;  #Open the fetch request header
		proxy_set_header X-Real-IP $remote_addr;   # Records the IP address of the client that actually made the request
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;  #Record agent information
	}
}
```

```shell
upstream  heima-app-gateway{
    server localhost:51601;
}

server {
	listen 8801;
	location / {
		root html/app-web/;
		index index.html;
	}
	
	location ~/app/(.*) {
		proxy_pass http://heima-app-gateway/$1;
		proxy_set_header HOST $host;  # Does not change the value of the source request header
		proxy_pass_request_body on;  #The fetch request body is enabled
		proxy_pass_request_headers on;  #Open the fetch request header
		proxy_set_header X-Real-IP $remote_addr;   # Records the IP address of the client that actually made the request
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;  #Record agent information
	}
}
```

```shell
upstream  heima-wemedia-gateway{
    server localhost:51602;
}

server {
	listen 8802;
	location / {
		root html/wemedia-web/;
		index index.html;
	}
	
	location ~/wemedia/MEDIA/(.*) {
		proxy_pass http://heima-wemedia-gateway/$1;
		proxy_set_header HOST $host;  # Does not change the value of the source request header
		proxy_pass_request_body on;  #The fetch request body is enabled
		proxy_pass_request_headers on;  #Open the fetch request header
		proxy_set_header X-Real-IP $remote_addr;   # Records the IP address of the client that actually made the request
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;  #Record agent information
	}
}
```

## Web backend environment configuration: docker container technology

### nacos

* 1. Pull the image

```shell
docker pull nacos/nacos-server:1.2.0
```

* 2. Create a container

```shell
docker run --env MODE=standalone --name nacos --restart=always  -d -p 8848:8848 nacos/nacos-server:1.2.0
```

### minio

* 1. Pull the image

```shell
docker pull minio/minio:RELEASE.2021-06-17T00-10-46Z.fips
```

* 2. Create a container

```shell
docker run -p 9000:9000 --name minio -d --restart=always -e "MINIO_ACCESS_KEY=minio" -e "MINIO_SECRET_KEY=minio123" -v /home/data:/data -v /home/config:/root/.minio minio/minio:RELEASE.2021-06-17T00-10-46Z.fips server /data
```

### redis

* 1. Pull the image

```shell
docker pull redis
```

* 2. Create a container

```shell
docker run -d --name redis --restart=always -p 6379:6379 redis --requirepass "leadnews"
```

### zookeeper

* 1. Pull the image

```shell
docker pull zookeeper:3.4.14
```

* 2. Create a container

```shell
docker run -d --name zookeeper -p 2181:2181 zookeeper:3.4.14
```

### kafka

* 1. Pull the image

```shell
docker pull wurstmeister/kafka:2.12-2.3.1
```

* 2. Create a container

```shell
docker run -d --name kafka \
--env KAFKA_ADVERTISED_HOST_NAME=192.168.200.130 \
--env KAFKA_ZOOKEEPER_CONNECT=192.168.200.130:2181 \
--env KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.200.130:9092 \
--env KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
--env KAFKA_HEAP_OPTS="-Xmx256M -Xms256M" \
--net=host wurstmeister/kafka:2.12-2.3.1
```

### elasticsearch

* 1. Pull the image

```shell
docker pull elasticsearch:7.4.0
```

* 2. Create a container

```shell
docker run -id --name elasticsearch -d --restart=always -p 9200:9200 -p 9300:9300 -v /usr/share/elasticsearch/plugins:/usr/share/elasticsearch/plugins -e "discovery.type=single-node" elasticsearch:7.4.0
```

### ik word divider

```shell
#Switch directory
cd /usr/share/elasticsearch/plugins
#New directory
mkdir analysis-ik
cd analysis-ik
#root Copies files to the root directory
mv elasticsearch-analysis-ik-7.4.0.zip /usr/share/elasticsearch/plugins/analysis-ik
#decompressing files
cd /usr/share/elasticsearch/plugins/analysis-ik
unzip elasticsearch-analysis-ik-7.4.0.zip
```

### mongoDB

* 1. Pull the image

```shell
docker pull mongo
```

* 2. Create a container

```shell
docker run -di --name mongo-service --restart=always -p 27017:27017 -v ~/data/mongodata:/data mongo
```

### xxl-job

* 1. Create a mysql container and initialize the SQL script for xxl-job

```shell
docker run -p 3306:3306 --name mysql57 \
-v /opt/mysql/conf:/etc/mysql \
-v /opt/mysql/logs:/var/log/mysql \
-v /opt/mysql/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql:5.7
```

* 2. Pull the image

```shell
docker pull xuxueli/xxl-job-admin:2.3.0
```

* 3. Create a container

```shell
docker run -e PARAMS="--spring.datasource.url=jdbc:mysql://192.168.31.125:3306/xxl_job?Unicode=true&characterEncoding=UTF-8 \
--spring.datasource.username=root \
--spring.datasource.password=root" \
-p 8888:8080 -v /tmp:/data/applogs \
--name xxl-job-admin --restart=always  -d xuxueli/xxl-job-admin:2.3.0
```
