package com.mirai.water.sweetbot.util;

import net.mamoe.mirai.utils.ExternalResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * UrlStreamUtil class
 *
 * @author Mechellwater
 * @date 2021/02/05
 */
@Component
public class UrlStreamUtil {
    /**
     *
     * 读取网络文件.
     *
     * @return
     */
    private byte[] getFileInputStream(String str) throws MalformedURLException {
        try {
            URL httpUrl = new URL(str);
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36 Edg/96.0.1054.62");
            InputStream inStream = conn.getInputStream();//通过输入流获取数据
            byte[] bytes = readInputStream(inStream);//得到二进制数据
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getVoiceByte(String voiceUrl) throws IOException {
        try {
            URL httpUrl = new URL(voiceUrl);
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36 Edg/96.0.1054.62");
            InputStream inStream = conn.getInputStream();//通过输入流获取数据
            byte[] bytes = readCutVoiceInputStream(inStream);//得到二进制数据
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] readInputStream(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    private byte[] readCutVoiceInputStream(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     *
     * 通过URL创建Mirai ExternalResource 图片文件
     *
     */
    public ExternalResource createImgExternalResource(String imgUrl) throws IOException {
        return ExternalResource.create(getFileInputStream(imgUrl));
    }
    /**
     *
     * 通过URL创建Mirai ExternalResource 语音文件
     *
     */
    public ExternalResource createVoiceExternalResource(String voiceUrl) throws IOException {
        return ExternalResource.create(getVoiceByte(voiceUrl));
    }

    public String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}