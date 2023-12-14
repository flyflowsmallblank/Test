package com.handsome.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonActivity extends AppCompatActivity {
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        initView();
        String jsonData = "{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"desc\": \"我们支持订阅啦~\",\n" +
                "            \"id\": 30,\n" +
                "            \"imagePath\": \"https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png\",\n" +
                "            \"isVisible\": 1,\n" +
                "            \"order\": 2,\n" +
                "            \"title\": \"我们支持订阅啦~\",\n" +
                "            \"type\": 0,\n" +
                "            \"url\": \"https://www.wanandroid.com/blog/show/3352\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"desc\": \"\",\n" +
                "            \"id\": 6,\n" +
                "            \"imagePath\": \"https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png\",\n" +
                "            \"isVisible\": 1,\n" +
                "            \"order\": 1,\n" +
                "            \"title\": \"我们新增了一个常用导航Tab~\",\n" +
                "            \"type\": 1,\n" +
                "            \"url\": \"https://www.wanandroid.com/navi\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"desc\": \"一起来做个App吧\",\n" +
                "            \"id\": 10,\n" +
                "            \"imagePath\": \"https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png\",\n" +
                "            \"isVisible\": 1,\n" +
                "            \"order\": 1,\n" +
                "            \"title\": \"一起来做个App吧\",\n" +
                "            \"type\": 1,\n" +
                "            \"url\": \"https://www.wanandroid.com/blog/show/2\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"errorCode\": 0,\n" +
                "    \"errorMsg\": \"\"\n" +
                "}";
        BannerData bannerData = decodeJson(jsonData);
        setData(bannerData);
    }

    private void setData(BannerData bannerData) {
        mTvTitle.setText(bannerData.data.get(0).title);
    }

    private void initView() {
        mTvTitle = findViewById(R.id.main_activity_json_tv_title);
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
}