package com.example.gaotianyu.app.Activity;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;

import com.mob.MobApplication;


/**
 * Created by GaoTianyu on 2017/12/21.
 */

public class MyApplication extends Application {
    private static Context context;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate(){
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        context = getApplicationContext();
        //RxTool.init(context);

    }
    public static Context getContext(){
        return context;
    }
}
