# 1.Usage Scenario:
* Massive data: Search, analysis, computation
* reverse index

# 2.Index base operation：
* mapping{
		properties{}
		type{text\keyword}
		index{}
		analyzer{}
	}
* PUT 'index_base_name'
* Delete 'index_base_name'
* Get 'index_base_name'

# 3.Document operation：
* POST(Get/Delete/Put) /'index_base_name'/_doc/'document_id'
* POST:Full modification
* Put:Incremental modification

# 4.SpringBoot's Elasticsearch Client Initialization：
- RestHighLevelClient client;
- client=new RestHighLevelClient(RestClient.builder(HttpHost.create("http://")))

# 5.SpringBoot's index base operation：

```java
CreateIndexRequest request;
request.source(MAPPING_TEMPLATE, XContentType.JSON);
client.indices().create(request);

DeleteIndexRequest request;
client.indices.delete(request);

GetIndexRequest request;
client.indices.exists(request);
```

# 6.SpringBoot's document operation：

```java
IndexRequest request;
request.source(JSON.toJSONString(Dto), XContentType.JSON);
client.index(request);

GetRequest("indexName","id") request;
GetResponse response=client.get(request);

UpdateRequest("indexName", "id") request;
request.doc("age",18,"name","Rose");
client.update(request);

DeleteRequest("indexName","id") request;
client.delete(request);

BulkRequest request;
for () {
	request.add(new IndexRequest("indexName").id().source("json", XContentType.JSON));
}
client.bulk(request);
```

# 7.Query operation：
* Query all：match_all
* full-text search：match_query\multi_match_query
* precise query：Find data based on the exact term value

```java
Get /indexName/_search
{
	“query”:{
		“match_all”(match\multi_match):{
			“field”:”text”
		}
	}
}

Get /indexName/_search
{
	“query”:{
		“term”:{
			“field”:{
				“value”:”value”
			}
		}
	}
}

Get /indexName/_search
{
	“query”:{
		“match_all”:{}
	},
	“highLight”:{
		“fields”:{
			“Field”:{
				“pre_tags”:”<em>”,
				“post_tags”:”</em>”
			}
		}
	}
	“from”: 9000,
	“size”:10,
	“sort”:[
		{
			“field”:”desc”
		}
	]
}
```



# 8.SpringBoot's query operation：

```java
SearchRequest request  = new SearchRequest(“hotel”);
request.source().query(QueryBulder.matchAllQuery());
SearchResponse response = client.search(request, RequestOptions.DEFAULT);

SearchHits[] hits=response.getHits().getHits();
for(SearchHit hit:hits){
	String json=hit.getSourceAsString();
}

source.query();
source.sort();
source.aggregation();
source.highlighter();
source.size();
source.from();

Querybulders.matchAllQuery();
Querybulders.multiMatchAllQuery();
Querybuilders.boolQuery();
Querybulders.rangeQuery();
Querybuilders.termQuery();
```

# 9.boolean query：
```java
Boolean query:
Get /hotel/_search
{
	“query”:{
		“bool”:{
			“must”:[
				{“term”:{“city”:”上海”}}
			],
			“should”:[
				{“term”:{“brand”:”皇冠假日”}},
				{“term”:{“brand”:”华美达”}}
			],
			“must_not”:[
				{“range”:{“price”:{“lte”:500}}}
			],
			“filter”:[
				{“range”:{“score”:{“gte”:45}}}
			]
		}
	}
}
```
# 10.Aggregation query：
数据聚合：bucket(count)\metric(min\max\avg)\pipeline

```java
Get /hotel/_search
{
	“size”:0,
	“aggs”:{
		“brandAgg”:{//aggregation name
			“terms”:{//aggregation type
				“field”:”brand”,
				“size”:20
				“aggs”:{
					“score_stats”:{
						“stats”:{
							“field”:”score”
						}
					}
				}
			}
		}
	}
}
```

```java
Source.aggregation(AggregationBuilders.terms().field().size());
```
