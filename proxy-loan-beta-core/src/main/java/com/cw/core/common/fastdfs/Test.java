package com.cw.core.common.fastdfs;

import java.io.*;

/**
 * Created by Administrator on 2017/6/3.
 */
public class Test {
    public static void main(String[] args) throws Exception {

        //不要带classpath
        FileManager client = new FileManager();
        FastDFSFile fastDFSFile = new FastDFSFile("12.png",getBytes("D:\\var\\12.png"),"png");
        String result = client.upload(fastDFSFile);
        System.out.println(result);
    }

    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
