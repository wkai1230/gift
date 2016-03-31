package com.wenkai.my.gift.http;

import com.google.gson.Gson;

/**
 * Created by my on 2016/3/16.
 */
public class GsonTool {
    private static Gson gson = new Gson();

    /**
     * 将一个Json字符串解析为对象
     * @param json
     * @param object
     * @param <T>
     * @return
     */
    public static <T> T parseJson2Object(String json,Class<T> object){
        return gson.fromJson(json,object);
    }
}
