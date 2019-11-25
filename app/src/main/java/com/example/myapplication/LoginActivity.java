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
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
public class LoginActivity extends AppCompatActivity {
    private EditText nameEdit, passwordEdit;
    private Button btn_register, loginButtonHttpURLConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
        loginButtonHttpURLConnectionOption();
    }
    private void loginButtonHttpURLConnectionOption() {
        loginButtonHttpURLConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = HttpUtilsHttpURLConnection.BASE_URL+"/login.do";
                        JSONObject jsonObject=new JSONObject();
                        String workId = nameEdit.getText().toString();
                        String password = passwordEdit.getText().toString();
                        jsonObject.put("workid", workId);
                        jsonObject.put("password", password);
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
                                    String result = (String) json.get("result");
                                    if ("success".equals(result)) {
                                        //保存账号密码
                                        String userWorkId = nameEdit.getText().toString();
                                        String userPwd = passwordEdit.getText().toString();
                                        UserManager.getInstance().saveUserInfo(LoginActivity.this, userWorkId, userPwd);

                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        LoginActivity.this.finish();
                                        startActivity(intent);
                                    } else if ("error".equals(result)) {
                                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
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
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init() {
        nameEdit = (EditText) findViewById(R.id.nameEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        btn_register = (Button) findViewById(R.id.btn_register);
        loginButtonHttpURLConnection = (Button) findViewById(R.id.login1);
    }
}
