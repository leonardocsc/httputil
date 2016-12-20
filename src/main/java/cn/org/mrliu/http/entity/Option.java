package cn.org.mrliu.http.entity;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author MrLiu
 * @date 2016/12/18
 */
public class Option implements Serializable {
    public static final long serialVersionUID = -8699582951600746712L;
    public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE_DEFAULT = APPLICATION_X_WWW_FORM_URLENCODED;
    public static final String GET = "get";
    public static final String POST = "post";

    public static final String DATA_TYPE_JSON = "json";
    public static final String DATA_TYPE_XML = "xml";
    public static final String DATA_TYPE_SCRIPT = "script";
    public static final String DATA_TYPE_JSONP = "jsonp";
    public static final String DATA_TYPE_TEXT = "text";


    public String url;                     // 路径
    public String type = POST;             // 请求方式
    public Object data;                    // 数据对象
    public String contentType = CONTENT_TYPE_DEFAULT;             // 内容类型
    public String dataType;                // 预期服务器返回的数据类型
    public Map<String,String> headers;     // 请求头信息
    public boolean async = false;         // 是否异步,默认为同步,true:异步

    public Option() {
    }

    public Option(String url, Object data) {
        this.url = url;
        this.data = data;
    }

    public Option(String url, String type, Object data, String contentType, String dataType) {
        this.url = url;
        this.type = type;
        this.data = data;
        this.contentType = contentType;
        this.dataType = dataType;
    }

    public Option(String url, String type, Object data, String contentType, String dataType, Map<String, String> headers) {
        this.url = url;
        this.type = type;
        this.data = data;
        this.contentType = contentType;
        this.dataType = dataType;
        this.headers = headers;
    }

    public Option(String url, String type, Object data, String contentType, String dataType, Map<String, String> headers, boolean async) {
        this.url = url;
        this.type = type;
        this.data = data;
        this.contentType = contentType;
        this.dataType = dataType;
        this.headers = headers;
        this.async = async;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (StringUtils.isBlank(url)){
            return;
        }
        this.url = url.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (StringUtils.isBlank(type)){
            return;
        }
        this.type = type.trim();
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
        if (StringUtils.isBlank(contentType)){
            return;
        }
        this.contentType = contentType.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        if (StringUtils.isBlank(dataType)){
            return;
        }
        this.dataType = dataType.trim();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        if (MapUtils.isEmpty(headers)){
            return;
        }
        this.headers = headers;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    @Override
    public String toString() {
        return "Option{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", data=" + data +
                ", contentType='" + contentType + '\'' +
                ", dataType='" + dataType + '\'' +
                ", headers=" + headers +
                ", async=" + async +
                '}';
    }
}
