# Usage Scenario
* High concurrency, massive data, high scalability
* Large amount of data, write frequently, low value data, transactional requirements are not required

## 1.collection operation:
- mongoTemplate.collectionExists();
- mongoTemplate.createCollection();
- mongoTemplate.dropCollection();

## 2.Data modification operation:
- mongoTemplate.save();
- mongoTemplate.insert();
- mongoTemplate.findAll();
- mongoTemplate.findOne();
- mongoTemplate.findById();

## 3.Data query operation:
- Query query = new Query(Criteria.where().gte());
- Query query = new Query(Criteria.where().regex());

- Criteria criteria = new Criteria();
- Criteria.andOperator(Criteria.where().gt(),Criteria.where().gt());

## 4.Data update operation:
- Update update = new Update();
- Update.set();

- UpdateResult updateResult = mongoTemplate.updateFirst(query, update, class);
- mongoTemplate.remove(new Query(),class);

## 5.aggregation operation:
- GroupOperation groupOperation = Aggregation.group().sum().as();
- MatchOperation matchOperation = Aggregation.match(Criteria.where().gt());