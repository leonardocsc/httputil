package cn.org.mrliu.http.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author MrLiu
 * @date 2016/12/20
 */
public class TestJSON {
    public static void main(String[] args) {
//        String jsonString = "{\"account\":\"admin\",\"pwd\":\"123456\"}";
        String jsonString = "[{\"account\":\"admin\",\"pwd\":\"123456\"},{\"account\":\"hello\",\"pwd\":\"123123\"}]";
        Object object = JSON.parse(jsonString);
        System.out.println(object);
        System.out.println(object instanceof JSONObject);
        System.out.println(object instanceof JSONArray);
        if (object instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) object;
            System.out.println("jsonObject:"+jsonObject);
        }else if (object instanceof  JSONArray){
            JSONArray jsonArray = (JSONArray) object;
            System.out.println("jsonArray:"+jsonArray);
        }
    }
}
