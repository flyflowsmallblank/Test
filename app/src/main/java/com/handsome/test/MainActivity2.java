package com.handsome.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity2 extends AppCompatActivity {

    private static final String INTENT_USER_NAME = "username"; //intent 中的数据标记 (数据键)
    private static final String INTENT_PASSWORD = "password"; //intent 中的数据标记 (数据键)
    /**
     * 写一个 static 方法，让它来决定该传入那些数据
     */
    public static void startActivity(Context context, String username,
                                     String password) {
        Intent intent = new Intent(context, MainActivity2.class);
        intent.putExtra(INTENT_USER_NAME, username);
        intent.putExtra(INTENT_PASSWORD, password);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String mUserName = intent.getStringExtra(INTENT_USER_NAME);
        String mPassword = intent.getStringExtra(INTENT_PASSWORD);
        Log.d("tag","传过来的username: " + mUserName + "; password: "
                +mPassword);
    }
}
