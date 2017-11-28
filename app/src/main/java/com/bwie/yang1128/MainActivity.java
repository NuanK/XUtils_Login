package com.bwie.yang1128;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 请输入手机号
     */
    private EditText mEdTel;
    /**
     * 请输入密码
     */
    private EditText mEdPass;
    /**
     * 登录
     */
    private Button mBtnLogin;
    /**
     * 注册
     */
    private Button mBtnZhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEdTel = (EditText) findViewById(R.id.ed_tel);
        mEdPass = (EditText) findViewById(R.id.ed_pass);
        mBtnLogin = (Button) findViewById(R.id.btn_Login);
        mBtnLogin.setOnClickListener(this);
        mBtnZhuce = (Button) findViewById(R.id.btn_Zhuce);
        mBtnZhuce.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_Login:
                String mobile = mEdTel.getText().toString();
                String password = mEdPass.getText().toString();
                //判断输入的内容是否为phone
                boolean b = isPhoneNumber(mobile);

                if (mobile.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!b) {
                    Toast.makeText(MainActivity.this, "手机号不合法", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(MainActivity.this, "密码不能少于六位数", Toast.LENGTH_SHORT).show();
                } else {

                    login(mobile, password);
                }


                break;
            case R.id.btn_Zhuce:
                Intent intent_zhuce = new Intent(this, ZhuceActivity.class);
                startActivity(intent_zhuce);
                break;
        }
    }

    private boolean isPhoneNumber(String phoneStr) {
        //定义电话格式的正则表达式
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        //设定查看模式
        Pattern p = Pattern.compile(regex);
        //判断Str是否匹配，返回匹配结果
        Matcher m = p.matcher(phoneStr);
        return m.find();
    }

    private void login(String mobile, String password) {

        RequestParams params = new RequestParams("http://120.27.23.105/user/login");
        params.addQueryStringParameter("mobile", mobile);
        params.addQueryStringParameter("password", password);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //成功
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(result, LoginBean.class);
                Toast.makeText(MainActivity.this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (loginBean.getCode().equals("0")) {
                    Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                    startActivity(intent);
                }
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }
}