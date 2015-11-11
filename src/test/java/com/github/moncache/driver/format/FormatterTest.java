package com.github.moncache.driver.format;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatterTest {

    private static final DBObject DOCUMENT_1;
    static {
        DOCUMENT_1 = new BasicDBObject("data", null);
    }

    private static final DBObject DOCUMENT_2;
    static {
        DOCUMENT_2 = new BasicDBObject("data", true);
    }

    private static final DBObject DOCUMENT_3;
    static {
        DOCUMENT_3 = new BasicDBObject("data", false);
    }

    private static final DBObject DOCUMENT_4;
    static {
        DOCUMENT_4 = new BasicDBObject("data", 213);
    }

    private static final DBObject DOCUMENT_5;
    static {
        DOCUMENT_5 = new BasicDBObject("data", "Hello world!");
    }

    private static final DBObject DOCUMENT_6;
    static {
        DOCUMENT_6 = new BasicDBObject("data", new ObjectId("55e3e118900621b760000001"));
    }

    private static final DBObject DOCUMENT_7;
    static {
        BasicDBList data = new BasicDBList();
        data.add(null);
        data.add(true);
        data.add(false);
        data.add(213);
        data.add("Hello world!");
        data.add(new ObjectId("55e3e118900621b760000001"));

        DOCUMENT_7 = new BasicDBObject("data", data);
    }

    private static final DBObject DOCUMENT_8;
    static {
        BasicDBList data = new BasicDBList();
        data.add(new BasicDBObject("nf", null));
        data.add(new BasicDBObject("tf", true));
        data.add(new BasicDBObject("ff", false));
        data.add(new BasicDBObject("nf", 213));
        data.add(new BasicDBObject("sf", "Hello world!"));
        data.add(new BasicDBObject("of", new ObjectId("55e3e118900621b760000001")));

        DOCUMENT_8 = new BasicDBObject("data", data);
    }

    private static final ObjectNode DOCUMENT_1_MON;
    static {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode dataField = objectMapper.createObjectNode();
        dataField.put("n", "data");
        dataField.put("t", "null");
        dataField.put("v", "");

        ArrayNode documentFields = objectMapper.createArrayNode();
        documentFields.add(dataField);

        DOCUMENT_1_MON = objectMapper.createObjectNode();
        DOCUMENT_1_MON.put("t", "object");
        DOCUMENT_1_MON.set("v", documentFields);
    }

    private static final ObjectNode DOCUMENT_2_MON;
    static {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode dataField = objectMapper.createObjectNode();
        dataField.put("n", "data");
        dataField.put("t", "boolean");
        dataField.put("v", 1);

        ArrayNode documentFields = objectMapper.createArrayNode();
        documentFields.add(dataField);

        DOCUMENT_2_MON = objectMapper.createObjectNode();
        DOCUMENT_2_MON.put("t", "object");
        DOCUMENT_2_MON.set("v", documentFields);
    }

    private static final ObjectNode DOCUMENT_3_MON;
    static {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode dataField = objectMapper.createObjectNode();
        dataField.put("n", "data");
        dataField.put("t", "boolean");
        dataField.put("v", 0);

        ArrayNode documentFields = objectMapper.createArrayNode();
        documentFields.add(dataField);

        DOCUMENT_3_MON = objectMapper.createObjectNode();
        DOCUMENT_3_MON.put("t", "object");
        DOCUMENT_3_MON.set("v", documentFields);
    }

    private static final ObjectNode DOCUMENT_4_MON;
    static {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode dataField = objectMapper.createObjectNode();
        dataField.put("n", "data");
        dataField.put("t", "number");
        dataField.put("v", 213);

        ArrayNode documentFields = objectMapper.createArrayNode();
        documentFields.add(dataField);

        DOCUMENT_4_MON = objectMapper.createObjectNode();
        DOCUMENT_4_MON.put("t", "object");
        DOCUMENT_4_MON.set("v", documentFields);
    }

    private static final ObjectNode DOCUMENT_5_MON;
    static {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode dataField = objectMapper.createObjectNode();
        dataField.put("n", "data");
        dataField.put("t", "string");
        dataField.put("v", "Hello world!");

        ArrayNode documentFields = objectMapper.createArrayNode();
        documentFields.add(dataField);

        DOCUMENT_5_MON = objectMapper.createObjectNode();
        DOCUMENT_5_MON.put("t", "object");
        DOCUMENT_5_MON.set("v", documentFields);
    }

    private static final ObjectNode DOCUMENT_6_MON;
    static {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode dataField = objectMapper.createObjectNode();
        dataField.put("n", "data");
        dataField.put("t", "objectid");
        dataField.put("v", "55e3e118900621b760000001");

        ArrayNode documentFields = objectMapper.createArrayNode();
        documentFields.add(dataField);

        DOCUMENT_6_MON = objectMapper.createObjectNode();
        DOCUMENT_6_MON.put("t", "object");
        DOCUMENT_6_MON.set("v", documentFields);
    }

    private static final ObjectNode DOCUMENT_7_MON;
    static {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode itemNull = objectMapper.createObjectNode();
        itemNull.put("t", "null");
        itemNull.put("v", "");

        ObjectNode itemTrue = objectMapper.createObjectNode();
        itemTrue.put("t", "boolean");
        itemTrue.put("v", 1);

        ObjectNode itemFalse = objectMapper.createObjectNode();
        itemFalse.put("t", "boolean");
        itemFalse.put("v", 0);

        ObjectNode itemNumber = objectMapper.createObjectNode();
        itemNumber.put("t", "number");
        itemNumber.put("v", 213);

        ObjectNode itemString = objectMapper.createObjectNode();
        itemString.put("t", "string");
        itemString.put("v", "Hello world!");

        ObjectNode itemObjectId = objectMapper.createObjectNode();
        itemObjectId.put("t", "objectid");
        itemObjectId.put("v", "55e3e118900621b760000001");

        ArrayNode items = objectMapper.createArrayNode();
        items.add(itemNull);
        items.add(itemTrue);
        items.add(itemFalse);
        items.add(itemNumber);
        items.add(itemString);
        items.add(itemObjectId);

        ObjectNode dataField = objectMapper.createObjectNode();
        dataField.put("n", "data");
        dataField.put("t", "array");
        dataField.set("v", items);

        ArrayNode documentFields = objectMapper.createArrayNode();
        documentFields.add(dataField);

        DOCUMENT_7_MON = objectMapper.createObjectNode();
        DOCUMENT_7_MON.put("t", "object");
        DOCUMENT_7_MON.set("v", documentFields);
    }

    private static final ObjectNode DOCUMENT_8_MON;
    static {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode fieldNull = objectMapper.createObjectNode();
        fieldNull.put("n", "nf");
        fieldNull.put("t", "null");
        fieldNull.put("v", "");

        ObjectNode fieldTrue = objectMapper.createObjectNode();
        fieldTrue.put("n", "tf");
        fieldTrue.put("t", "boolean");
        fieldTrue.put("v", 1);

        ObjectNode fieldFalse = objectMapper.createObjectNode();
        fieldFalse.put("n", "ff");
        fieldFalse.put("t", "boolean");
        fieldFalse.put("v", 0);

        ObjectNode fieldNumber = objectMapper.createObjectNode();
        fieldNumber.put("n", "nf");
        fieldNumber.put("t", "number");
        fieldNumber.put("v", 213);

        ObjectNode fieldString = objectMapper.createObjectNode();
        fieldString.put("n", "sf");
        fieldString.put("t", "string");
        fieldString.put("v", "Hello world!");

        ObjectNode fieldObjectId = objectMapper.createObjectNode();
        fieldObjectId.put("n", "of");
        fieldObjectId.put("t", "objectid");
        fieldObjectId.put("v", "55e3e118900621b760000001");

        ObjectNode[] fields = new ObjectNode[]{fieldNull, fieldTrue, fieldFalse, fieldNumber, fieldString, fieldObjectId};

        ArrayNode items = objectMapper.createArrayNode();

        for (ObjectNode field: fields) {
            ArrayNode objectFields = objectMapper.createArrayNode();
            objectFields.add(field);

            ObjectNode object = objectMapper.createObjectNode();
            object.put("t", "object");
            object.set("v", objectFields);

            items.add(object);
        }

        ObjectNode dataField = objectMapper.createObjectNode();
        dataField.put("n", "data");
        dataField.put("t", "array");
        dataField.set("v", items);

        ArrayNode documentFields = objectMapper.createArrayNode();
        documentFields.add(dataField);

        DOCUMENT_8_MON = objectMapper.createObjectNode();
        DOCUMENT_8_MON.put("t", "object");
        DOCUMENT_8_MON.set("v", documentFields);
    }

    private static void assertToString(DBObject document, ObjectNode mon) {
        assertEquals(Formatter.toString(document), mon.toString());
    }

    private static void assertFromString(ObjectNode mon, DBObject document) {
        assertEquals(Formatter.fromString(mon.toString()), document);
    }

    private static void assertConsistency(DBObject document) {
        assertEquals(document, Formatter.fromString(Formatter.toString(document)));
    }

    @Test
    public void assertToStringOnDocument1() {
        assertToString(DOCUMENT_1, DOCUMENT_1_MON);
    }

    @Test
    public void assertToStringOnDocument2() {
        assertToString(DOCUMENT_2, DOCUMENT_2_MON);
    }

    @Test
    public void assertToStringOnDocument3() {
        assertToString(DOCUMENT_3, DOCUMENT_3_MON);
    }

    @Test
    public void assertToStringOnDocument4() {
        assertToString(DOCUMENT_4, DOCUMENT_4_MON);
    }

    @Test
    public void assertToStringOnDocument5() {
        assertToString(DOCUMENT_5, DOCUMENT_5_MON);
    }

    @Test
    public void assertToStringOnDocument6() {
        assertToString(DOCUMENT_6, DOCUMENT_6_MON);
    }

    @Test
    public void assertToStringOnDocument7() {
        assertToString(DOCUMENT_7, DOCUMENT_7_MON);
    }

    @Test
    public void assertToStringOnDocument8() {
        assertToString(DOCUMENT_8, DOCUMENT_8_MON);
    }

    @Test
    public void assertFromStringOnDocument1() {
        assertFromString(DOCUMENT_1_MON, DOCUMENT_1);
    }

    @Test
    public void assertFromStringOnDocument2() {
        assertFromString(DOCUMENT_2_MON, DOCUMENT_2);
    }

    @Test
    public void assertFromStringOnDocument3() {
        assertFromString(DOCUMENT_3_MON, DOCUMENT_3);
    }

    @Test
    public void assertFromStringOnDocument4() {
        assertFromString(DOCUMENT_4_MON, DOCUMENT_4);
    }

    @Test
    public void assertFromStringOnDocument5() {
        assertFromString(DOCUMENT_5_MON, DOCUMENT_5);
    }

    @Test
    public void assertFromStringOnDocument6() {
        assertFromString(DOCUMENT_6_MON, DOCUMENT_6);
    }

    @Test
    public void assertFromStringOnDocument7() {
        assertFromString(DOCUMENT_7_MON, DOCUMENT_7);
    }

    @Test
    public void assertFromStringOnDocument8() {
        assertFromString(DOCUMENT_8_MON, DOCUMENT_8);
    }

    @Test
    public void assertConsistencyOnDocument1() {
        assertConsistency(DOCUMENT_1);
    }

    @Test
    public void assertConsistencyOnDocument2() {
        assertConsistency(DOCUMENT_2);
    }

    @Test
    public void assertConsistencyOnDocument3() {
        assertConsistency(DOCUMENT_3);
    }

    @Test
    public void assertConsistencyOnDocument4() {
        assertConsistency(DOCUMENT_4);
    }

    @Test
    public void assertConsistencyOnDocument5() {
        assertConsistency(DOCUMENT_5);
    }

    @Test
    public void assertConsistencyOnDocument6() {
        assertConsistency(DOCUMENT_6);
    }

    @Test
    public void assertConsistencyOnDocument7() {
        assertConsistency(DOCUMENT_7);
    }

    @Test
    public void assertConsistencyOnDocument8() {
        assertConsistency(DOCUMENT_8);
    }
}