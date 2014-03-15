package org.assertj.swing.aut.main;

import static org.assertj.swing.aut.util.SystemPropertiesUtil.fetchProperties;

import java.lang.reflect.InvocationTargetException;

/**
 * Given the program arguments being system property keys, this class retrieves the values to the keys and stores key
 * value pairs in an array you can read via {@link #getProperties()}.
 */
public class SystemPropertiesStore {

  private static String[][] properties;

  public static void main(String[] args) throws InterruptedException, InvocationTargetException {
    properties = fetchProperties(args);
  }

  public static String[][] getProperties() {
    return properties;
  }

  public static void setProperties(String[][] properties) {
    SystemPropertiesStore.properties = properties;
  }
}
