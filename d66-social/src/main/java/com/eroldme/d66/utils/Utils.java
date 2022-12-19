package com.eroldme.d66.utils;

import java.util.UUID;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 14:42
 */
public class Utils {
  public static String stringToken() {
    return UUID.randomUUID().toString();
  }
}
