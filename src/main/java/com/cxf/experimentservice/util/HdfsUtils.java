package com.cxf.experimentservice.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 操作hdfs文件的工具类
 */

class HdfsUtils {

    /**
     * 读取hdfs指定路径的文件内容
     */
    public static String gethdfsDataFile(String hdfsPath){
        String result = "";
        if(hdfsPath!=null){
            Path path = new Path(hdfsPath);
            Configuration configuration = new Configuration();
            FSDataInputStream fsDataInputStream = null;
            FileSystem fileSystem = null;
            BufferedReader br = null;
            // 定义一个字符串用来存储文件内容
            try {
                fileSystem = path.getFileSystem(configuration);
                fsDataInputStream = fileSystem.open(path);
                br = new BufferedReader(new InputStreamReader(fsDataInputStream));
                String str2;
                while ((str2 = br.readLine()) != null) {
                    // 遍历抓取到的每一行并将其存储到result里面
                    result += str2 + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(br!=null){
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(fsDataInputStream!=null){
                    try {
                        fsDataInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(fileSystem!=null){
                    try {
                        fileSystem.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("文件内容:" + result);
        }
        return result;
    }

    public static void main(String[] args) {

        //gethdfsData("hdfs://nameservice1/data_yqt/测试图片1.bmp");
        gethdfsDataFile("hdfs://nameservice1/data_cxf/测试文件.txt");
    }

}