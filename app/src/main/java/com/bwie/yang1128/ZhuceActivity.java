package com.bwie.yang1128;

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

public class ZhuceActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 请输入手机号
     */
    private EditText mZhuTel;
    /**
     * 请输入密码
     */
    private EditText mZhuPass;
    /**
     * 立即注册
     */
    private Button mZhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        initView();
    }

    private void initView() {
        mZhuTel = (EditText) findViewById(R.id.zhu_tel);
        mZhuPass = (EditText) findViewById(R.id.zhu_pass);
        mZhuce = (Button) findViewById(R.id.Zhuce);
        mZhuce.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.Zhuce:
                String mobile = mZhuTel.getText().toString().trim();
                String password = mZhuPass.getText().toString().trim();
                //判断输入的内容是否为phone
                boolean b = isPhoneNumber(mobile);
                if (mobile.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!b) {
                    Toast.makeText(this, "手机号不合法", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(this, "密码不能少于六位数", Toast.LENGTH_SHORT).show();
                } else {
                    register(mobile, password);
                }

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
    private void register(String mobile,String password){
        RequestParams params = new RequestParams("http://120.27.23.105/user/reg");
        params.addQueryStringParameter("mobile",mobile);
        params.addQueryStringParameter("password",password);

        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //成功
                Gson gson = new Gson();
                RegistBean registBean = gson.fromJson(result, RegistBean.class);

                //如果注册成功就返回登录页面
                Toast.makeText(ZhuceActivity.this,registBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (registBean.getCode().equals("0")){
                    finish();
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
