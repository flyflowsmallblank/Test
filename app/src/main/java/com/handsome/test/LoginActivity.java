package com.handsome.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private ImageView mImgHead;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private long mExitTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initLogin();
    }

    /**
     * 该方法专门用于做些与 初始化 View有关的工作
     */
    private void initView() {
        mImgHead = findViewById(R.id.img_main_head);
        mEtUsername = findViewById(R.id.et_main_username);
        mEtPassword = findViewById(R.id.et_main_password);
        mBtnLogin = findViewById(R.id.btn_main_login);
    }

    /**
     * 专门用于登录的方法
     */
    private void initLogin() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    /**
     * 执行登录逻辑的方法
     */
    private void login() {
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        String content = "username = "+username+"password" + password;
        Log.d("日志筛选名词", content);
        if (username.equals("123456") && password.equals("123456")) {
            loginSuccess(username, password);
        } else {
            loginFailure();
        }
    }

    /**
     * 登录失败执行的方法
     */
    private void loginFailure() {
        Toast.makeText(this, "账号或者密码好像输错了 :(", Toast.LENGTH_SHORT).show();
    }

    /**
     * 登录成功执行的方法
     *
     * @param username 正确的用户名
     * @param password 正确的密码
     */
    private void loginSuccess(String username, String password) {
        Toast.makeText(this, "登陆成功!", Toast.LENGTH_SHORT).show();
//        MainActivity2.startActivity(this, username, password);
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 距离上次按返回键的时间 超过了2 秒钟时
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}