package com.wenkai.my.gift.http;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by my on 2016/3/8.
 */
public class HttpUtils {

    private static ExecutorService mExecutorService;
    private static RequestCallBack mRequestCallBack;

    //创建一个定长的线程池
    static {
        mExecutorService = Executors.newFixedThreadPool(4);
    }

    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mRequestCallBack != null){
                mRequestCallBack.onSuccess(msg.obj.toString(),msg.what);
            }
        }
    };

    /**
     * Get请求
     */
    public static void requestGet(final String urlparam,final RequestCallBack callBack,final int requestCode){
        mRequestCallBack = callBack;
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                String result = httpGet(urlparam);
                Message msg = handler.obtainMessage();
                msg.obj = result;
                msg.what = requestCode;
                handler.sendMessage(msg);
            }
        });
    }

    private static String httpGet(String urlparam) {
        InputStream is = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlparam);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //获取请求的状态码
            if (httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK){
                is = httpURLConnection.getInputStream();
                StringBuffer sb = new StringBuffer();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1){
                    sb.append(new String(buffer,0,len));
                }
                return sb.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

}
