package org.assertj.swing.aut.util;

public class SystemPropertiesUtil {

  public static String[][] fetchProperties(String[] args) {
    String[][] properties = new String[args.length][];
    for (int i = 0; i < args.length; ++i) {
      properties[i] = new String[] { args[i], System.getProperty(args[i]) };
    }
    return properties;
  }
}
