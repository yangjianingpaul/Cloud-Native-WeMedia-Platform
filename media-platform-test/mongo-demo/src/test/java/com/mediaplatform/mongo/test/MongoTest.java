package com.mediaplatform.mongo.test;


import com.mediaplatform.mongo.MongoApplication;
import com.mediaplatform.mongo.pojo.ApAssociateWords;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = MongoApplication.class)
@RunWith(SpringRunner.class)
public class MongoTest {


    @Autowired
    private MongoTemplate mongoTemplate;

    //save
    @Test
    public void saveTest() {
        for (int i = 0; i < 10; i++) {
            ApAssociateWords apAssociateWords = new ApAssociateWords();
            apAssociateWords.setAssociateWords("helloWorld");
            apAssociateWords.setCreatedTime(new Date());
            mongoTemplate.save(apAssociateWords);
        }
    }

    //query next
    @Test
    public void saveFindOne() {
        ApAssociateWords apAssociateWords = mongoTemplate.findById("65a6812c66692827b9907ab2", ApAssociateWords.class);
        System.out.println(apAssociateWords);
    }

    //conditional queries
    @Test
    public void testQuery() {
        Query query = Query.query(Criteria.where("associateWords").is("helloWorld"))
                .with(Sort.by(Sort.Direction.DESC, "createdTime"));
        List<ApAssociateWords> apAssociateWordsList = mongoTemplate.find(query, ApAssociateWords.class);
        System.out.println(apAssociateWordsList);
    }

//    delete
    @Test
    public void testDel() {
        mongoTemplate.remove(Query.query(Criteria.where("associateWords").is("helloWorld")), ApAssociateWords.class);
    }
}
