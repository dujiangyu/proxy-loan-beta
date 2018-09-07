package com.cw.web.common.component;

import com.cw.biz.CwException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传组件
 * Created by Administrator on 2017/6/2.
 */
@Service
public class UploadFileComponent {

    /**
     * 文件上传到fastdfs
     * @param file
     * @return
     */
    public String upload(MultipartFile file)
    {
        try {
//            FileManager client = new FileManager();
            String fileName = file.getOriginalFilename();
//            String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
//            FastDFSFile fastDFSFile = new FastDFSFile(fileName, file.getBytes(), prefix);
//            return client.upload(fastDFSFile);
            String port="9393";
            String filePort="9394";
            String domain="http://120.79.255.186:";
            if (!file.isEmpty()) {
                if(file.getContentType().contains("image")){
                    domain = domain+port+"/";
                }else{
                    domain = domain+filePort+"/";
                }
                try {
                    // 获取图片的扩展名
                    String extensionName = StringUtils.substringAfter(fileName, ".");
                    // 新的图片文件名 = 获取时间戳+"."图片扩展名
                    //String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
                    // 文件路径
                    //String filePath = "/Users/mac/Desktop";//"/data/image";
                    String filePath = "/data/image";


                    File dest = new File(filePath, fileName);
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }

                    // 上传到指定目录
                    file.transferTo(dest);
                  return domain+fileName;
                } catch (IOException e) {
                    CwException.throwIt("文件上传失败");
                }
            }
        }catch (Exception e)
        {
            CwException.throwIt("文件上传失败");
            return null;
        }
        return "";
    }
}
