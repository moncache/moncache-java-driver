package com.github.moncache.driver.format;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

/**
 * Decodes MON (MonCache Object Notation) to MongoDB document.
 */
public class FormatDecoder {
    /**
     * Returns boolean value
     * @param valueInfo MON (MonCache Object Notation) of the Boolean value
     * @return boolean
     */
    private static Boolean decodeBoolean(ObjectNode valueInfo) {
        return valueInfo.get("v").asInt() != 0;
    }

    /**
     * Returns number value
     * @param valueInfo MON (MonCache Object Notation) of the Number value
     * @return number
     */
    private static Number decodeNumber(ObjectNode valueInfo) {
        return valueInfo.get("v").numberValue();
    }

    /**
     * Returns string value
     * @param valueInfo MON (MonCache Object Notation) of the String value
     * @return string
     */
    private static String decodeString(ObjectNode valueInfo) {
        return valueInfo.get("v").asText();
    }

    /**
     * Returns ObjectId instance
     * @param valueInfo MON (MonCache Object Notation) of the ObjectId instance
     * @return ObjectId
     */
    private static ObjectId decodeObjectId(ObjectNode valueInfo) {
        return new ObjectId(valueInfo.get("v").asText());
    }

    /**
     * Returns BasicDBList instance
     * @param valueInfo MON (MonCache Object Notation) of the BasicDBList instance
     * @return BasicDBList
     */
    private static BasicDBList decodeArray(ObjectNode valueInfo) {
        BasicDBList array = new BasicDBList();

        ArrayNode encodedItems = (ArrayNode) valueInfo.get("v");

        for (int i = 0; i < encodedItems.size(); i++) {
            ObjectNode encodedItem = (ObjectNode) encodedItems.get(i);

            Object item = decode(encodedItem);

            array.add(item);
        }

        return array;
    }

    /**
     * Returns BasicDBObject instance
     * @param valueInfo MON (MonCache Object Notation) of the BasicDBObject instance
     * @return BasicDBObject
     */
    private static BasicDBObject decodeObject(ObjectNode valueInfo) {
        BasicDBObject object = new BasicDBObject();

        ArrayNode encodedFields = (ArrayNode) valueInfo.get("v");

        for (int i = 0; i < encodedFields.size(); i++) {
            ObjectNode encodedField = (ObjectNode) encodedFields.get(i);

            Object item = decode(encodedField);

            object.put(encodedField.get("n").asText(), item);
        }

        return object;
    }

    /**
     * Returns MonCacheType value
     * @param data MON (MonCache Object Notation) of the MonCacheType instance
     * @return MonCacheType
     */
    public static Object decode(ObjectNode data) {
        String valueType = data.get("t").asText();

        if ("null".equals(valueType)) {
            return null;
        }

        if ("boolean".equals(valueType)) {
            return decodeBoolean(data);
        }

        if ("number".equals(valueType)) {
            return decodeNumber(data);
        }

        if ("string".equals(valueType)) {
            return decodeString(data);
        }

        if ("objectid".equals(valueType)) {
            return decodeObjectId(data);
        }

        if ("array".equals(valueType)) {
            return decodeArray(data);
        }

        if ("object".equals(valueType)) {
            return decodeObject(data);
        }

        throw new RuntimeException("Unknown MonCache type \"" + valueType + "\"");
    }
}