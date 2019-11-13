package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class LeaderThirdFragment extends Fragment {
    private View view;
    private Button btn_meeting_create;
    private Button btn_meeting_sign_in;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_third_leader, container, false);
        //btn_robot=(Button) view.findViewById(R.id.btn_robot);
        btn_meeting_create = view.findViewById(R.id.meeting_create);
        btn_meeting_sign_in = view.findViewById(R.id.meeting_sign_in);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_meeting_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), null));
            }
        });
        btn_meeting_sign_in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), null));
            }
        });
    }
}
