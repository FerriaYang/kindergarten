package com.util;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FastDFSUtil {

    public static String uploadFile(MultipartFile file){
        String fileName = file.getOriginalFilename();//得到文件名
        if (fileName != ""){
            byte[] content = null;
            try {
                content = file.getBytes();//获取文件内容
            } catch (IOException e) {
                e.printStackTrace();
            }
            //获取文件扩展名
            String ext = null;
            if (fileName != null && fileName != ""){
                int index = fileName.lastIndexOf(".") + 1;
                ext = fileName.substring(index);
            }

            String path = "http://192.168.1.16:90/";
            //连接tracker注册中心，通过注册文件
            String confName = "fdfs_client.conf";
            try {
                //加载配置文件
                ClientGlobal.init(confName);
                //创建tracker客户端和服务端对象
                TrackerClient trackerClient = new TrackerClient();
                TrackerServer trackerServer = trackerClient.getConnection();
                //创建storage客户端和服务端对象
                StorageClient storageClient = new StorageClient(trackerServer,null);
                //storage客户端调用上传文件的方法
                String[] jpgs = storageClient.upload_file(content,ext,null);
                System.out.println(jpgs.length);
                System.out.println("组名：" + jpgs[0]);
                System.out.println("路径：" + jpgs[1]);
                path += jpgs[0] + "/" + jpgs[1];
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return path;
        }else {
            return null;
        }
    }

    public static void deleteFile(String path){
        //连接tracker注册中心，通过注册文件
        String confName = "fdfs_client.conf";
        String filePath = path.substring(path.lastIndexOf("group1") + 7);
        try {
            //加载配置文件
            ClientGlobal.init(confName);
            //创建tracker客户端和服务端对象
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //创建storage客户端和服务端对象
            StorageClient storageClient = new StorageClient(trackerServer,null);
            int result = storageClient.delete_file("group1", filePath);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
