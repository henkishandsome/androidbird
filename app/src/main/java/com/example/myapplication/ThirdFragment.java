package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class ThirdFragment extends Fragment {
    private View view;
    private  Button btn_remark;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.activity_third,container,false);
//        btn_robot=(Button) view.findViewById(R.id.btn_robot);
        btn_remark=(Button) view.findViewById(R.id.btn_remark);
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
}
