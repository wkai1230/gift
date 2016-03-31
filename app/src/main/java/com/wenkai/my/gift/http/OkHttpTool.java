package com.wenkai.my.gift.http;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wenkai.my.gift.bean.HomeHeartExpandInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 1 导包
 * 2 初始化OkHttpClient
 *      (注意：官方建议OKHttpClient在应用中使用单例；一个应用只拥有一个OkHttpClient对象)
 * 3 请求网络（POST，GET）
 * Created by wkai on 2016/3/16.
 */
public class OkHttpTool {
    private static OkHttpClient okHttpClient;
    private static Map<Activity,OkHttpTool> toolMap = new HashMap<>();
    private List<Call> callList = new ArrayList<>();

    static {
        if (okHttpClient == null){
            okHttpClient = new OkHttpClient();
        }
    }

    public static OkHttpTool newInstance(Activity activity) {
        OkHttpTool okHttpTool = toolMap.get(activity);
        if (okHttpTool == null){
            okHttpTool = new OkHttpTool();
            toolMap.put(activity,okHttpTool);
        }
        return okHttpTool;
    }

    private  Handler mHandler = new Handler();

    public <T> void okGet(String url, final Class<T> clazz, final IOkCallBack callBack) {
        //创建一个请求
        final Request request = new Request.Builder().url(url).build();
        //执行请求
        Call call = okHttpClient.newCall(request);
        callList.add(call);
        call.enqueue(new Callback() {
            //请求失败
            @Override
            public void onFailure(Call call, IOException e) {
                //执行在工作线程中，不能直接对UI进行更新

            }

            //请求成功
            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //执行在工作线程中，不能直接对UI进行更新

                //Gson解析请求结果
                final T homeHeartExpandInfo = GsonTool.parseJson2Object(response.body().string(), clazz);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(homeHeartExpandInfo);
                    }
                });
            }
        });
    }

    /**
     * 取消链接
     */
    public void cancel(Activity activity) {
        //TODO
        if (callList != null && !callList.isEmpty()) {
            for (int i=callList.size()-1; i>=0; i--) {
                callList.get(i).cancel();
                callList.remove(i);
            }
        }

        if (toolMap != null && toolMap.get(activity) != null) {
            toolMap.remove(activity);
        }
    }

}
