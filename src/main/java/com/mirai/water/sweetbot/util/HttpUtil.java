package com.mirai.water.sweetbot.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用于模拟HTTP请求中GET/POST方式
 * @author by MechellWater
 * @date : 2022-05-17 22:12
 */

public class HttpUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final int CONNECT_TIME_OUT = 10000;
    private static final int READ_TIME_OUT = 15000;
    //请求方式
    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";
    //编码格式，UTF-8
    private static final String CHARSET_UTF8 = "UTF-8";


    /**
     * 发送GET请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return
     */
    public synchronized static String sendGet(String url, Map<String, String> parameters) {
        return sendGet(url, parameters, true);
    }

    /**
     * 发送GET请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @param isEncoder  是否编码-默认true
     * @return 远程响应结果
     */
    public synchronized static String sendGet(String url, Map<String, String> parameters, boolean isEncoder) {
        String result = "";
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    String value = isEncoder ? java.net.URLEncoder.encode(parameters.get(name), "UTF-8") : parameters.get(name);
                    sb.append(name).append("=").append(value);
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    String value = isEncoder ? java.net.URLEncoder.encode(parameters.get(name), "UTF-8") : parameters.get(name);
                    sb.append(name).append("=").append(value).append("&");
                    //java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            String full_url = url + "?" + params;
            //System.out.println("本次访问链接："+full_url);
            logger.info("本次访问链接：" + (isEncoder? java.net.URLDecoder.decode(full_url, "UTF-8") : full_url));

            // 创建URL对象
            java.net.URL connURL = new java.net.URL(full_url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
//            httpConn.setRequestProperty("Charsert", "UTF-8"); //设置请求编码
//            httpConn.setRequestProperty("Accept-Charset", "utf-8");
//            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8") ;//url编码

            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取
            Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
//                System.out.println(key + "\t：\t" + headers.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, Map<String, String> parameters) {
        return sendPost(url, parameters, 90);
    }

    /**
     * 服务数据抽取
     * @param url
     * @param parameters
     * @return
     */
    public static String sendFwzxPost(String url, Map<String, String> parameters) {
        return sendPost(url, parameters, 3 * 60);
    }

    /**
     * 发送POST请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost(String url, Map<String, String> parameters, int timeout) {
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    if (parameters.get(name) != null) {
                        sb.append(name).append("=").append(
                                java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
                    }
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setConnectTimeout(timeout * 1000);
            httpConn.setReadTimeout(timeout * 1000);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }



    public static JSONObject sendPost(String url, JSONObject jsonObject){
        JSONObject result = new JSONObject();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        StringEntity stringEntity = new StringEntity(jsonObject.toJSONString(),"UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        try {
            HttpResponse httpResponse = client.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                result = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity(),"UTF-8") );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 检查端口是否可用
     * 经过测试，好像只要端口被占用了就判定链接成功
     * ssr不开局域网端口，或者开了以后没开代理，端口也是通的
     *
     * @param address 地址
     * @param prot    端口
     * @return 是否可用
     */
    public static boolean checkProt(String address, int prot) {
        if (StringUtil.isEmpty(address)) {
            return false;
        }
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(address, prot));
            socket.close();
            return true;
        } catch (IOException ioEx) {
            //没那么重要，直接打在控制台里
            ioEx.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException ioEx) {
            //没那么重要，直接打在控制台里
            ioEx.printStackTrace();
        }

        return false;
    }

    /**
     * 获取代理信息
     */
    public static Proxy getProxy() {
        // 创建代理 todo 地址和端口写为配置
        Proxy proxy = null;
        if (HttpUtil.checkProt("127.0.0.1", 8889)) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8889));
        }
        return proxy;
    }


    /**
     * get请求
     *
     * @param connUrl 完整的请求链接
     * @return 接口返回报文
     * @throws IOException 请求异常
     */
    public static String get(String connUrl) throws IOException {
        return get(connUrl, null);
    }

    public static String get(String connUrl, Proxy proxy) throws IOException {
        return get(connUrl, null, proxy);
    }

    public static String get(String connUrl, Map<String, String> header, Proxy proxy) throws IOException {
        HttpURLConnection httpURLConnection = getHttpURLConnection(connUrl, REQUEST_METHOD_GET, proxy);
        //模拟chrome
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        if (null != header) {
            //加入请求头
            for (String key : header.keySet()) {
                //忽略空的参数
                if (StringUtil.isEmpty(key) || StringUtil.isEmpty(header.get(key))) {
                    continue;
                }
                httpURLConnection.setRequestProperty(key, header.get(key));
            }
        }
        //开始链接
        httpURLConnection.connect();

        //获取错误流
//        httpURLConnection.getErrorStream();
        //获取响应流
        InputStream rspInputStream = httpURLConnection.getInputStream();
        String rspStr = parseInputStreamStr(rspInputStream);

        //关闭流
        rspInputStream.close();
        //断开连接
        httpURLConnection.disconnect();
        return rspStr;
    }

    //转化流
    private static String parseInputStreamStr(InputStream inputStream) throws IOException {
        //编码这里暂时固定utf-8，以后根据返回的编码来
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        String tempStr = null;
        //逐行读取
        while ((tempStr = reader.readLine()) != null) {
            stringBuilder.append(tempStr);
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }

    public static HttpURLConnection getHttpURLConnection(String connUrl, String method, Proxy proxy) throws IOException {
        //使用url对象打开一个链接
        HttpURLConnection httpURLConnection = null;
        if (null != proxy) {
            httpURLConnection = (HttpURLConnection) new URL(connUrl).openConnection(proxy);
        } else {
            httpURLConnection = (HttpURLConnection) new URL(connUrl).openConnection();
        }
        httpURLConnection.setRequestMethod(method);
        //设置链接超时时间
        httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
        //设置返回超时时间
        httpURLConnection.setReadTimeout(READ_TIME_OUT);

        return httpURLConnection;
    }

    /**
     * 转化为urlencode
     * 采用UTF-8格式
     *
     * @param params 转化的参数
     * @return 转化后的urlencode
     * @throws IOException 转化异常
     */
    public static String parseUrlEncode(Map<String, Object> params) throws IOException {
        return parseUrlEncode(params, CHARSET_UTF8);
    }

    /**
     * 转化为urlencode
     *
     * @param params  转化的参数
     * @param charset 编码格式
     * @return 转化后的urlencode
     * @throws IOException 转化异常
     */
    public static String parseUrlEncode(Map<String, Object> params, String charset) throws IOException {
        //非空判断
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder urlEncode = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = params.entrySet();

        for (Map.Entry<String, Object> entry : entries) {
            String name = entry.getKey();
            String value = null;
            if (null != entry.getValue()) {
                value = entry.getValue().toString();
            }
            // 忽略参数名或参数值为空的参数
            if (StringUtil.isEmpty(name) || StringUtil.isEmpty(value)) {
                continue;
            }

            //多个参数之间使用&连接
            urlEncode.append("&");
            //转化格式，并拼接参数和值
            urlEncode.append(name).append("=").append(URLEncoder.encode(value, charset));
        }
        if (urlEncode.length() <= 0) {
            return "";
        }
        return "?" + urlEncode.substring(1);
    }

    /**
     * get请求方法列表
     */
    public static byte[] doGet(String uri) throws IOException {
        return doGet(uri, null);
    }

    public static byte[] doGet(String uri, Proxy proxy) throws IOException {
        return doGet(uri, null, proxy);
    }

    public static byte[] doGet(String uri, Map<String, String> header, Proxy proxy) throws IOException {
        HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "GET", header, proxy);
        return getBytesFromStream(httpsConn.getInputStream());
    }


    /**
     * post请求方法列表
     */
    public static byte[] doPost(String uri, String data) throws IOException {
        return doPost(uri,data,null);
    }

    /**
     * post请求方法列表
     */
    public static byte[] doPost(String uri, String data, Proxy proxy) throws IOException {
        HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "POST", proxy);
        setBytesToStream(httpsConn.getOutputStream(), data.getBytes());
        return getBytesFromStream(httpsConn.getInputStream());
    }


    /**
     * 获取Https链接
     */
    public static HttpsURLConnection getHttpsURLConnection(String uri, String method) throws IOException {
        return getHttpsURLConnection(uri, method, null);
    }

    public static HttpsURLConnection getHttpsURLConnection(String uri, String method, Proxy proxy) throws IOException {
        return getHttpsURLConnection(uri, method, null, proxy);
    }

    /**
     * @param uri    请求链接
     * @param method 请求方式，比如get post
     * @param header 请求头，有时候需要
     * @param proxy  代理
     * @return http链接对象
     * @throws IOException 链接异常
     */
    public static HttpsURLConnection getHttpsURLConnection(String uri, String method, Map<String, String> header, Proxy proxy) throws IOException {
        URL url = new URL(uri);

        HttpsURLConnection httpsConn = null;
        if (null != proxy) {
            httpsConn = (HttpsURLConnection) url.openConnection(proxy);
        } else {
            httpsConn = (HttpsURLConnection) url.openConnection();
        }
        SSLSocketFactory ssf = getSSLSocketFactory();
        httpsConn.setSSLSocketFactory(ssf);

        //加入请求头
        setHeaderParam(httpsConn, header);

        /*
        在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
        则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。
        策略可以是基于证书的或依赖于其他验证方案。
        当验证 URL 主机名使用的默认规则失败时使用这些回调。
         */
        httpsConn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        httpsConn.setRequestMethod(method);
        httpsConn.setDoInput(true);
        httpsConn.setDoOutput(true);
        //链接超时时间
        httpsConn.setConnectTimeout(CONNECT_TIME_OUT);
        //读取资源超时时间
        httpsConn.setReadTimeout(READ_TIME_OUT);
        return httpsConn;
    }

    /**
     * 获取请求结果
     */
    private static byte[] getBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] kb = new byte[1024];
        int len;
        while ((len = is.read(kb)) != -1) {
            baos.write(kb, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        baos.close();
        is.close();
        return bytes;
    }

    /**
     * 添加post请求参数
     */
    private static void setBytesToStream(OutputStream os, byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        byte[] kb = new byte[1024];
        int len;
        while ((len = bais.read(kb)) != -1) {
            os.write(kb, 0, len);
        }
        os.flush();
        os.close();
        bais.close();
    }
    private static final class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    //获取SSLSocketFactory
    //SSLSocket通信 应该是种协议吧
    public static SSLSocketFactory getSSLSocketFactory() {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLSocketFactory ssf = ctx.getSocketFactory();
        return ssf;
    }

    //添加请求头
    public static void setHeaderParam(HttpsURLConnection httpsConn, Map<String, String> header) {
        if (null == header) {
            header = new HashMap<>();
        }
        //媒体格式
        //region 媒体格式参数范例
        //例如： Content-Type: text/html;charset:utf-8;
        //
        // 常见的媒体格式类型如下：
        //    text/html ： HTML格式
        //    text/plain ：纯文本格式
        //    text/xml ：  XML格式
        //    image/gif ：gif图片格式
        //    image/jpeg ：jpg图片格式
        //    image/png：png图片格式

        //   以application开头的媒体格式类型：
        //   application/xhtml+xml ：XHTML格式
        //   application/xml     ： XML数据格式
        //   application/atom+xml  ：Atom XML聚合格式
        //   application/json    ： JSON数据格式
        //   application/pdf       ：pdf格式
        //   application/msword  ： Word文档格式
        //   application/octet-stream ： 二进制流数据（如常见的文件下载）
        //   application/x-www-form-urlencoded ： <form encType=””>中默认的encType，form表单数据被编码为key/value格式发送到服务器（表单默认的提交数据的格式）
        //   另外一种常见的媒体格式是上传文件之时使用的：
        //    multipart/form-data ： 需要在表单中进行文件上传时，就需要使用该格式
        //endregion
        header.put("Content-Type", "application/json;charset=utf-8");
        //模拟谷歌浏览器
        //region User-Agent简介与构成
        //这段字符串是在chrome的开发者工具里提取的，网页请求里可以见到，所以没必要非得自己一点点拼
        //各部分介绍：
        //https://www.jianshu.com/p/c5cf6a1967d1
        //User-Agent会告诉网站服务器，访问者是通过什么工具来请求的，如果是爬虫请求，一般会拒绝，如果是用户浏览器，就会应答
        //第一部分：Mozilla/5.0 由于历史上的浏览器大战，当时想获得图文并茂的网页，就必须宣称自己是 Mozilla 浏览器。此事导致如今User-Agent里通常都带有Mozilla字样，出于对历史的尊重，大家都会默认填写该部分。
        //第二部分：操作平台
        //第三部分：引擎版本 AppleWebKit/537.36 (KHTML, like Gecko)...Safari/537.36，历史上，苹果依靠了WebKit内核开发出Safari浏览器，WebKit包含了WebCore引擎，而WebCore又从KHTML衍生而来。由于历史原因，KHTML引擎需要声明自己是“类似Gecko”的，因此引擎部分这么写。再后来，Google开发Chrome也是用了WebKit内核，于是也跟着这么写。借用Littern的一句话：“Chrome 希望能得到为Safari编写的网页，于是决定装成Safari，Safari使用了WebKit渲染引擎，而WebKit呢又伪装自己是KHTML，KHTML呢又是伪装成Gecko的。同时所有的浏览器又都宣称自己是Mozilla。”。不过，后来Chrome 28某个版本改用了blink内核，但还是保留了这些字符串。而且，最近的几十个版本中，这部分已经固定，没再变过
        //第四部分：浏览器版本 这里是Chrome
        //endregion
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");

        //加入请求头
        for (String key : header.keySet()) {
            //忽略空的参数
            if (StringUtil.isEmpty(key) || StringUtil.isEmpty(header.get(key))) {
                continue;
            }
            httpsConn.setRequestProperty(key, header.get(key));
        }

    }
}
