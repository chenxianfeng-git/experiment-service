package com.cxf.experimentservice.util;

import java.io.*;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.PNGEncodeParam;

public class TifToPngUtils {
    public String TifTopng(String tiffFilePath, String pngFilePath) throws IOException {
        RenderedOp ro = JAI.create("fileload", tiffFilePath);
        OutputStream os = new FileOutputStream(pngFilePath);
        PNGEncodeParam param = new PNGEncodeParam.RGB();

        ImageEncoder ie = ImageCodec.createImageEncoder("PNG", os, param);
        ie.encode(ro);
        os.flush();
        os.close();
        return pngFilePath;
    }

    public static void main(String[] args) throws IOException {
        TifToPngUtils tifToPngUtils = new TifToPngUtils();
        tifToPngUtils.TifTopng("E://ProjectCode/com_cxf_prod/experiment-service/src/main/data/图片.tif","E://ProjectCode/com_cxf_prod/experiment-service/src/main/data/图片.png");
    }
}
