package cn.org.mrliu.http.entity;

import cn.org.mrliu.http.callback.Callback;
import javafx.geometry.Pos;

import java.io.Serializable;

/**
 * @author MrLiu
 * @date 2016/12/18
 */
public class Option implements Serializable {
    public static final long serialVersionUID = -8699582951600746712L;
    public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE_DEFAULT = APPLICATION_X_WWW_FORM_URLENCODED;
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String TYPE_DEFAULT = GET;

    public static final String JSON = "json";
    public static final String XML = "xml";
    public static final String SCRIPT = "script";
    public static final String JSONP = "jsonp";
    public static final String TEXT = "text";




    public String url;                     // 路径
    public String type = POST;             // 请求方式
    public Object data;                    // 数据对象
    public String contentType = CONTENT_TYPE_DEFAULT;             // 内容类型
    public String dataType;                // 预期服务器返回的数据类型
    public String headers;                 // 请求头信息
    public boolean async = false;         // 是否异步,默认为同步,true:异步
    public Callback success;               // 成功回调
    public Callback error;                 // 失败回调

    public Option() {
    }

    public Option(String url, String type, Object data, String contentType, String dataType, String headers, Callback success) {
        this.url = url;
        this.type = type;
        this.data = data;
        this.contentType = contentType;
        this.dataType = dataType;
        this.headers = headers;
        this.success = success;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public Callback getSuccess() {
        return success;
    }

    public void setSuccess(Callback success) {
        this.success = success;
    }

    public static void main(String[] args) {
        Option option = new Option();
        option.setSuccess((result) -> {

        });

    }
}
