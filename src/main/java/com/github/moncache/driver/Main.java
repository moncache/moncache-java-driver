package com.github.moncache.driver;

import com.github.moncache.driver.format.Formatter;
import com.github.moncache.driver.mongodb.MongoClient;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class Main {
    public static void main(String[] args) {
//        MongoClient mongoClient = new MongoClient("mongodb://localhost:27017/test");
//
//        DBObject document = new BasicDBObject();
//        document.put("email", "jxcoder@ya.ru");
//
//        mongoClient.getDB().save("emails", document);


        DBObject user1 = new BasicDBObject();
        user1.put("_id", new ObjectId());
        user1.put("login", "jxcoder");
        user1.put("email", "jxcoder@ya.ru");

        DBObject user2 = new BasicDBObject();
        user2.put("_id", new ObjectId());
        user2.put("login", "atygaev.mi");
        user2.put("email", "atygaev.mi@gmail.com");

        BasicDBList users = new BasicDBList();
        users.add(user1);
        users.add(user2);

        DBObject document = new BasicDBObject();
        document.put("status", "ok");
        document.put("data", users);

        System.out.println(document.toString());
        System.out.println();

        String s = Formatter.toString(document);

        System.out.println(s);
    }
}
