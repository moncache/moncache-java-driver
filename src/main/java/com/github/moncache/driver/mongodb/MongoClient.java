package com.github.moncache.driver.mongodb;

public class MongoClient {

    private String dbName;

    public MongoClient(String url) {
        dbName = url.substring(url.lastIndexOf('/') + 1);
    }

    public static DB getDB(String dbName) {
        return new DB(dbName);
    }

    public DB getDB() {
        return new DB(dbName);
    }
}
