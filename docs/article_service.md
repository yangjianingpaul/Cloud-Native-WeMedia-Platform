# App Article Loading Interface Definition
|          | **Load home page**         | **Load more**             | **Load latest**            |
| -------- | -------------------- | ------------------------ | ----------------------- |
| Interface Path | /api/v1/article/load | /api/v1/article/loadmore | /api/v1/article/loadnew |
| Request Method | POST                 | POST                     | POST                    |
| Parameter     | ArticleHomeDto       | ArticleHomeDto           | ArticleHomeDto          |
| Response Result | ResponseResult       | ResponseResult           | ResponseResult          |

# Article template engine: freemarker
* FreeMarker is a template engine: a general-purpose tool for generating output text (HTML pages, emails, configuration files, source code, etc.) based on templates and data to be changed. It is not intended for end users, but is a Java class library, a component that programmers can embed in the product they are developing.

![](/resources/freemarker.png)

# Object storage service MinIO
MinIO is an object storage service based on the Apache License v2.0 open source protocol. It can be used as a cloud storage solution to save massive pictures, videos, and documents. Thanks to the Golang implementation, the server can work on Windows, Linux, OS X, and FreeBSD. Simple configuration, basically copy the executable program, a single line command can be run.

# MinIO Management Console
![](/resources/MinIO.png)

# MinIO test cases

```java
public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("file_path");
            MinioClient minioClient = MinioClient.builder().credentials("username", "password")
                    .endpoint("MinIO_access_url").build();

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object("target_directory")
                    .contentType("file's_type")
                    .bucket("bucket_name")
                    .stream(fileInputStream, fileInputStream.available(), -1).build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

# We-media articles off the shelf
* kafka decouple the we-media microservice from the article microservice.

# We media articles off the shelf
* kafka decouple the we-media microservice from the article microservice.

# Description of the loading and unloading process
![](/resources/up_or_down.png)

# content search
- Article search

  - set up environment of ElasticSearch

  - Create an index library

  - Article search multi-condition compound query

  - Index data synchronization

- search history

  - set up environment of Mongodb

  - Save search history asynchronously

  - View the search history list

  - Delete search history

- Associative word query

  - The source of associative words

  - Associative word function realization


## Set up the ElasticSearch environment
### 1) Pull image

```shell
docker pull elasticsearch:7.4.0
```

### 2) Create a container

```shell
docker run -id --name elasticsearch -d --restart=always -p 9200:9200 -p 9300:9300 -v /usr/share/elasticsearch/plugins:/usr/share/elasticsearch/plugins -e "discovery.type=single-node" elasticsearch:7.4.0
```

### 3) Configure the Chinese word divider ik

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

## app side article search
- The user enters a list of key searchable articles

- Keywords are highlighted

- The article list display is the same as the home display, when the user clicks on an article, the article details can be viewed

## Create indexes and mappings
- Add mappings using postman
    - put request add mappings ： http://192.168.31.125:9200/app_info_article
    - GET requests query mappings：http://192.168.31.125:9200/app_info_article
    - DELETE requests to delete indexes and mappings：http://192.168.31.125:9200/app_info_article
    - GET request, query all documents：http://192.168.31.125:9200/app_info_article/_search

## Data initialization to the es index library
```java
package com.heima.es;

import com.alibaba.fastjson.JSON;
import com.heima.es.mapper.ApArticleMapper;
import com.heima.es.pojo.SearchArticleVo;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApArticleTest {

    @Autowired
    private ApArticleMapper apArticleMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * Note: The import of data volume, if the amount of data is too large, need to page import
     * @throws Exception
     */
    @Test
    public void init() throws Exception {
//        1.Query all eligible article data
        List<SearchArticleVo> searchArticleVos = apArticleMapper.loadArticleList();

        BulkRequest bulkRequest = new BulkRequest("app_info_article");
//        2.Import es index libraries in batches
        for (SearchArticleVo searchArticleVo : searchArticleVos) {
            IndexRequest indexRequest = new IndexRequest()
                    .id(searchArticleVo.getId().toString())
                    .source(JSON.toJSONString(searchArticleVo), XContentType.JSON);
//            Batch add data
            bulkRequest.add(indexRequest);
        }
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }
}
```

## Test
- postman Queries data in all es GET request： http://192.168.31.125:9200/app_info_article/_search

## Articles are automatically reviewed and indexed
![](/resources/create_search_index.png)

* The article collects data and sends messages in the buildArticleToMinIO method in the ArticleFreemarkerService of the microservice.

## app Search - Search records
- Display 10 search records of the user, in reverse chronological order according to the search keyword
- You can delete the search history
- Save the history records, save 10, and delete the oldest history records

## Installing mongoDb
- Pull image

```shell
docker pull mongo
```

- Create a container

```shell
docker run -di --name mongo-service --restart=always -p 27017:27017 -v ~/data/mongodata:/data mongo
```

- In the search method of ArticleSearchService asynchronous invocation 'apUserSearchService.insert' method save history.

## Load a list of search records
- Query information based on the current user in reverse chronological order
|          | **Introduction**             |
| -------- | -------------------- |
| Interface path | /api/v1/history/load |
| request method | POST                 |
| parameter     | Null                   |
| response result | ResponseResult       |

## Delete search history
- Delete based on the search history id
|          | **Introduction**            |
| -------- | ------------------- |
| Interface path | /api/v1/history/del |
| request method | POST                |
| parameter     | HistorySearchDto    |
| response result | ResponseResult      |

# Keyword association
- Maintain search terms in the ap_associate_words table in mongoDB
|          | **Introduction**                 |
| -------- | ------------------------ |
| Interface path | /api/v1/associate/search |
| request method | POST                     |
| parameter     | UserSearchDto            |
| response result | ResponseResult           |
