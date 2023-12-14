package com.handsome.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class NetActivity extends AppCompatActivity {
    private TextView mTvData;
    private Button mBtnSend;

    private Handler mHandler;

    private final String mGetUrl = "https://www.wanandroid.com/banner/json";
    private final String mPostUrl = "https://www.wanandroid.com/user/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        mHandler = new MyHandler();
        initView();
        initClick();
    }

    private void initView() {
        mTvData = findViewById(R.id.main_activity_net_tv_data);
        mBtnSend = findViewById(R.id.main_activity_net_btn_send);
    }

    private void initClick() {
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 网络请求
                sendGetRequest(mGetUrl);
//                HashMap<String,String> params = new HashMap<>();
//                params.put("username","薛暗浊");
//                params.put("password","123456");
//                params.put("repassword","123456");
//                sendPostRequest(mPostUrl,params);
            }
        });
    }

    private void sendGetRequest(String theUrl){
        new Thread(
                () -> {
                    try {
                        URL url = new URL(theUrl);
                        HttpURLConnection connection = (HttpURLConnection)
                                url.openConnection();
                        connection.setRequestMethod("GET");//设置请求方式为GET
                        connection.setConnectTimeout(8000);//设置最大连接时间，单位为ms
                        connection.setReadTimeout(8000);//设置最大的读取时间，单位为ms
                        connection.setRequestProperty("Accept-Language",
                                "zh-CN,zh;q=0.9");
                        connection.setRequestProperty("Accept-Encoding",
                                "gzip,deflate");
                        connection.connect();//正式连接
                        InputStream in = connection.getInputStream();//从接口处获取
                        String responseData = StreamToString(in);//这里就是服务器返回的数据
                        Message message = new Message();
                        message.obj = responseData;
                        mHandler.sendMessage(message);
                        Log.d("lx", "sendGetNetRequest: "+responseData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //这个Message msg 就是从另一个线程传递过来的数据
            //在这里进行你要对msg的处理，将JSON字段解析
            String responseData = msg.obj.toString();
            setText(decodeJson(responseData));
        }
    }

    private void setText(BannerData bannerData) {
        mTvData.setText(bannerData.data.get(0).title);
    }

    private BannerData decodeJson(String data) {
        // 数据存储对象
        BannerData bannerData = new BannerData();
        bannerData.data = new ArrayList<>();
        try {
            // 获得这个JSON对象{}
            JSONObject jsonObject = new JSONObject(data);
            // 获取并列的三个，errorCode，errorMsg，data
            bannerData.errorCode = jsonObject.getInt("errorCode");
            bannerData.errorMsg = jsonObject.getString("errorMsg");
            // data是一个对象集合
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            BannerData.DetailData detailData;
            for (int i = 0; i < jsonArray.length(); i++) {
                // 遍历数组，给集合添加对象
                detailData = new BannerData.DetailData();
                JSONObject singleData = jsonArray.getJSONObject(i);
                detailData.desc =  singleData.getString("desc");
                detailData.id =  singleData.getInt("id");
                detailData.imagePath =  singleData.getString("imagePath");
                detailData.isVisible =  singleData.getInt("isVisible");
                detailData.order = singleData.getInt("order");
                detailData.title =  singleData.getString("title");
                detailData.type =  singleData.getInt("type");
                detailData.url =  singleData.getString("url");
                bannerData.data.add(detailData);
            }
        }catch (Exception e){
            Log.w("lx","(JsonActivity.java:59)-->>",e);
        }
        return bannerData;
    }

    private void sendPostRequest(String theUrl,HashMap<String,String> params){
        new Thread(
                () -> {
                    try {
                        URL url = new URL(theUrl);
                        HttpURLConnection connection = (HttpURLConnection)
                                url.openConnection();
                        connection.setRequestMethod("POST");//设置请求方式为GET
                        connection.setConnectTimeout(8000);//设置最大连接时间，单位为ms
                        connection.setReadTimeout(8000);//设置最大的读取时间，单位为ms
                        connection.setDoOutput(true);//允许输入流
                        connection.setDoInput(true);//允许输出流
                        StringBuilder dataToWrite = new StringBuilder();//构建参数值
                        for (String key : params.keySet()) {
                            dataToWrite.append(key).append("=").append(params.get(key)).append("&");//拼接参
                        }
                        connection.connect();//正式连接
                        OutputStream outputStream = connection.getOutputStream();//开
                        outputStream.write(dataToWrite.substring(0,
                                dataToWrite.length() - 1).getBytes());//去除最后一个&
                        InputStream in = connection.getInputStream();//从接口处获取输入
                        String responseData = StreamToString(in);//这里就是服务器返回的
                        Log.d("lx", "sendPostNetRequest: "+responseData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }



    private String StreamToString(InputStream in) {
        StringBuilder sb = new StringBuilder();//新建一个StringBuilder，用于一点一点
        String oneLine;//流转换为字符串的一行
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            while ((oneLine = reader.readLine()) != null) {//readLine方法将读取一行
                sb.append(oneLine).append('\n');//拼接字符串并且增加换行，提高可读性
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();//关闭InputStream
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();//将拼接好的字符串返回出去
    }
}