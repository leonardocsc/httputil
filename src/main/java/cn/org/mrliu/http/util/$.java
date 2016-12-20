package cn.org.mrliu.http.util;

import cn.org.mrliu.http.entity.FileOption;
import cn.org.mrliu.http.entity.Option;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author MrLiu
 * @date 2016/12/17
 */
public class ${

    private static Option GLOBAL_OPTION;

    public static <T>T ajax(Option option) throws Exception{
        if (option == null){
            throw new RuntimeException("option不能为空");
        }
        // 先设置默认的option值
        if (GLOBAL_OPTION != null){
            BeanCopier.create(Option.class,Option.class,false).copy(GLOBAL_OPTION,option,null);
        }
        String url = option.getUrl();
        String type = option.getType();
        Object data = option.getData();
        String contentType = option.getContentType();
        String dataType = option.getDataType();
        Map<String,String> headers = option.getHeaders();


        if (Option.POST.equalsIgnoreCase(type)){
            if (Option.APPLICATION_JSON.equalsIgnoreCase(contentType)){
                String json = JSON.toJSONString(data);
                String result = HttpUtil.postJson(url,json,headers);
                return handleResult(dataType,result);
            }else if (Option.APPLICATION_X_WWW_FORM_URLENCODED.equalsIgnoreCase(contentType)){
                String result = HttpUtil.postObject(url,data,headers);
                return handleResult(dataType,result);
            }else{

            }
        }else if (Option.GET.equalsIgnoreCase(type)){
            String result = HttpUtil.get(url,headers);
            return handleResult(dataType,result);
        }

        return null;
    }

    public static void ajaxSetup(Option option){
        GLOBAL_OPTION = option;
    }


    public static <T>T get(String url) throws Exception{
        return get(url,null);
    }

    public static <T>T get(String url,Object data) throws Exception{
        return get(url,data,null);
    }

    public static <T>T get(String url,Object data,String dataType) throws Exception{
        Option option = new Option();
        option.setType(Option.GET);
        option.setUrl(url);
        option.setData(data);
        option.setDataType(dataType);
        return $.ajax(option);
    }

    public static <T>T getJSON(String url,Object data) throws Exception{
        return $.get(url,data,Option.DATA_TYPE_JSON);
    }



    public static <T>T post(String url) throws Exception{
        return post(url,null);
    }

    public static <T>T post(String url,Object data) throws Exception{
        return post(url,data,null);
    }

    public static <T>T post(String url,Object data,String dataType) throws Exception{
        Option option = new Option();
        option.setType(Option.POST);
        option.setUrl(url);
        option.setData(data);
        option.setDataType(dataType);
        return $.ajax(option);
    }

    public static <T>T postJSON(String url,Object data,String dataType) throws Exception{
        return post(url,data,dataType);
    }

    public static String upload(String url, String formName, File file) throws Exception{
        return upload(url,formName,file,null);
    }

    public static String upload(String url, String formName, File file, Map<String,String> params) throws Exception{
        return upload(url,formName,file,null,null);
    }

    public static <T>T upload(String url, String formName, File file, Map<String,String> params, Map<String,String> headers) throws Exception{
        String result = HttpUtil.upload(url,formName,file,params,headers);
        return handleResult(null,result);
    }

    public static <T>T upload(String url, List<FileOption> fileOptions) throws Exception{
        return upload(url,fileOptions);
    }

    public static <T>T upload(String url, List<FileOption> fileOptions, Map<String,String> params) throws Exception{
        return upload(url,fileOptions,params);
    }

    public static <T>T upload(String url, List<FileOption> fileOptions, Map<String,String> params, Map<String,String> headers) throws Exception{

        return null;
    }



    private static <T>T handleResult(String dataType,String string) throws Exception{
        if (string == null){
            return null;
        }
        string = string.trim();
        if (Option.DATA_TYPE_JSON.equalsIgnoreCase(dataType)){
            return handleJsonResult(string);
        }else if (Option.DATA_TYPE_TEXT.equalsIgnoreCase(dataType)){
            return (T) string;
        }
        return null;
    }

    /**
     * 获取JSON结果
     * @param string
     * @return
     * @throws Exception
     */
    private static <T>T handleJsonResult(String string) throws Exception{
        if (StringUtils.isBlank(string)){
            return null;
        }
        string = string.trim();

        Object object = JSON.parse(string);
        System.out.println(object);
        System.out.println(object instanceof JSONObject);
        System.out.println(object instanceof JSONArray);
        if (object instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) object;
            System.out.println("jsonObject:"+jsonObject);
            return (T) jsonObject;
        }else if (object instanceof  JSONArray){
            JSONArray jsonArray = (JSONArray) object;
            System.out.println("jsonArray:"+jsonArray);
            return (T) jsonArray;
        }
        return null;
    }

    public static void main(String[] args) throws Exception{
//        Option globalOption = new Option();
//        globalOption.setType("111111");
//        globalOption.setContentType("22222222222");
//        ajaxSetup(globalOption);

        String keyword = "java";


        Option option = new Option();
        option.setUrl("https://www.baidu.com/s?ie=utf-8&wd="+keyword);
        option.setType(Option.GET);

        /**
         * var option = {};
         * option.url = "";
         * option.data = "";
         */

        option.url = "";
        option.data = "";


        String result = $.ajax(option);



        System.out.println(result);
    }

}
