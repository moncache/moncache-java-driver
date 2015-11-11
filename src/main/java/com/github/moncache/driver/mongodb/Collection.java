package com.github.moncache.driver.mongodb;

import com.mongodb.DBObject;

import java.util.List;

public class Collection {

    private DB db;

    private String name;

    public Collection(DB db, String name) {
        this.db = db;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void insert(DBObject document) {
        db.insert(getName(), document);
    }

    public void save(DBObject document) {
        db.save(getName(), document);
    }

    public List<DBObject> find(DBObject query, DBObject projection) {
        return db.find(getName(), query, projection);
    }

    public DBObject findOne(DBObject query, DBObject projection) {
        return db.findOne(getName(), query, projection);
    }

    public void update(DBObject query, DBObject modifications, DBObject parameters) {
        db.update(getName(), query, modifications, parameters);
    }

    public void remove(DBObject query, DBObject parameters) {
        db.remove(getName(), query, parameters);
    }
}