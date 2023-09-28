package com.wind.common.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName : OssUtil
 * @Description :
 * @Author : CJH
 * @Date: 2021-01-07 14:59
 */
public class AliOss {
    private static final String endpoint = "https://oss-cn-qingdao.aliyuncs.com";
    private static final String accessKeyId = "LTAI5tGCW6UMyZzkPcKweQBM";
    private static final String accessKeySecret = "c7AQb3pqQvQR5ikTf0kyknwvl1HDu0";
    private static final String bucketName = "doc-save";
    private static final String root = "doc-save/";

    /**
     * 上传文件，以IO流方式
     *
     * @param inputStream 输入流
     * @param key         唯一key（在oss中的文件名字）
     */
    public static void upload(InputStream inputStream, String key) {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（key）。
            // String content = "Hello OSS";
            // ossClient.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));
            ossClient.putObject(bucketName, key, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 流式上传
     *
     * @param inputStream
     * @param origin
     * @return
     */
    public static String uploadByStream(InputStream inputStream, String origin) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String suffix = origin.split("\\.")[1];
        String key = root + UUID.randomUUID() + "." + suffix;
        ossClient.putObject(bucketName, key, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return getPath(key);
    }

    /**
     * 获取文件地址
     *
     * @param key
     * @return
     */
    public static String getPath(String key) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 设置URL过期时间为12个月
        Date expiration = new Date(new Date().getTime() + 3600 * 1000 * 24 * 30 * 12);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);
        generatePresignedUrlRequest.setExpiration(expiration);
        URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    /**
     * 创建存储桶
     */
    public void createBucket() {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 创建存储空间。
            ossClient.createBucket(bucketName);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传本地文件至OSS
     *
     * @param filePath 文件路径
     * @param key      唯一key（在oss中的文件名字）
     */
    public void upload(String filePath, String key) {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（key）。
            // String content = "Hello OSS";
            // ossClient.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));
            ossClient.putObject(bucketName, key, new FileInputStream(new File(filePath)));
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传本地文件至OSS
     *
     * @param file 文件
     * @param key  唯一key（在oss中的文件名字）
     */
    public void upload(File file, String key) {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（key）。
            // String content = "Hello OSS";
            // ossClient.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));
            ossClient.putObject(bucketName, key, new FileInputStream(file));
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传网络文件至OSS
     *
     * @param url 文件url，如：http://www.baidu.com/xxx.jpg
     * @param key 唯一key（在oss中的文件名字）
     */
    public void uploadNetwork(String url, String key) {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传网络流。
            InputStream inputStream = new URL(url).openStream();
            ossClient.putObject(bucketName, key, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除OSS中的文件
     *
     * @param key 唯一key（在oss中的文件名字）
     */
    public void delete(String key) {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 删除文件。
            ossClient.deleteObject(bucketName, key);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

