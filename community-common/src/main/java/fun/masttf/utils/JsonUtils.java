package fun.masttf.utils;

import java.util.List;

import org.slf4j.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class JsonUtils {
    public static final Logger logger = org.slf4j.LoggerFactory.getLogger(JsonUtils.class);

    /*
     * 将对象转换为json字符串
     */
    public static String convertObj2Json(Object obj) {
        return JSON.toJSONString(obj);
    }

    /*
     * 将json字符串转换为对象
     */
    public static <T> T convertJson2Obj(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /*
     * 将json数组转换为对象列表
     */
    public static <T> List<T> convertJsonList2Obj(String json, Class<T> clazz) {
        return JSONArray.parseArray(json, clazz);
    }
}
