package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_workId,et_password,et_name,et_tel,et_email;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        loginButtonHttpURLConnectionOption();
    }

    private void loginButtonHttpURLConnectionOption() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = HttpUtilsHttpURLConnection.BASE_URL+"/gosign.do";
                        JSONObject jsonObject = new JSONObject();
                        String workId = et_workId.getText().toString();
                        String password = et_password.getText().toString();
                        String name=et_name.getText().toString();
                        String tel=et_tel.getText().toString();
                        String email=et_email.getText().toString();
                        jsonObject.put("workid", workId);
                        jsonObject.put("password", password);
                        jsonObject.put("name",name);
                        jsonObject.put("tel",tel);
                        jsonObject.put("email",email);
                        String result = HttpUtilsHttpURLConnection.getContextByHttp(url, jsonObject);
                        Message msg = new Message();
                        msg.what = 0x12;
                        Bundle data = new Bundle();
                        data.putString("result", result);
                        msg.setData(data);
                        hander.sendMessage(msg);
                    }
                    Handler hander = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg.what == 0x12) {
                                Bundle data = msg.getData();
                                String key = data.getString("result");//得到json返回的json
                                //                                   Toast.makeText(LoginActivity.this,key,Toast.LENGTH_LONG).show();
                                try {
                                    JSONObject json = JSONObject.parseObject(key);
                                    Boolean result = (Boolean) json.get("result");
                                    if (result) {
                                        Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, (String)json.get("errmsg"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                }).start();
            }
        });
    }

    private void init() {
        et_workId = (EditText) findViewById(R.id.et_workId);
        et_password = (EditText) findViewById(R.id.et_password);
        et_name=(EditText) findViewById(R.id.et_name);
        et_tel=(EditText) findViewById(R.id.et_tel);
        et_email=(EditText) findViewById(R.id.et_email);
        btn_submit=(Button) findViewById(R.id.btn_submit);
    }

}
