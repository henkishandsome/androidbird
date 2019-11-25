package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ThirdFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ThirdFragment";
    private View view;
    private  Button btn_remark;
    private  Button sign;
    private  Button sign1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.activity_third,container,false);
//        btn_robot=(Button) view.findViewById(R.id.btn_robot);
        btn_remark=(Button) view.findViewById(R.id.btn_remark);
        sign1 = view.findViewById(R.id.btn_sign1);
        sign = view.findViewById(R.id.btn_sign);
        sign.setOnClickListener(this);
        sign1.setOnClickListener(this);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

       btn_remark.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getActivity(),MeetingActivity.class));
           }
       });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        Log.d(TAG,"druid连接池:"+ sdf.format(new Date()));
                        String url = HttpUtilsHttpURLConnection.BASE_URL + "/gosign.do";

                        for (int i = 1; i < 200; i++) {
                            String workId = String.valueOf(i);
                            String password = String.valueOf(i);
                            String name = String.valueOf(i);
                            String tel = String.valueOf(i);
                            String email = String.valueOf(i);
                            jsonObject.put("workid", workId);
                            jsonObject.put("password", password);
                            jsonObject.put("name", name);
                            jsonObject.put("tel", tel);
                            jsonObject.put("email", email);
                            String result = HttpUtilsHttpURLConnection.getContextByHttp(url, jsonObject);
                            jsonObject.clear();
                        }
                        jsonObject.put("workid", "201");
                        jsonObject.put("password", "201");
                        jsonObject.put("name", "201");
                        jsonObject.put("tel", "201");
                        jsonObject.put("email", "201");
                        String result = HttpUtilsHttpURLConnection.getContextByHttp(url, jsonObject);
                        String date2=sdf.format(new Date());
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
                                SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                                Bundle data = msg.getData();
                                String key = data.getString("result");//得到json返回的json
                                //                                   Toast.makeText(LoginActivity.this,key,Toast.LENGTH_LONG).show();
                                try {
                                    JSONObject json = JSONObject.parseObject(key);
                                    Log.d(TAG,"druid连接池:"+ sdf.format(new Date()));
                                    Boolean result = (Boolean) json.get("result");
                                    if (result) {
                                        Toast.makeText(getContext(), "OK", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), (String) json.get("errmsg"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                }).start();
                break;
            case R.id.btn_sign1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        Log.d(TAG,"JDBC服务器:"+ sdf.format(new Date()));
                        String url = "http://223.255.255.173:8080/HttpServlet/RegisterServlet";
                        Map<String, String> params = new HashMap<String, String>();
                        for (int i = 1; i < 200; i++) {
                            String workId = String.valueOf(i);
                            String password = String.valueOf(i);
                            String name = String.valueOf(i);
                            String tel = String.valueOf(i);
                            String email = String.valueOf(i);
                            params.put("workid", workId);
                            params.put("password", password);
                            params.put("name", name);
                            params.put("tel", tel);
                            params.put("email", email);
                            String result = HttpUtilsHttpURLConnection.getContextByHttptest(url, params);
                            params.clear();
                        }
                        params.put("workid", "201");
                       params.put("password", "201");
                        params.put("name", "201");
                        params.put("tel", "201");
                        params.put("email", "201");
                        String result = HttpUtilsHttpURLConnection.getContextByHttptest(url, params);
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
                                SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                                Bundle data = msg.getData();
                                String key = data.getString("result");//得到json返回的json
                                //                                   Toast.makeText(LoginActivity.this,key,Toast.LENGTH_LONG).show();
                                try {
                                    org.json.JSONObject json = new org.json.JSONObject(key);
                                    Log.d(TAG,"JDBC服务器:"+ sdf.format(new Date()));
                                    String result = (String) json.get("result");
                                    if ("registerYes".equals(result)) {
                                        Toast.makeText(getContext(), "OK", Toast.LENGTH_LONG).show();
                                    } else if ("registerNo".equals(result)){
                                        Toast.makeText(getContext(), "no", Toast.LENGTH_LONG).show();
                                    }

                                } catch (org.json.JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                }).start();
                break;
            default:
                break;
        }

    }
    private long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return returnMillis;
    }

    private String getTimeExpend(String startTime, String endTime){
        //传入字串类型 2016/06/28 08:30
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数

        return longExpend+"毫秒";
    }
}



