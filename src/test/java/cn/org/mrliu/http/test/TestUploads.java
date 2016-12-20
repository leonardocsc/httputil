package cn.org.mrliu.http.test;

import cn.org.mrliu.http.entity.FileOption;
import cn.org.mrliu.http.util.HttpUtil;
import org.apache.commons.collections.map.HashedMap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MrLiu
 * @date 2016/12/20
 */
public class TestUploads {
    public static void main(String[] args) throws Exception{
        String url = "http://localhost:8080/api/upload/uploads";
        List<FileOption> fileOptions = new ArrayList<>();
        String formName = "images";
        fileOptions.add(new FileOption(formName,new File("D:\\testUploadImages\\01.jpg")));
        fileOptions.add(new FileOption(formName,new File("D:\\testUploadImages\\02.jpg")));
        fileOptions.add(new FileOption(formName,new File("D:\\testUploadImages\\03.jpg")));
        Map<String,Object> param = new HashedMap();
        param.put("id",1);
        param.put("name","测试参数");
        param.put("account.name","admin");
        param.put("account.pwd","123456");
        String result = HttpUtil.upload(url,fileOptions,param);
        System.out.println("result:"+result);
    }
}
