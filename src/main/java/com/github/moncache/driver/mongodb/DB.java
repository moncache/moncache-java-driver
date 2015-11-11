package com.github.moncache.driver.mongodb;

import com.github.moncache.driver.MonCacheDriver;
import com.mongodb.DBObject;

import java.util.List;

public class DB {

    private String name;

    public DB(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void insert(String collectionName, DBObject document) {
        MonCacheDriver.insert(getName(), collectionName, document);
    }

    public void save(String collectionName, DBObject document) {
        MonCacheDriver.save(getName(), collectionName, document);
    }

    public List<DBObject> find(String collectionName, DBObject query, DBObject projection) {
        return MonCacheDriver.find(getName(), collectionName, query, projection);
    }

    public DBObject findOne(String collectionName, DBObject query, DBObject projection) {
        return MonCacheDriver.findOne(getName(), collectionName, query, projection);
    }

    public void update(String collectionName, DBObject query, DBObject modifications, DBObject parameters) {
        MonCacheDriver.update(getName(), collectionName, query, modifications, parameters);
    }

    public void remove(String collectionName, DBObject query, DBObject parameters) {
        MonCacheDriver.remove(getName(), collectionName, query, parameters);
    }
}