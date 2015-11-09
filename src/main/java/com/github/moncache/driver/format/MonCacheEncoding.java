package com.github.moncache.driver.format;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.github.nsnjson.encoding.style.ArrayStyleDynamicEncoding;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.Optional;

import static com.github.nsnjson.format.Format.TYPE_MARKER_ARRAY;
import static com.github.nsnjson.format.Format.TYPE_MARKER_OBJECT;

public class MonCacheEncoding extends ArrayStyleDynamicEncoding {

    private static final int TYPE_MARKER_ID = 6;

    @Override
    public Optional<JsonNode> encodeDynamic(Object data) {
        Optional<JsonNode> presentationOption = super.encodeDynamic(data);

        if (presentationOption.isPresent()) {
            return presentationOption;
        }

        if (data instanceof Number) {
            return encode((Number) data);
        }

        if (data instanceof String) {
            return encode((String) data);
        }

        if (data instanceof Boolean) {
            return encodeBoolean(BooleanNode.valueOf((Boolean) data));
        }

        if (data instanceof ObjectId) {
            return encode((ObjectId) data);
        }

        if (data instanceof DBObject) {
            return encode((DBObject) data);
        }

        return Optional.empty();
    }

    private Optional<JsonNode> encode(ObjectId objectId) {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode presentation = objectMapper.createArrayNode();
        presentation.add(TYPE_MARKER_ID);
        presentation.add(objectId.toHexString());

        return Optional.of(presentation);
    }

    private Optional<JsonNode> encode(DBObject data) {
        if (data instanceof BasicDBObject) {
            return encode((BasicDBObject) data);
        }

        if (data instanceof BasicDBList) {
            return encode((BasicDBList) data);
        }

        return Optional.empty();
    }

    private Optional<JsonNode> encode(BasicDBObject dbObject) {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode presentation = objectMapper.createArrayNode();
        presentation.add(TYPE_MARKER_OBJECT);

        for (String key : dbObject.keySet()) {
            Object value = dbObject.get(key);

            encodeDynamic(value).ifPresent(valuePresentation -> {
                ArrayNode fieldPresentation = objectMapper.createArrayNode();
                fieldPresentation.add(key);
                fieldPresentation.add(valuePresentation);

                presentation.add(fieldPresentation);
            });
        }

        return Optional.of(presentation);
    }

    private Optional<JsonNode> encode(BasicDBList dbList) {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode presentation = objectMapper.createArrayNode();
        presentation.add(TYPE_MARKER_ARRAY);

        for (Object item : dbList) {
            encodeDynamic(item).ifPresent(presentation::add);
        }

        return Optional.of(presentation);
    }

    private Optional<JsonNode> encode(Number value) {
        if (value instanceof Byte) {
            return encodeNumber(IntNode.valueOf(value.byteValue()));
        }

        if (value instanceof Short) {
            return encodeNumber(IntNode.valueOf(value.shortValue()));
        }

        if (value instanceof Integer) {
            return encodeNumber(IntNode.valueOf(value.intValue()));
        }

        if (value instanceof Long) {
            return encodeNumber(LongNode.valueOf(value.longValue()));
        }

        if (value instanceof Float) {
            return encodeNumber(DoubleNode.valueOf(value.floatValue()));
        }

        if (value instanceof Double) {
            return encodeNumber(DoubleNode.valueOf(value.doubleValue()));
        }

        return Optional.empty();
    }

    private Optional<JsonNode> encode(String value) {
        return encodeString(TextNode.valueOf(value));
    }

}