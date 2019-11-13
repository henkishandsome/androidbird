package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {
    String password;
    final Data app = (Data)getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        UserInfo userInfo = UserManager.getInstance().getUserInfo(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = HttpUtilsHttpURLConnection.BASE_URL + "/UserInfo";
                Map<String, String> params = new HashMap<String, String>();
                //取出当前用户账号
                if (UserManager.getInstance().hasUserInfo(WelcomeActivity.this)) {
                    SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
                    String workId = sp.getString("WorkId", "");
                    password = sp.getString("password","");
                    params.put("workId", workId);
                }

                String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);

                Message msg = new Message();
                msg.what = 0x12;
                Bundle data = new Bundle();
                data.putString("result", result);
                data.putString("password",password);
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
                            JSONObject json = new JSONObject(key);
                            String result = (String) json.get("result");
                            String workId = (String) json.get("workId");
                            String password = json.getString("password");
                            int status = json.getInt("status");
                            if ("success".equals(result) & password.equals(data.getString("password"))) {
                                app.setStatus(status);
                                if (status == 0) {
                                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }).start();
    }

}


