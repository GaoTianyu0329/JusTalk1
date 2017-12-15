package com.example.gaotianyu.app.Activity.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gaotianyu.app.Activity.Activity.LoginActivity;
import com.example.gaotianyu.app.Activity.Activity.MessageActivity;
import com.example.gaotianyu.app.Activity.User.UserManage;
import com.example.gaotianyu.app.R;

import java.io.File;

/**
 * Created by GaoTianyu on 2017/12/1.
 */

public class GerenFragment extends Fragment {
    protected static final int CHOOSE_PICTURE = 0;
    private Bitmap mBitmap;
    ImageView imageView;
    protected static final int TAKE_PICTURE = 1;

    protected static Uri tempUri;

    private static final int CROP_SMALL_PICTURE = 2;
    private AlertDialog.Builder builder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contianer, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_geren,contianer,false);
        Button button = (Button) view.findViewById(R.id.button_exit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });
        Button button_message = (Button) view.findViewById(R.id.button_message);
        button_message.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);

            }
        });
        imageView = (ImageView) view.findViewById(R.id.imageView_touxiang);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog();
            }
        });
        return view;
    }
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setIcon(R.drawable.ic_notifications_black_24dp);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("你要点击要退出吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getActivity().getSharedPreferences("userinfo",0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
        normalDialog.setNegativeButton("关闭", null);
        // 显示
        normalDialog.show();
    }
    protected void showChoosePicDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("添加图片");

        String[] items = { "选择本地照片", "拍照" };

        builder.setNegativeButton("取消", null);

        builder.setItems(items, new DialogInterface.OnClickListener() {



            @Override

            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case CHOOSE_PICTURE: // 选择本地照片

                        Intent openAlbumIntent = new Intent(

                                Intent.ACTION_GET_CONTENT);

                        openAlbumIntent.setType("image/*");

                        //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作

                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);

                        break;

                    case TAKE_PICTURE: // 拍照

                        Intent openCameraIntent = new Intent(

                                MediaStore.ACTION_IMAGE_CAPTURE);

                        tempUri = Uri.fromFile(new File(Environment

                                .getExternalStorageDirectory(), "temp_image.jpg"));

                        // 将拍照所得的相片保存到SD卡根目录

                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);

                        startActivityForResult(openCameraIntent, TAKE_PICTURE);

                        break;

                }

            }

        });

        builder.show();

    }
    /**

     * 裁剪图片方法实现

     */

    protected void cutImage(Uri uri) {

        if (uri == null) {

            Log.i("alanjet", "The uri is not exist.");

        }

        tempUri = uri;

        Intent intent = new Intent("com.android.camera.action.CROP");

        //com.android.camera.action.CROP这个action是用来裁剪图片用的

        intent.setDataAndType(uri, "image/*");

        // 设置裁剪

        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例

        intent.putExtra("aspectX", 1);

        intent.putExtra("aspectY", 1);

        // outputX outputY 是裁剪图片宽高

        intent.putExtra("outputX", 150);

        intent.putExtra("outputY", 150);

        intent.putExtra("return-data", true);

        startActivityForResult(intent, CROP_SMALL_PICTURE);

    }

    /**

     * 保存裁剪之后的图片数据

     */

    protected void setImageToView(Intent data) {

        Bundle extras = data.getExtras();

        if (extras != null) {

            mBitmap = extras.getParcelable("data");

            //这里图片是方形的，可以用一个工具类处理成圆形（很多头像都是圆形，这种工具类网上很多不再详述）

            imageView.setImageBitmap(mBitmap);//显示图片

            //在这个地方可以写上上传该图片到服务器的代码，后期将单独写一篇这方面的博客，敬请期待...

        }

    }
}
