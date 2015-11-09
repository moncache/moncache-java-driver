package com.github.moncache.driver.format;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DBObject;

import java.util.Optional;

/**
 * Converts {Document} <-> {String}
 *
 */
public class Formatter {
  /**
   * Converts MongoDB document to document string representation.
   * @param document MongoDB document
   * @return document MON (MonCache Object Notation) as string
   */
  public static String toString(DBObject document) {
    return encode(document).get().toString();
  }

  /**
   * Converts document MON (MonCache Object Notation) string to MongoDB document.
   * @param string document MON (MonCache Object Notation) string
   * @return MongoDB document
   */
  public static DBObject fromString(String string) {
    try {
      return (DBObject) decode(new ObjectMapper().readTree(string)).get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static Optional<JsonNode> encode(Object data) {
    return new MonCacheEncoding().encodeDynamic(data);
  }

  public static Optional<Object> decode(JsonNode presentation) {
    return new MonCacheDecoding().decodeDynamic(presentation);
  }

}