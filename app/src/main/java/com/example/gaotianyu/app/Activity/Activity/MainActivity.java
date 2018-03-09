package com.example.gaotianyu.app.Activity.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaotianyu.app.Activity.Fragment.LifeFragment;
import com.example.gaotianyu.app.Activity.Fragment.SaishiFragment;
import com.example.gaotianyu.app.Activity.Fragment.StudyFragment;
import com.example.gaotianyu.app.Activity.Tools.ButtonSlop;
import com.example.gaotianyu.app.R;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri imageUri;
    private SaishiFragment saishiFragment;
    private LifeFragment lifeFragment;
    private StudyFragment studyFragment;
    private int nowFragment = 0;
    private final int SAISHIFRAGMENT = 0;
    private final int LIFEFRAGMENT = 1;
    private final int STUDYFRAGMNET = 2;
    private ImageView button_saishi;
    private ImageView button_life;
    private ImageView button_study;
    private ImageView touxiang;
    private TextView button_exit;
    private LinearLayout myCollection;
    View view;


private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.menu);
        }


/*
        ImageView button_message = (ImageView) findViewById(R.id.button_message);
        button_message.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(MainActivity.this,MessageActivity.class);
                startActivity(intent1);
            }
        });
        */
        button_saishi = (ImageView) findViewById(R.id.button_saishi);
        button_saishi.setOnClickListener(this);
        button_life = (ImageView) findViewById(R.id.button_life);
        button_life.setOnClickListener(this);
        button_study = (ImageView) findViewById(R.id.button_study);
        button_study.setOnClickListener(this);

        /*ImageView button_geren = (ImageView) findViewById(R.id.button_geren);
        button_geren.setOnClickListener(this);
        button_geren.callOnClick();
        */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (saishiFragment == null) {
            saishiFragment = new SaishiFragment();
            transaction.add(R.id.fragment_layout, saishiFragment);
        } else {
            transaction.show(saishiFragment);
        }
        transaction.commit();
        button_saishi.setImageResource(R.mipmap.click1);
    }

        @Override
        public void onResume() {
            super.onResume();

            button_exit = (TextView) findViewById(R.id.exit);
            button_exit.setOnClickListener(this);
            myCollection = (LinearLayout) findViewById(R.id.mycollection);
            myCollection.setOnClickListener(this);
            touxiang = (ImageView) findViewById(R.id.imageView_touxiang);
            touxiang.setOnClickListener(this);


        }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }
    @Override
    public void onClick(View v){
        //TextView title = (TextView)findViewById(R.id.text_title);
        switch (v.getId()){
            case R.id.button_saishi:
                if (nowFragment == SAISHIFRAGMENT){
                    return;
                }
                //title.setText("赛事");
                if (ButtonSlop.check(R.id.button_saishi)) {
                    //Toast.makeText(PostActivity.this,"请稍后尝试",Toast.LENGTH_SHORT).show();
                    return;
                }
                showFragment(SAISHIFRAGMENT);
                button_saishi.setImageResource(R.mipmap.click1);
                //replaceFragment(new SaishiFragment());
                break;
            case R.id.button_life:
                if (nowFragment == LIFEFRAGMENT){
                    return;
                }
                if (ButtonSlop.check(R.id.button_life)) {
                    //Toast.makeText(PostActivity.this,"请稍后尝试",Toast.LENGTH_SHORT).show();
                    return;
                }
                showFragment(LIFEFRAGMENT);
                button_life.setImageResource(R.mipmap.click2);
                //title.setText("生活助手");
                //replaceFragment(new LifeFragment());
                break;
            case R.id.button_study:
                if (nowFragment == STUDYFRAGMNET){
                    return;
                }
                if (ButtonSlop.check(R.id.button_study)) {
                    //Toast.makeText(PostActivity.this,"请稍后尝试",Toast.LENGTH_SHORT).show();
                    return;
                }
                showFragment(STUDYFRAGMNET);
                button_study.setImageResource(R.mipmap.click3);
                //title.setText("学术交流");
                //replaceFragment(new StudyFragment());
                break;
            case R.id.exit:
                clear();
                break;
            case R.id.mycollection:
                Intent intent = new Intent(MainActivity.this,CollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView_touxiang:
                showChoosePicDialog();
                break;

                /*
            case R.id.button_geren:
                //title.setText("个人中心");
                replaceFragment(new GerenFragment());


           break;
           */
            default:
                break;
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.button_message:
                Intent intent1 = new Intent(MainActivity.this,MessageActivity.class);
                startActivity(intent1);
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;


        }
        return true;
    }
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        choosePicture();
                        break;
                    case TAKE_PICTURE: // 拍照
                        takePicture();
                        break;
                }
            }
        });
        builder.create().show();
    }
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    protected void takePicture(){
        File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(MainActivity.this,
                    "com.example.gaotianyu.app.fileprovider",outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PICTURE);
    }
    protected void choosePicture(){
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent,CHOOSE_PICTURE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent,CHOOSE_PICTURE);
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case CHOOSE_PICTURE: //从相册图片后返回的uri
                    if (resultCode == RESULT_OK) {
                        // 判断手机系统版本号
                        if (Build.VERSION.SDK_INT >= 19) {
                            // 4.4及以上系统使用这个方法处理图片
                            handleImageOnKitKat(data);
                        } else {
                            // 4.4以下系统使用这个方法处理图片
                            handleImageBeforeKitKat(data);
                        }
                    }
                    //启动裁剪

                    break;
                case TAKE_PICTURE: //相机返回的 uri
                    //启动裁剪
                    if (resultCode == RESULT_OK) {
                        try {
                            // 将拍摄的照片显示出来
                            Intent intent = new Intent("com.android.camera.action.CROP");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            }
                            intent.setDataAndType(imageUri, "image/*");
                            // crop为true是设置在开启的intent中设置显示的view可以剪裁
                            intent.putExtra("crop", "true");

                            intent.putExtra("scale", true);

                            // aspectX aspectY 是宽高的比例
                            intent.putExtra("aspectX", 1);
                            intent.putExtra("aspectY", 1);

                            // outputX,outputY 是剪裁图片的宽高
                            intent.putExtra("outputX", 300);
                            intent.putExtra("outputY", 300);
                            intent.putExtra("circleCrop",true);
                            intent.putExtra("return-data", true);
                            intent.putExtra("noFaceDetection", true);

                            File out = new File(getExternalCacheDir(),"output_image.jpg");
                            if (!out.getParentFile().exists()) {
                                out.getParentFile().mkdirs();
                            }
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
                            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                            startActivityForResult(intent,CROP_SMALL_PICTURE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case CROP_SMALL_PICTURE:
                    setImageToView(data);
                    break;
            }
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        Intent intent = invokeSystemCrop(uri,imagePath);
        startActivityForResult(intent,CROP_SMALL_PICTURE);// 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        Intent intent = invokeSystemCrop(uri,imagePath);
        startActivityForResult(intent,CROP_SMALL_PICTURE);

    }
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    public Intent invokeSystemCrop(Uri uri,String imagePath) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        intent.putExtra("scale", true);

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("circleCrop",true);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);

        File out = new File(imagePath);
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        return intent;
    }
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");

            touxiang.setImageBitmap(photo);

        }
    }




    private void showFragment(int x){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        orignalImage();
        switch (x){
            case SAISHIFRAGMENT:
                if (saishiFragment == null){
                    saishiFragment = new SaishiFragment();
                    transaction.add(R.id.fragment_layout, saishiFragment);
                }else {
                    transaction.show(saishiFragment);
                }
                break;
            case LIFEFRAGMENT:
                if (lifeFragment == null){
                    lifeFragment = new LifeFragment();
                    transaction.add(R.id.fragment_layout, lifeFragment);
                }else {
                    transaction.show(lifeFragment);
                }
                break;
            case STUDYFRAGMNET:
                if (studyFragment == null){
                    studyFragment = new StudyFragment();
                    transaction.add(R.id.fragment_layout, studyFragment);
                }else {
                    transaction.show(studyFragment);
                }
                break;
        }
        transaction.commit();
        nowFragment = x;

    }
    private void hideFragment(FragmentTransaction transaction){
        if(saishiFragment!=null){
            transaction.hide(saishiFragment);
        }
        if (lifeFragment!=null){
            transaction.hide(lifeFragment);
        }
        if (studyFragment!=null){
            transaction.hide(studyFragment);
        }
    }
    private void orignalImage(){
        button_saishi.setImageResource(R.mipmap.saishi);
        button_life.setImageResource(R.mipmap.life);
        button_study.setImageResource(R.mipmap.study);

    }
    private void clear(){
        SharedPreferences sp = MainActivity.this.getSharedPreferences("userinfo",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
