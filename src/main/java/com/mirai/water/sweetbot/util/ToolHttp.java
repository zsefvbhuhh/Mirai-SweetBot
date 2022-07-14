package com.mirai.water.sweetbot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
/*import com.alibaba.fastjson.util.IOUtils;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;*/
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
/*import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;*/
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * HTTP请求工具类
 * @author by MechellWater
 * @date : 2022-06-17 11:46
 */
public abstract class ToolHttp {

    private static Logger log = LoggerFactory.getLogger(ToolHttp.class);
    /**
     * 默认请求编码
     */
    private static final String CHARSET_UTF_8 = "UTF-8";
    /**
     * 连接超时
     */
    private static final int CONNECT_TIMEOUT = 3000;
    /**
     * 请求超时
     */
    private static final int SOCKET_TIMEOUT = 30000;

    /**
     * HttpClient get 请求
     *
     * @param isHttps 是否ssl链接
     * @param url     请求地址
     * @return
     */
    public static String get(boolean isHttps, String url) {
        CloseableHttpClient httpClient = null;
        try {
            if (!isHttps) {
                httpClient = HttpClients.createDefault();
            } else {
                httpClient = createSSLInsecureClient();
            }
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpget);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String out = EntityUtils.toString(entity, CHARSET_UTF_8);
                    return out;
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error("httpClient.close()异常");
            }
        }
        return null;
    }

    /**
     * HttpClient post请求
     *
     * @param isHttps     是否ssl链接
     * @param url         请求地址
     * @param data        报文内容
     * @param contentType 报文类型
     * @param headers     请求头
     * @param isRaw       字节传输
     * @return
     */
    public static String post(boolean isHttps, String url, String data, String contentType, Map<String, String> headers, boolean isRaw) {
        return post(isHttps, url, data, contentType, headers, isRaw, SOCKET_TIMEOUT, CONNECT_TIMEOUT);
    }

    /**
     * form表单请求
     *
     * @param url            请求地址
     * @param params         请求参数
     * @param headers        请求头
     * @param socketTimeout  请求超时
     * @param connectTimeout 连接超时
     * @return
     */
    public static String postForFrom(String url, Map<String, Object> params, Map<String, String> headers, int socketTimeout, int connectTimeout) {
        StringBuffer data = new StringBuffer();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            Object value = params.get(key);
            if (data.length() > 0) {
                data.append("&");
            }
            try {
                if (value instanceof String) {
                    data.append(key).append("=").append(URLEncoder.encode(((String) value), "UTF-8"));
                } else {
                    data.append(key).append("=").append(URLEncoder.encode(JSON.toJSONString(value, SerializerFeature.WriteMapNullValue), CHARSET_UTF_8));
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return post(false, url, data.toString(), ContentType.APPLICATION_FORM_URLENCODED.getMimeType(), headers, false, socketTimeout, connectTimeout);
    }

    /**
     * form表单请求
     *
     * @param url           请求地址
     * @param params        请求参数
     * @param headers       请求头
     * @param socketTimeout 请求超时时间
     * @return
     */
    public static String postForFrom(String url, Map<String, Object> params, Map<String, String> headers, int socketTimeout) {
        return postForFrom(url, params, headers, socketTimeout, CONNECT_TIMEOUT);
    }

    /**
     * form表单请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @return
     */
    public static String postForFrom(String url, Map<String, Object> params, Map<String, String> headers) {
        return postForFrom(url, params, headers, SOCKET_TIMEOUT, CONNECT_TIMEOUT);
    }

/*
    */
/**
     * Multipart请求
     * @param url
     * @param params
     * @return
     *//*

    public static String postForMultipart(String url, Map<String, String> params){
        String result = null;
        try {
            result = postForMultipartThrowException(url,params);
        } catch (Exception e) {
            log.error("http Multipart请求异常[{},{}]",url,e);
            e.printStackTrace();
        }
        return result;
    }
*/

/*
    public static String postForMultipartThrowException(String url, Map<String, String> params) throws Exception{
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                .create();
        //设置参数
        for(String key : params.keySet()){
            multipartEntityBuilder.addPart(key,new StringBody(params.get(key), ContentType.APPLICATION_JSON));
        }
        HttpEntity reqEntity = multipartEntityBuilder.build();
        httppost.setEntity(reqEntity);
        CloseableHttpResponse response = httpclient.execute(httppost);
        try {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = IOUtils.toString(resEntity.getContent(),"utf-8");
            }
            EntityUtils.consume(resEntity);
        } finally {
            response.close();
        }
        return result;
    }
*/
    /**
     * 进行HttpClient post连接
     *
     * @param isHttps        是否ssl链接
     * @param url            请求地址
     * @param data           内容
     * @param contentType    内容类型
     * @param headers        请求头
     * @param isRaw          是否字节流
     * @param socketTimeout  请求超时时间
     * @param connectTimeout 连接超时时间
     * @return
     */
    public static String post(boolean isHttps, String url, String data, String contentType, Map<String, String> headers, boolean isRaw, int socketTimeout, int connectTimeout) {
        CloseableHttpClient httpClient = null;
        try {
            if (!isHttps) {
                httpClient = HttpClients.createDefault();
            } else {
                httpClient = createSSLInsecureClient();
            }
            HttpPost httpPost = new HttpPost(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpPost.addHeader(key, headers.get(key));
                }
            }
            if (null != data) {
                if (isRaw) {
                    ByteArrayEntity byteEntity = new ByteArrayEntity(data.getBytes(CHARSET_UTF_8), ContentType.create(contentType, CHARSET_UTF_8));
                    httpPost.setEntity(byteEntity);
                } else {
                    StringEntity stringEntity = new StringEntity(data, CHARSET_UTF_8);
                    stringEntity.setContentEncoding(CHARSET_UTF_8);
                    if (null != contentType) {
                        stringEntity.setContentType(contentType);
                    } else {
                        stringEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                    }
                    httpPost.setEntity(stringEntity);
                }
            }
            /** 设置请求和传输超时时间*/
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpPost.setConfig(requestConfig);

            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, CHARSET_UTF_8);
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            log.error("连接超时：" + url);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IO异常:" + url);
        } finally {
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error("httpClient.close()异常");
            }
        }
        return null;
    }

    /**
     * HttpClient post请求
     *
     * @param url  请求地址
     * @param data json报文
     * @return
     */
    public static String post(String url, String data) {
        return post(false, url, data, ContentType.APPLICATION_JSON.getMimeType(), null, false);
    }

    /**
     * 进行HttpClean post连接
     * @param url   请求地址
     * @param data  json报文
     * @param isRaw 字节传输
     * @return
     */
    public static String post(String url, String data, boolean isRaw) {
        return post(false, url, data, ContentType.APPLICATION_JSON.getMimeType(), null, isRaw);
    }

    /**
     * 进行HttpClean post连接
     *
     * @param url  请求地址
     * @param data json报文
     * @param isRaw  字节传输
     * @param headers 请求头
     * @return
     */
    public static String post(String url, String data, Map<String, String> headers, boolean isRaw) {
        return post(false, url, data, ContentType.APPLICATION_JSON.getMimeType(), headers, isRaw);
    }

    /**
     * HTTPS访问对象，信任所有证书
     * @return
     */
    public static CloseableHttpClient createSSLInsecureClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }
    /**
     * 模拟登陆，返回的client对象可以保存cookie和session信息，
     * 然后按权限继续进行其他URL请求，
     * 切记：使用完关闭对象try catch finally client.close();
     * @param loginUrl
     * @param loginParam
     * @return
     */
    public static CloseableHttpClient mocklogin(String loginUrl, Map<String, String> loginParam) {
        CloseableHttpClient client = null;
        try {
            // 直接创建client
            client = HttpClients.createDefault();

            // 执行post登陆请求
            HttpPost loginHP = new HttpPost(loginUrl);
            UrlEncodedFormEntity loginEntity = new UrlEncodedFormEntity(getParam(loginParam), "UTF-8");
            loginHP.setEntity(loginEntity);
            HttpResponse loginHR = client.execute(loginHP);
            HttpEntity loginHE = loginHR.getEntity();
            JSONObject loginReturn = JSON.parseObject(EntityUtils.toString(loginHE));

            if(loginReturn==null||!"0".equals(loginReturn.getString("code")) ){
                if(loginReturn==null){
                    log.error("登录失败[{},{}]", loginUrl, "");
                }else{
                    log.error("登录失败[{},{}]",loginUrl,loginReturn.toJSONString());
                }
                return null;
            }

            return client;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用登陆成功的client对象继续其他post请求
     * @param client
     * @param dataUrl
     * @param dataParam
     * @return
     */
    public static String mockPostByClient(CloseableHttpClient client, String dataUrl, Map<String, String> dataParam)  {
        String dataReturn = null;
        try {
            // 使用post方式请求URL数据
            HttpPost dataHP = new HttpPost(dataUrl);
            UrlEncodedFormEntity dataEntity = new UrlEncodedFormEntity(getParam(dataParam), "UTF-8");
            dataHP.setEntity(dataEntity);
            HttpResponse dataHR = client.execute(dataHP);
            HttpEntity dataHE = dataHR.getEntity();
            dataReturn = EntityUtils.toString(dataHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataReturn;
    }

    /**
     * 使用登陆成功的client对象继续其他post请求
     * @param client
     * @param dataUrl
     * @param dataParam
     * @return
     */
    public static String mockPostByClientForRest(CloseableHttpClient client, String dataUrl, Map<String, String> dataParam)  {
        String dataReturn = null;
        try {
            // 使用post方式请求URL数据
            HttpPost dataHP = new HttpPost(dataUrl);
            StringEntity dataEntity = new StringEntity(JSON.toJSONString(dataParam), "UTF-8");
            dataEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            dataHP.setEntity(dataEntity);
            HttpResponse dataHR = client.execute(dataHP);
            HttpEntity dataHE = dataHR.getEntity();
            dataReturn = EntityUtils.toString(dataHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataReturn;
    }

    /**
     * 使用登陆成功的client对象继续其他get请求
     * @param client
     * @param dataUrl
     * @param dataParam
     * @return
     */
    public static String mockGetByClient(CloseableHttpClient client, String dataUrl, Map<String, String> dataParam)  {
        String dataReturn = null;
        try {
            // 使用get方式请求URL数据
            HttpGet dataHG = new HttpGet(dataUrl);
            HttpResponse dataHR = client.execute(dataHG);
            HttpEntity dataHE = dataHR.getEntity();
            dataReturn = EntityUtils.toString(dataHE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataReturn;
    }

    public static byte[] mockGetByClientForStream(CloseableHttpClient client, String dataUrl, Map<String, String> dataParam)  {
        byte[] dataReturn = null;
        try {
            // 使用get方式请求URL数据
            HttpGet dataHG = new HttpGet(dataUrl);
            HttpResponse dataHR = client.execute(dataHG);
            HttpEntity dataHE = dataHR.getEntity();
            dataReturn = EntityUtils.toByteArray(dataHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataReturn;
    }

    /**
     * 模拟登陆，并取出登陆验证cookie，然后构造自己的通用cookie对象
     * @param loginUrl
     * @param loginParam
     * @return
     */
    public static CookieStore mockCookie(String loginUrl, Map<String, String> loginParam) {
        CloseableHttpClient client = null;
        CookieStore cookieStore = null;
        try {
            // 直接创建client
            client = HttpClients.createDefault();

            // 执行post登陆请求
            HttpPost loginHP = new HttpPost(loginUrl);
            UrlEncodedFormEntity loginEntity = new UrlEncodedFormEntity(getParam(loginParam), "UTF-8");
            loginHP.setEntity(loginEntity);
            HttpResponse loginHR = client.execute(loginHP);
            HttpEntity loginHE = loginHR.getEntity();
            JSONObject loginReturn = JSON.parseObject(EntityUtils.toString(loginHE));
            if(loginReturn==null||!"0".equals(loginReturn.getString("code")) ){
                log.error("登录失败");
                return null;
            }

            cookieStore = new BasicCookieStore();
            Header[] headers = loginHR.getHeaders("Set-Cookie");
            String authmark = null;
            for (Header header : headers) {
                authmark = 	header.getValue();
                if(authmark.indexOf("authmark=") != -1){
                    authmark = authmark.replace("authmark=", "");
                    authmark = authmark.substring(0, authmark.indexOf(";"));
                    break;
                }
            }

            // 新建一个Cookie
            BasicClientCookie cookie = new BasicClientCookie("authmark", authmark);
            cookie.setVersion(0);
            cookie.setDomain("127.0.0.1");
            cookie.setPath("/");
            // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
            // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
            // cookie.setAttribute(ClientCookie.PORT_ATTR, "89");
            // cookie.setAttribute(ClientCookie.PATH_ATTR, "/");
            cookieStore.addCookie(cookie);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cookieStore;
    }

    /**
     * 使用登陆成功的Cookie对象继续其他post请求
     * @param cookie
     * @param dataUrl
     * @param dataParam
     * @return
     */
    public static String mockPostByCookie(CookieStore cookie, String dataUrl, Map<String, String> dataParam)  {
        CloseableHttpClient client = null;
        String dataReturn = null;
        try {
            client = HttpClients.custom().setDefaultCookieStore(cookie).build();
            // 使用post方式请求URL数据
            HttpPost dataHP = new HttpPost(dataUrl);
            UrlEncodedFormEntity dataEntity = new UrlEncodedFormEntity(getParam(dataParam), "UTF-8");
            dataHP.setEntity(dataEntity);
            HttpResponse dataHR = client.execute(dataHP);
            HttpEntity dataHE = dataHR.getEntity();
            dataReturn = EntityUtils.toString(dataHE);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataReturn;
    }

    /**
     * 使用登陆成功的Cookie对象继续其他get请求
     * @param cookie
     * @param dataUrl
     * @param dataParam
     * @return
     */
    public static String mockGetByCookie(CookieStore cookie, String dataUrl, Map<String, String> dataParam)  {
        CloseableHttpClient client = null;
        String dataReturn = null;
        try {
            client = HttpClients.custom().setDefaultCookieStore(cookie).build();
            // 使用get方式请求URL数据
            HttpGet dataHG = new HttpGet(dataUrl);
            HttpResponse dataHR = client.execute(dataHG);
            HttpEntity dataHE = dataHR.getEntity();
            dataReturn = EntityUtils.toString(dataHE);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataReturn;
    }

    /**
     * 模拟登陆，并取出登陆验证标示，然后构造在header中使用
     * @param loginUrl
     * @param loginParam
     * @return
     */
    public static String mockHeader(String loginUrl, Map<String, String> loginParam) {
        CloseableHttpClient client = null;
        String authmark = null;
        try {
            // 直接创建client
            client = HttpClients.createDefault();

            // 执行post登陆请求
            HttpPost loginHP = new HttpPost(loginUrl);
            UrlEncodedFormEntity loginEntity = new UrlEncodedFormEntity(getParam(loginParam), "UTF-8");
            loginHP.setEntity(loginEntity);
            HttpResponse loginHR = client.execute(loginHP);
            HttpEntity loginHE = loginHR.getEntity();
            String loginReturn = EntityUtils.toString(loginHE);
            if(!loginReturn.equals("success")){
                log.error("登录失败");
                return null;
            }

            Header[] headers = loginHR.getHeaders("Set-Cookie");
            for (Header header : headers) {
                authmark = 	header.getValue();
                if(authmark.indexOf("authmark=") != -1){
                    authmark = authmark.replace("authmark=", "");
                    authmark = authmark.substring(0, authmark.indexOf(";"));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return authmark;
    }

    /**
     * 使用登陆成功的Cookie对象继续其他post请求
     * @param authmark
     * @param dataUrl
     * @param dataParam
     * @return
     */
    public static String mockPostByHeader(String authmark, String dataUrl, Map<String, String> dataParam)  {
        CloseableHttpClient client = null;
        String dataReturn = null;
        try {
            client = HttpClients.createDefault();
            // 使用post方式请求URL数据
            HttpPost dataHP = new HttpPost(dataUrl);
            UrlEncodedFormEntity dataEntity = new UrlEncodedFormEntity(getParam(dataParam), "UTF-8");
            dataHP.setEntity(dataEntity);
            dataHP.addHeader("authmark", authmark);
            HttpResponse dataHR = client.execute(dataHP);
            HttpEntity dataHE = dataHR.getEntity();
            dataReturn = EntityUtils.toString(dataHE);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataReturn;
    }

    /**
     * map参数转list
     * @param parameterMap
     * @return
     */
    public static List<NameValuePair> getParam(Map<String, String> parameterMap) {
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> parmEntry = it.next();
            param.add(new BasicNameValuePair(parmEntry.getKey(), String.valueOf(parmEntry.getValue())));
        }
        return param;
    }

}