package cn.org.mrliu.http.test;

import cn.org.mrliu.http.entity.Option;
import cn.org.mrliu.http.entity.test.Account;
import cn.org.mrliu.http.entity.test.User;
import cn.org.mrliu.http.util.$;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;

/**
 * @author MrLiu
 * @date 2016/12/18
 */
public class Test$ {

    public static void main(String[] args) throws Exception{
        User user = new User();
        user.setId(1234);
        user.setName("张三");

        Account account = new Account();
        account.setId(100);
        account.setName("admin");
        account.setPwd("123456");
        account.setScores(Arrays.asList(66.66,77.77,88.88));
        user.setAccount(account);

        user.setColors(new String[]{"黑色","灰色","金色"});
        user.setScores(Arrays.asList(12.3,55.6));

        System.out.println(JSON.toJSONString(user,true));

        String url = "http://localhost:8080/api/test/add";


        Option option = new Option();
//        option.setUrl(url);
//        option.setType("POST");
//        option.setData(user);
        option.url = url;
        option.type = "POST";
        option.data = user;
        option.dataType = "json";
        option.success = (result) ->{
            System.out.println("xxx");
        };

        JSONObject result = $.ajax(option);
        System.out.println("==========");
        System.out.println(JSON.toJSONString(result,true));
    }

}
