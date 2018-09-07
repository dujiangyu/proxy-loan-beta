package com.cw.web.common.util;

/**
 * Created by dujy on 2017-05-29.
 */
public class Apps {

     public static String getAppName() {
    String name = System.getProperty("cw.appName");
    if (name == null) {
      throw new RuntimeException("没有设置应用名称,请设置系统变量cw.appName");
    }
    return name;
  }

 public static String getAppSessionCookieName() {
    return getAppName() + "-session";
  }
}
