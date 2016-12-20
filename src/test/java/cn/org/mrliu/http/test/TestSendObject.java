package cn.org.mrliu.http.test;

import cn.org.mrliu.http.entity.test.Account;
import cn.org.mrliu.http.entity.test.Address;
import cn.org.mrliu.http.entity.test.User;
import cn.org.mrliu.http.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author MrLiu
 * @date 2016/12/17
 */
public class TestSendObject {

    public static void main(String[] args) throws Exception{
        /**
         * $.get("test.cgi", { name: "John", time: "2pm" },
         function(data){
         alert("Data Loaded: " + data);
         });
         */



        /**
         * account.id:1
         account.name:2
         account.pwd:3
         addresses[0].id:4
         addresses[0].name:5
         addresses[0].city:6
         addresses[0].region:7
         addresses[0].detail:8
         id:9
         name:10
         */

        User user = new User();
        user.setId(1234);
        user.setName("张三");

        Account account = new Account();
        account.setId(100);
        account.setName("admin");
        account.setPwd("123456");
        account.setScores(Arrays.asList(66.66,77.77,88.88));
        user.setAccount(account);


//        List<Address> addresses = new ArrayList<>();
//        Address address = new Address();
//        address.setId(1000);
//        address.setName("家");
//        address.setProvince("四川");
//        address.setCity("成都");
//        address.setDetail("高新区....");
//        addresses.add(address);
//
//        address = new Address();
//        address.setId(1000);
//        address.setName("公司");
//        address.setProvince("四川");
//        address.setCity("成都");
//        address.setDetail("世纪城....");
//        addresses.add(address);
//
//        user.setAddresses(addresses);


        user.setColors(new String[]{"黑色","灰色","金色"});
        user.setScores(Arrays.asList(12.3,55.6));

        System.out.println(JSON.toJSONString(user,true));

        String url = "http://localhost:8080/api/test/add";
//        String result = HttpUtil.postObject(url,user);
        String result = HttpUtil.getObject(url,user);
        System.out.println(result);

    }



}
