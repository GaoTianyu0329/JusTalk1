package com.example.gaotianyu.app.Activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gaotianyu.app.R;

/**
 * Created by GaoTianyu on 2017/12/1.
 */

public class StudyFragment extends Fragment {@Override
public View onCreateView(LayoutInflater inflater, ViewGroup contianer, Bundle savedInstanceState){
    View view = inflater.inflate(R.layout.fragment_study,contianer,false);
    return view;
}
}
