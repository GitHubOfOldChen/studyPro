package com.hzbank.ebank.corporBank.mbApplication.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

/**
 * 图片、视频   等文件的编码、解码
 * @author yanjiang.chen
 *
 */
public class EncodeUtil {

	/**
	 * InputStream转化为base64
	 * @param in
	 * @return
	 */
    public static String getBase64FromInputStream(InputStream in) {
        // 将图片(视频)等文件转化为字节数组字符串，并对其进行Base64编码处理
    	//inputstream -- outputstream -- byte[] -- byte[](Base64) -- string
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String str = new String(Base64.encodeBase64(data));
        System.out.println( "str length: " + str.length() + "  str: " + str);
        return str;
    }

    /**
     * Base64解码并生成图片(视频)等
     * @param base64str
     * @param savepath 新图片存储路径
     * @return
     */
	public static boolean GenerateImage(String base64str,String savepath) { 
		//Base64 -- byte[] -- OutputStream
        if (base64str == null) //图像数据为空
            return false;
		BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64str);
            // System.out.println("解码完成");
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据（这一步很重要）
                   b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(savepath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

        public static void main(String[] args) {
            try {
                File file = new File("C:\\Users\\yanjiang.chen\\Desktop\\01.mp4");
                FileInputStream fileInputStream = new FileInputStream(file);
                //InputStream转化为base64
                String base64FromInputStream = getBase64FromInputStream(fileInputStream);
               
                //Base64解码并生成图片
                GenerateImage(base64FromInputStream,"C:\\\\Users\\\\yanjiang.chen\\\\Desktop\\\\02.mp4");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

}