package cn.org.mrliu.http.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Http工具类
 * @author MrLiu
 * @date 2016/12/17
 */
public class HttpUtil {
    private static BasicCookieStore cookieStore;
    private static CloseableHttpClient httpClient;
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    public static final ContentType CONTENT_TYPE_IMAGE_JPEG = ContentType.create("image/jpeg");
    public static final ContentType CONTENT_TYPE_IMAGE_PNG = ContentType.create("image/png");
    public static final ContentType CONTENT_TYPE_IMAGE_GIF = ContentType.create("image/gif");
    private static final String JPG = "jpg";
    private static final String JPEG = "jpeg";
    private static final String PNG = "png";
    private static final String GIF = "gif";
    private static final String UTF8 = "UTF-8";

    public static final String USER_AGENT_CHROME_54 = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    public static final String USER_AGENT_FIREFOX_50 = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0";
    public static final String USER_AGENT_IE_11_EDGE = "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko";
    public static final String USER_AGENT_IE_9 = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)";
    public static final String USER_AGENT_IE_8 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3)";

    static {
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        cookieStore = new BasicCookieStore();
        httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCookieStore(cookieStore)
                .build();
    }
    public static String get(String url) throws Exception{
        return get(url,null);
    }

    public static String get(String url,Map<String,Object> headers) throws Exception{
        logger.debug("url:"+url);
        HttpGet httpGet = new HttpGet(url);
        setCommonRequestHeaders(httpGet);
        setRequestHeaders(httpGet,headers);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        System.out.println("statusLine"+httpResponse.getStatusLine());
        HttpEntity httpEntity = httpResponse.getEntity();
        String result = EntityUtils.toString(httpEntity);
        System.out.println("result"+result);
        EntityUtils.consume(httpEntity);
        httpResponse.close();
        return result;
    }

    public static String getObject(String url,Object object) throws Exception{
        return getObject(url,object,null);
    }

    public static String getObject(String url,Object object,Map<String,Object> headers) throws Exception{
        List<NameValuePair> nameValuePairs = convertSerializeForm(object);
        String queryString = serializeQueryString(nameValuePairs);
        Integer questionMarkIndexOf = url.indexOf("?");
        if (questionMarkIndexOf == -1){
            url = url + "?" + queryString;
        }
        Integer indexOf = url.indexOf("&");
        if (indexOf == -1){
            url += "&" + queryString;
        }
        System.out.println("url:"+url);
        return get(url,headers);
    }

    public static InputStream getInputStream(String url) throws Exception{
        System.out.println("url"+url);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        System.out.println("statusLine"+httpResponse.getStatusLine());
        HttpEntity httpEntity = httpResponse.getEntity();
        InputStream inputStream = httpEntity.getContent();
//        EntityUtils.consume(httpEntity);
//        httpResponse.close();
        return inputStream;
    }
    public static String post(String url, List<NameValuePair> nameValuePairs) throws Exception{
       return post(url,nameValuePairs,null);
    }

    public static String post(String url, List<NameValuePair> nameValuePairs,Map<String,Object> headers) throws Exception{
        HttpPost httpPost = new HttpPost(url);
        setCommonRequestHeaders(httpPost);
        setRequestHeaders(httpPost,headers);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,UTF8));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        System.out.println("statusLine"+httpResponse.getStatusLine());
        HttpEntity httpEntity = httpResponse.getEntity();
        String result = EntityUtils.toString(httpEntity);
        System.out.println("result"+result);
        return result;
    }

    public static String postObject(String url,Object object) throws Exception{
        return postObject(url,object,null);
    }

    public static String postObject(String url,Object object,Map<String,Object> headers) throws Exception{
        List<NameValuePair> nameValuePairs = convertSerializeForm(object);
        System.out.println(JSON.toJSONString(nameValuePairs,true));
        String string = serializeQueryString(nameValuePairs);
        System.out.println("serializeQueryString:"+string);
        return post(url,nameValuePairs,headers);
    }

    private static List<NameValuePair> convertSerializeForm(Object object) {
        String json = JSON.toJSONString(object);
        JSONObject jsonObject = JSON.parseObject(json);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        serializeForm(null,nameValuePairs,jsonObject);
        return nameValuePairs;
    }

    public static String serializeQueryString(List<NameValuePair> nameValuePairs){
        if (CollectionUtils.isEmpty(nameValuePairs)){
            return null;
        }
        // ?a=1&b=2
        String string = "";
        for (NameValuePair nvp : nameValuePairs){
            string += "&" + nvp.getName() + "=" + nvp.getValue();
        }
        string = string.substring(1);
        return string;
    }


    public static String upload(String url,String formName, File file){
        return upload(url,formName,file,null);
    }

    public static String upload(String url,String formName, File file,Map<String,Object> params){
        return upload(url,formName,file,params,null);
    }

    public static String upload(String url,String formName, File file,Map<String,Object> params,Map<String,Object> headers){
        try {
            System.out.println("upload");
            System.out.println("url:"+url);
            System.out.println("formName:"+formName);
            System.out.println("file:"+file);
            System.out.println("params:"+params);
            HttpPost httpPost = new HttpPost(url);

            ContentType contentType = null;
            String fileName = file.getName();
            fileName = fileName.toLowerCase();
            if (fileName.endsWith(JPG) || fileName.endsWith(JPEG)){
                contentType = CONTENT_TYPE_IMAGE_JPEG;
            }else if (fileName.endsWith(PNG)){
                contentType = CONTENT_TYPE_IMAGE_PNG;
            }else if (fileName.endsWith(GIF)){
                contentType = CONTENT_TYPE_IMAGE_GIF;
            }
            FileBody fileBody = new FileBody(file,contentType);

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                    .addPart(formName,fileBody);

            // 构建参数
            if (MapUtils.isNotEmpty(params)){
                for (Map.Entry<String,Object> entry : params.entrySet()){
                    StringBody stringBody = new StringBody(String.valueOf(entry.getValue()), ContentType.TEXT_PLAIN);
                    multipartEntityBuilder.addPart(entry.getKey(),stringBody);
                }
            }

            HttpEntity httpEntity = multipartEntityBuilder.build();

            httpPost.setEntity(httpEntity);

            setCommonRequestHeaders(httpPost);
            setRequestHeaders(httpPost,headers);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            System.out.println("response status"+response.getStatusLine());
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            System.out.println("result:"+result);
            EntityUtils.consume(responseEntity);
            response.close();
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    public static List<String> upload(String url,String formName, File[] files){
        if (files != null){
            List<String> results = new ArrayList<>();
            for (File file : files){
                String result = upload(url,formName,file);
                results.add(result);
            }
            return results;
        }
        return null;
    }

    public static String postJson(String url, String data){
        return postJson(url,data,null);
    }

    public static String postJson(String url, String data,Map<String,Object> headers){
        try {
            System.out.println("url:"+url);
            System.out.println("data:"+data);
            HttpPost httpPost = new HttpPost(url);
            setCommonRequestHeaders(httpPost);
            setRequestHeaders(httpPost,headers);
            StringEntity stringEntity = new StringEntity(data, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println("response status:"+httpResponse.getStatusLine());
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);
            EntityUtils.consume(httpEntity);
            httpResponse.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    public static void setCommonRequestHeaders(HttpUriRequest request){
        request.setHeader("User-Agent",USER_AGENT_CHROME_54);
    }

    public static void setXMLHttpRequestHeaders(HttpUriRequest request){
        request.setHeader("X-Requested-With","XMLHttpRequest");
    }

    public static void setRequestHeaders(HttpUriRequest request, Map<String,Object> headers){
        if (MapUtils.isEmpty(headers)){
            return;
        }
        for (Map.Entry<String,Object> entry : headers.entrySet()){
            request.setHeader(entry.getKey(),String.valueOf(entry.getValue()));
        }
    }

    public static String submitObject(String url,List<NameValuePair> nameValuePairs) throws Exception{

        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");

        setCommonRequestHeaders(httpPost);
//        setRequestHeaders(httpPost,headers);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,UTF8));

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        System.out.println("statusLine"+httpResponse.getStatusLine());
        HttpEntity httpEntity = httpResponse.getEntity();
        String result = EntityUtils.toString(httpEntity);
        System.out.println("result"+result);
        return result;

    }

    public static void serializeForm(String prefix,List<NameValuePair> nameValuePairs,JSONObject jsonObject){
        for (Map.Entry<String,Object> entry : jsonObject.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JSONObject){
                JSONObject valueJsonObject = (JSONObject) value;
                serializeForm(key,nameValuePairs,valueJsonObject);

            }else if (value instanceof JSONArray){
                JSONArray jsonArray = (JSONArray) value;
                System.out.println("ComponentType:"+jsonArray.getComponentType());
                System.out.println(jsonArray);
                System.out.println(jsonArray.getClass());
                for (int i = 0; i < jsonArray.size(); i++) {
                    Object object = jsonArray.get(i);
                    if (object instanceof JSONObject){
                        JSONObject arrayJsonObject = (JSONObject) object;
                        serializeForm(key+"["+i+"]",nameValuePairs,arrayJsonObject);
                    }else{
                        System.out.println("+++===>>");
                        System.out.println(key+"-"+value);
                        System.out.println(value.getClass());
                        setNameValuePair(prefix,key+"["+i+"]",object,nameValuePairs);

                    }
                }
            }else{
                setNameValuePair(prefix, key, value, nameValuePairs);
            }
        }
    }

    private static void setNameValuePair(String prefix, String key, Object value, List<NameValuePair> nameValuePairs) {
        String fullKey = null;
        if (StringUtils.isNotBlank(prefix)){
            fullKey = prefix + "." + key;
        }else{
            fullKey = key;
        }
        nameValuePairs.add(new BasicNameValuePair(fullKey,String.valueOf(value)));
    }

    public static Object ajax() throws Exception{

        return null;
    }

    public static void main(String[] args) throws Exception{
//        String result = upload("http://localhost:8080/api/upload/","file",new File("D:\\test.png"));
//        System.out.println(result);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("account","66123456");
//        jsonObject.put("password","xxxxxxxxxxx");
//        String result = postJson("http://localhost:8080/api/cpf/cd/account",jsonObject.toString());
//        System.out.println(result);

//        Map<String,Object> map = new HashMap<>();
//        map.put("fileId",2);
//        String result = upload("http://localhost:8080/api/upload/","file",new File("D:\\test.png"),map);
//        System.out.println(result);

    }

}
