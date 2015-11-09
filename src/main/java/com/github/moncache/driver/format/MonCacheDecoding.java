package com.github.moncache.driver.format;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.nsnjson.decoding.style.ArrayStyleDynamicDecoding;
import com.github.nsnjson.format.Format;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.Optional;

public class MonCacheDecoding extends ArrayStyleDynamicDecoding {
    @Override
    public Optional<Object> decodeDynamic(JsonNode presentation) {

        if (presentation.get(Format.INDEX_TYPE).asInt() == 0) {
            return decodeNull().flatMap(Optional::of);
        }

        if (presentation.get(Format.INDEX_TYPE).asInt() == 1) {
            return decodeNumber(presentation).flatMap(json -> {
                if (json.isInt()) {
                    return Optional.of(json.intValue());
                }

                if (json.isLong()) {
                    return Optional.of(json.longValue());
                }

                if (json.isDouble()) {
                    return Optional.of(json.doubleValue());
                }

                return Optional.empty();
            });
        }

        if (presentation.get(Format.INDEX_TYPE).asInt() == 2) {
            return decodeString(presentation).flatMap(json -> Optional.of(json.asText()));
        }

        if (presentation.get(Format.INDEX_TYPE).asInt() == 3) {
            return decodeBoolean(presentation).flatMap(json -> Optional.of(json.asBoolean()));
        }

        if (presentation.get(Format.INDEX_TYPE).asInt() == 4) {
            return decodeDBList(presentation);
        }

        if (presentation.get(Format.INDEX_TYPE).asInt() == 5) {
            return decodeDBObject(presentation);
        }

        if (presentation.get(Format.INDEX_TYPE).asInt() == 6) {
            return decodeObjectId(presentation);
        }

        return Optional.empty();
    }

    private Optional<Object> decodeDBList(JsonNode presentation) {
        BasicDBList dbList = new BasicDBList();

        for (int i = Format.INDEX_VALUE; i < presentation.size(); i++) {
            JsonNode itemPresentation = presentation.get(i);

            decodeDynamic(itemPresentation).ifPresent(dbList::add);
        }

        return Optional.of(dbList);
    }

    private Optional<Object> decodeDBObject(JsonNode presentation) {
        BasicDBObject dbObject = new BasicDBObject();

        for (int i = Format.INDEX_VALUE; i < presentation.size(); i++) {
            JsonNode fieldPresentation = presentation.get(i);

            String name = fieldPresentation.get(Format.INDEX_NAME).asText();

            JsonNode valuePresentation = fieldPresentation.get(Format.INDEX_VALUE);

            decodeDynamic(valuePresentation).ifPresent(value -> dbObject.put(name, value));
        }

        return Optional.of(dbObject);
    }

    private Optional<Object> decodeObjectId(JsonNode presentation) {
        return Optional.of(new ObjectId(presentation.get(Format.INDEX_VALUE).asText()));
    }

}