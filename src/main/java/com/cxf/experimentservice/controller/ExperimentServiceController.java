package com.cxf.experimentservice.controller;


import com.cxf.experimentservice.util.TifToPngUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.awt.image.BufferedImage;
import java.io.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.IOUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ExperimentServiceController {
    @Autowired(required = false)
    private HttpServletResponse response;

    @RequestMapping("/picture")
    @GetMapping("/getHeadImage")
    //public void getPicture(@RequestParam("name") String name) throws IOException, URISyntaxException {
    public void getPicture(@RequestParam("project_name") String project_name,@RequestParam("group_name") String group_name,@RequestParam("picture_name") String picture_name) throws IOException, URISyntaxException {

        //1.下载图片到本地
        //1-1定义hdfs集群
        FileSystem fs = FileSystem.get(new URI("hdfs://nameservice1"), new Configuration());
        //1-2指定需下载的hdfs文件路径
        String hdfs_picture_path="/data_yqt/"+project_name+"/"+group_name+"/"+picture_name;
        //InputStream in = fs.open(new Path("/data_yqt/测试图片2.png"));
        InputStream in = fs.open(new Path(hdfs_picture_path));
        int length = picture_name.length();
        String postfix=picture_name.substring(length-3,length);
        System.out.println(postfix);
        String local_picture_path="E://ProjectCode/com_cxf_prod/experiment-service/src/main/data/图片."+postfix;
        System.out.println("文件写出路径："+local_picture_path);
        //OutputStream out = new FileOutputStream("E://ProjectCode/com_cxf_prod/experiment-service/src/main/data/图片.png");
        OutputStream out = new FileOutputStream(local_picture_path);
        IOUtils.copyBytes(in, out, 4096, true);

        //2.将本地图片返回
        //2-1如果是tif格式数据需转化为png格式
        if (postfix.equals("tif")){
            TifToPngUtils tifToPngUtils = new TifToPngUtils();
            tifToPngUtils.TifTopng(local_picture_path,local_picture_path.replace(".tif",".png"));
            local_picture_path=local_picture_path.replace(".tif",".png");
            System.out.println("图片格式转换成功！");
        }

        OutputStream os = null;
        BufferedImage image = null;
        try {
            //返回图片路径
            System.out.println("文件读取路径："+local_picture_path);
            //image = ImageIO.read(new FileInputStream(new File("E://ProjectCode/com_cxf_prod/experiment-service/src/main/data/图片.png")));
            image = ImageIO.read(new FileInputStream(new File(local_picture_path)));
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }

    }
}
