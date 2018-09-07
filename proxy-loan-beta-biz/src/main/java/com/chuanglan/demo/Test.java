package com.chuanglan.demo;

import com.zds.common.lang.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Away on 2018/9/1.
 */
public class Test {

    public static void main(String[] args){
        LinkedHashMap<String,String> ttt=new LinkedHashMap<>();
        ttt.put("1","a");
        ttt.put("2","b");
        ttt.put("3","c");
        ttt.put("4","d");
        ttt.put("5","e");
        ttt.put("6","f");

        for(Map.Entry<String, String> entry:ttt.entrySet()){
            System.out.println(entry.getKey()+"="+entry.getValue());
        }

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(DateUtil.simple);
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MINUTE,5);
        System.out.println(simpleDateFormat.format(calendar.getTime()));
    }
}
