package com.github.moncache.driver.format;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DBObject;

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
    return FormatEncoder.encode(document).toString();
  }

  /**
   * Converts document MON (MonCache Object Notation) string to MongoDB document.
   * @param string document MON (MonCache Object Notation) string
   * @return MongoDB document
   */
  public static DBObject fromString(String string) {
    try {
      return (DBObject) FormatDecoder.decode((ObjectNode) new ObjectMapper().readTree(string));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}