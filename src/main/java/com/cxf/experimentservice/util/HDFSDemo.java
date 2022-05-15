package com.cxf.experimentservice.util;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HDFSDemo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        // TODO Auto-generated method stub
        FileSystem fs = FileSystem.get(new URI("hdfs://nameservice1"), new Configuration());
        InputStream in = fs.open(new Path("/data_yqt/测试图片2.png"));
        OutputStream out = new FileOutputStream("E://ProjectCode/com_cxf_prod/experiment-service/src/main/data/图片.png");
        IOUtils.copyBytes(in, out, 4096, true);
    }
}
