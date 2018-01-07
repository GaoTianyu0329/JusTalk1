package com.example.gaotianyu.app.Activity.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.gaotianyu.app.R;

public class MessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_message);
    }
}
