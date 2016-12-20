package cn.org.mrliu.http.util;

import cn.org.mrliu.http.entity.Option;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author MrLiu
 * @date 2016/12/17
 */
public class ${

    public static <T>T ajax(Option option) throws Exception{
        String url = option.getUrl();
        String type = option.getType();
        Object data = option.getData();
        String contentType = option.getContentType();
        String dataType = option.getDataType();


        if (Option.POST.equalsIgnoreCase(type)){
            if (Option.APPLICATION_JSON.equalsIgnoreCase(contentType)){
                String json = JSON.toJSONString(data);
                String result = HttpUtil.postJson(url,json);
                return handleResult(dataType,result);
            }else if (Option.APPLICATION_X_WWW_FORM_URLENCODED.equalsIgnoreCase(contentType)){
                String result = HttpUtil.postObject(url,data);
                // 测试异步回调
//                option.success.callback(result);
                return handleResult(dataType,result);
            }else{

            }
        }else if (Option.GET.equalsIgnoreCase(type)){

        }

        return null;
    }


    private static <T>T handleResult(String dataType,String string) throws Exception{
        if (string == null){
            return null;
        }
        string = string.trim();
        if (Option.JSON.equalsIgnoreCase(dataType)){
            return (T) handleJsonResult(string);
        }else if (Option.TEXT.equalsIgnoreCase(dataType)){
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
    private static JSONObject handleJsonResult(String string) throws Exception{
        if (StringUtils.isBlank(string)){
            return null;
        }
        string = string.trim();
        JSONObject jsonObject = JSON.parseObject(string);
        return jsonObject;
    }

}
