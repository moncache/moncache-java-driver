package com.github.moncache.driver;

import com.github.moncache.driver.format.Formatter;
import com.intersys.globals.Connection;
import com.intersys.globals.ConnectionContext;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

public class MonCacheDriver {

    private static class CacheConnection {
        static final Connection CONNECTION;
        static {
            String namespace = System.getProperty("MONCACHE_NAMESPACE");

            String username = System.getProperty("MONCACHE_USERNAME");

            String password = System.getProperty("MONCACHE_PASSWORD");

            CONNECTION = ConnectionContext.getConnection();

            if (!CONNECTION.isConnected()) {
                CONNECTION.connect(namespace, username, password);
            }
        }
    }

    private static DBObject callCacheMethod(String method, String... args) {
        Object dbResult = CacheConnection.CONNECTION.callClassMethod("MonCache.Driver", method, args);

        DBObject resultDocument = Formatter.fromString((String) dbResult);

        if (resultDocument.containsField("status") && resultDocument.get("status").equals("ok") && resultDocument.containsField("data")) {
            return resultDocument;
        }

        throw new RuntimeException("Error while \"" + method + "\" executed.");
    }

    public static void insert(String dbName, String collectionName, DBObject document) {
        String encodedDocument = Formatter.toString(document);

        callCacheMethod("insert", dbName, collectionName, encodedDocument);
    }

    public static void save(String dbName, String collectionName, DBObject document) {
        String encodedDocument = Formatter.toString(document);

        callCacheMethod("save", dbName, collectionName, encodedDocument);
    }

    public static List<DBObject> find(String dbName, String collectionName, DBObject query, DBObject projection) {
        String encodedQuery = Formatter.toString(query);

        String encodedProjection = Formatter.toString(projection);

        DBObject dbResult = callCacheMethod("find", dbName, collectionName, encodedQuery, encodedProjection);

        if (!(dbResult.get("data") instanceof BasicDBList)) {
            throw new RuntimeException("Method \"find\" didn't return documents.");
        }

        BasicDBList dbDocuments = (BasicDBList) dbResult.get("data");

        List<DBObject> documents = new ArrayList<DBObject>();

        for (int i = 0; i < dbDocuments.size(); i++) {
            dbDocuments.add(documents.get(i));
        }

        return documents;
    }

    public static DBObject findOne(String dbName, String collectionName, DBObject query, DBObject projection) {
        String encodedQuery = Formatter.toString(query);

        String encodedProjection = Formatter.toString(projection);

        DBObject dbResult = callCacheMethod("findOne", dbName, collectionName, encodedQuery, encodedProjection);

        if (dbResult.get("data") == null) {
            return null;
        }

        if (!(dbResult.get("data") instanceof BasicDBObject)) {
            throw new RuntimeException("Method \"find\" didn't return document.");
        }

        return (DBObject) dbResult.get("data");
    }

    public static void update(String dbName, String collectionName, DBObject query, DBObject modifications, DBObject parameters) {
        String encodedQuery = Formatter.toString(query);

        String encodedModifications = Formatter.toString(modifications);

        String encodedParameters = Formatter.toString(parameters);

        callCacheMethod("update", dbName, collectionName, encodedQuery, encodedModifications, encodedParameters);
    }

    public static void remove(String dbName, String collectionName, DBObject query, DBObject parameters) {
        String encodedQuery = Formatter.toString(query);

        String encodedParameters = Formatter.toString(parameters);

        callCacheMethod("remove", dbName, collectionName, encodedQuery, encodedParameters);
    }
}
