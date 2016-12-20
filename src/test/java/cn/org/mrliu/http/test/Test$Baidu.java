package cn.org.mrliu.http.test;

import cn.org.mrliu.http.entity.Option;
import cn.org.mrliu.http.util.$;

/**
 * @author MrLiu
 * @date 2016/12/20
 */
public class Test$Baidu {

    public static void main(String[] args) throws Exception{
        // 全局设置
        Option globalOption = new Option();
        globalOption.setType(Option.POST);
        $.ajaxSetup(globalOption);

        String keyword = "java";
        Option option = new Option();
        option.setUrl("https://www.baidu.com/s?ie=utf-8&wd="+keyword);
        option.setType(Option.GET);
        // or
//        option.url = "https://www.baidu.com/s?ie=utf-8&wd="+keyword;
//        option.type = "get";

        // 发送请求并获取结果
        String result = $.ajax(option);
        System.out.println(result);
    }
}
