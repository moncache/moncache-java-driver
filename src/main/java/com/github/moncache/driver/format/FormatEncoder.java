package com.github.moncache.driver.format;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import java.util.Set;

/**
 * Encodes MongoDB document to MON (MonCache Object Notation).
 */
public class FormatEncoder {
    /**
     * Returns MON (MonCache Object Notation) presentation of the Null value.
     * @return MON
     */
    private static ObjectNode encodeNull() {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode valueInfo = objectMapper.createObjectNode();
        valueInfo.put("t", "null");
        valueInfo.put("v", "");

        return valueInfo;
    }

    /**
     * Returns MON (MonCache Object Notation) presentation of the Boolean value.
     * @param value boolean value
     * @return MON
     */
    private static ObjectNode encodeBoolean(Boolean value) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode valueInfo = objectMapper.createObjectNode();
        valueInfo.put("t", "boolean");
        valueInfo.put("v", value ? 1 : 0);

        return valueInfo;
    }

    /**
     * Returns MON (MonCache Object Notation) presentation of the Number value.
     * @param value number value
     * @return MON
     */
    private static ObjectNode encodeNumber(Number value) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode valueInfo = objectMapper.createObjectNode();
        valueInfo.put("t", "number");

        if (value instanceof Byte) {
            valueInfo.put("v", value.byteValue());
        }
        else if (value instanceof Short) {
            valueInfo.put("v", value.shortValue());
        }
        else if (value instanceof Integer) {
            valueInfo.put("v", value.intValue());
        }
        else if (value instanceof Long) {
            valueInfo.put("v", value.longValue());
        }
        else if (value instanceof Float) {
            valueInfo.put("v", value.floatValue());
        }
        else if (value instanceof Double) {
            valueInfo.put("v", value.doubleValue());
        }

        return valueInfo;
    }

    /**
     * Returns MON (MonCache Object Notation) presentation of the String value.
     * @param value string value
     * @return MON
     */
    private static ObjectNode encodeString(String value) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode valueInfo = objectMapper.createObjectNode();
        valueInfo.put("t", "string");
        valueInfo.put("v", value);

        return valueInfo;
    }

    /**
     * Returns MON (MonCache Object Notation) presentation of the ObjectId.
     * @param value ObjectId instance
     * @return MON
     */
    private static ObjectNode encodeObjectId(ObjectId value) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode valueInfo = objectMapper.createObjectNode();
        valueInfo.put("t", "objectid");
        valueInfo.put("v", value.toHexString());

        return valueInfo;
    }

    /**
     * Returns MON (MonCache Object Notation) presentation of the BasicDBList.
     * @param array BasicDBList instance
     * @return MON
     */
    private static ObjectNode encodeArray(BasicDBList array) {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode encodedItems = objectMapper.createArrayNode();

        Set<String> indexes = array.keySet();

        for (String index: indexes) {
            Object item = array.get(index);

            ObjectNode encodedItem = encode(item);

            encodedItems.add(encodedItem);
        }

        ObjectNode arrayInfo = objectMapper.createObjectNode();
        arrayInfo.put("t", "array");
        arrayInfo.set("v", encodedItems);

        return arrayInfo;
    }

    /**
     * Returns MON (MonCache Object Notation) presentation of the BasicDBObject.
     * @param object BasicDBObject instance
     * @return MON
     */
    private static ObjectNode encodeObject(BasicDBObject object) {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode encodedFields = objectMapper.createArrayNode();

        Set<String> names = object.keySet();

        for (String name: names) {
            Object value = object.get(name);

            ObjectNode encodedValue = encode(value);

            ObjectNode encodedField = objectMapper.createObjectNode();
            encodedField.put("n", name);
            encodedField.set("t", encodedValue.get("t"));

            if (encodedValue.has("v")) {
                encodedField.set("v", encodedValue.get("v"));
            }

            encodedFields.add(encodedField);
        }

        ObjectNode objectInfo = objectMapper.createObjectNode();
        objectInfo.put("t", "object");
        objectInfo.set("v", encodedFields);

        return objectInfo;
    }

    /**
     * Returns MON (MonCache Object Notation) presentation of the MonCacheType value.
     * @param value MonCacheType value
     * @return MON
     */
    public static ObjectNode encode(Object value) {
        if (value == null) {
            return encodeNull();
        }

        if (value instanceof Boolean) {
            return encodeBoolean((Boolean) value);
        }

        if (value instanceof Number) {
            return encodeNumber((Number) value);
        }

        if (value instanceof String) {
            return encodeString((String) value);
        }

        if (value instanceof ObjectId) {
            return encodeObjectId((ObjectId) value);
        }

        if (value instanceof BasicDBObject) {
            return encodeObject((BasicDBObject) value);
        }

        if (value instanceof BasicDBList) {
            return encodeArray((BasicDBList) value);       
        }

        throw new RuntimeException("Unknown MonCacheType \"" + value.getClass() + "\"");
    }
}