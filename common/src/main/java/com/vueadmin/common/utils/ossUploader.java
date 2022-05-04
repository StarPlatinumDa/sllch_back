package com.vueadmin.common.utils;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ossUploader {
    public String ossImageLoader(String filePath){
        String endpoint = "https://oss-cn-chengdu.aliyuncs.com";
        String accessKeyId = "LTAI5tPyw8BmLWwNYbZFso9t";
        String accessKeySecret = "MepFLoDc6m4ylXpjFgaSeiA0EwkQT8";
        String bucketName = "rcdl";
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMddHHmmss");
        String objectName = simpleDateFormat.format(nowDate);
        String path = filePath;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(path));
        // 上传文件。
        ossClient.putObject(putObjectRequest);
        String result = "rcdl.oss-cn-chengdu.aliyuncs.com/"+objectName;
        return result;
    }
}
