package com.cw.core.common.util;/*
 * 描       述:  &lt;描述&gt;
 * 修  改   人:  ${user}
 * 修  改 时 间:  ${date}
 * &lt;修改描述:&gt;
 */

import java.util.HashMap;

public class CodeConvert {

    public static HashMap<String,String> cooperationModel=new HashMap<String,String>();
    static {
        cooperationModel.put("zc","正常模式");
        cooperationModel.put("fd","返点模式");
        cooperationModel.put("jt","阶梯模式");
        cooperationModel.put("jtfd","阶梯返点模式");

    }

    public static String getCooperationModel(String key){
        return cooperationModel.get(key);
    }
}
