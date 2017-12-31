package com.example.gaotianyu.app.Activity.Fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import com.example.gaotianyu.app.Activity.Activity.StartActivity;
import com.example.gaotianyu.app.Activity.Tools.PhotoTool;
import com.example.gaotianyu.app.Activity.User.UserManage;
import com.example.gaotianyu.app.R;
import com.vondear.rxtools.RxImageTool;
import com.vondear.rxtools.RxIntentTool;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.RxTool;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by GaoTianyu on 2017/12/1.
 */

public class GerenFragment extends Fragment {

    protected static final int CHOOSE_PICTURE = 0;
    private Bitmap mBitmap;
    ImageView imageView;
    protected static final int TAKE_PICTURE = 1;

    public static final int TAKE_PHOTO = 5001;

    public static final int CHOOSE_PHOTO = 5002;
    public static final int CROP_IMAGE = 5003;
    public static final int  CUT_PICTURE = 1;
    public static final int SHOW_PICTURE = 2;
    private File outputImage;


    protected static Uri imageUri;


    private AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contianer, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geren, contianer, false);
/*
        Button button = (Button) view.findViewById(R.id.button_exit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });
        Button button_message = (Button) view.findViewById(R.id.button_message);
        button_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        */
        return view;
    }

    private void showNormalDialog() {
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
                        SharedPreferences sp = getActivity().getSharedPreferences("userinfo", 0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
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


        String[] items = {"选择本地照片", "拍照"};

        builder.setNegativeButton("取消", null);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case CHOOSE_PICTURE: // 选择本地照片
                        /*
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                        } else {
                            openAlbum();
                        }
                        */
                        outputImage = new
                                File(Environment.getExternalStorageDirectory(),

                                "output_image.jpg");

                        imageUri = Uri.fromFile(outputImage);



                        try
                        {

                            if
                                    (outputImage.exists()) {

                                outputImage.delete();

                            }

                            outputImage.createNewFile();

                        } catch
                                (IOException e) {

                            e.printStackTrace();

                        }

                        Intent intent = new
                                Intent(Intent.ACTION_PICK,null);

                        //此处调用了图片选择器

                        //如果直接写intent.setDataAndType("image/*");

                        //调用的是系统图库

                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                        startActivityForResult(intent, CUT_PICTURE);



                break;

                    case TAKE_PICTURE: // 拍照
                        // 创建File对象，用于存储拍照后的图片
                        /*
                        File outputImage = new File(getActivity().getExternalCacheDir(), "output_image.jpg");
                        try {
                            if (outputImage.exists()) {
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (Build.VERSION.SDK_INT < 24) {
                            imageUri = Uri.fromFile(outputImage);
                        } else {
                            imageUri = FileProvider.getUriForFile(getActivity(), "com.example.gaotianyu.app.fileprovider", outputImage);
                        }
                        // 启动相机程序
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, TAKE_PHOTO);
                        */
                        //创建File对象，用于存储拍照后的图片

                        //将此图片存储于SD卡的根目录下

                        outputImage = new
                                File(Environment.getExternalStorageDirectory(),

                                "output_image.jpg");

                        try
                        {

                            if
                                    (outputImage.exists()) {

                                outputImage.delete();

                            }

                            outputImage.createNewFile();

                        } catch
                                (IOException e) {

                            e.printStackTrace();

                        }

                        //将File对象转换成Uri对象

                        //Uri表标识着图片的地址

                        imageUri = Uri.fromFile(outputImage);

                        //隐式调用照相机程序

                        Intent intent1 = new
                                Intent("android.media.action.IMAGE_CAPTURE");

                        //拍下的照片会被输出到output_image.jpg中去

                        intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                        //此处是使用的startActivityForResult（）

                        //因此在拍照完后悔有结果返回到onActivityResult（）中去，返回值即为TAKE_PHOTO

                        //onActivityResult（）中主要是实现图片裁剪

                        startActivityForResult(intent1, CUT_PICTURE);



                break;

                }

            }

        });

        builder.show();
    }
    /*
    private void openAlbum() {
        File outputImage = new File(getActivity().getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(getActivity(), "com.example.gaotianyu.app.fileprovider", outputImage);
        }
        Intent intent = new Intent(Intent.ACTION_PICK,null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(getActivity(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            /*case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来


                        imageUri = data.getData();
                        RxPhotoTool.cropImage(getActivity(),imageUri);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
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
                break;
            case CROP_IMAGE:
                imageUri = data.getData();
                //displayImage(RxPhotoTool.getRealFilePath(getActivity(),imageUri));
                try {
                    imageView.setImageBitmap(getBitmapFormUri(getActivity(),imageUri));
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
                */
            //此处启动裁剪程序

            case CUT_PICTURE:
            Intent intent2 = new Intent("com.android.camera.action.CROP");

            //此处注释掉的部分是针对android 4.4路径修改的一个测试

            //有兴趣的读者可以自己调试看看

            String text=imageUri.toString();

            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();

            intent2.setDataAndType(data.getData(), "image/*");

            intent2.putExtra("scale", true);

            intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            startActivityForResult(intent2, SHOW_PICTURE);



        break;

        case SHOW_PICTURE:

        if
                (resultCode == RESULT_OK) {

            try
            {

                //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来

                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()

                        .openInputStream(imageUri));

                imageView.setImageBitmap(bitmap);

            } catch
                    (FileNotFoundException e) {

                e.printStackTrace();

            }

        }
        default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
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
        uri = Uri.parse(imagePath);
        RxPhotoTool.cropImage(getActivity(),uri);// 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        uri = Uri.parse(imagePath);
        RxPhotoTool.cropImage(getActivity(),uri);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getActivity(), "failed to get image", Toast.LENGTH_SHORT).show();
        }

    }
    public static Bitmap getBitmapFormUri(Activity activity, Uri uri)throws FileNotFoundException,IOException{
        InputStream inputStream = activity.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true ;
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeStream(inputStream,null,onlyBoundsOptions);
        inputStream.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalHeight==-1)||(originalWidth==-1)){
            return null;
        }
        float h = 800f;
        float w = 480f;
        int be = 1;
        if (originalWidth>originalHeight&&originalWidth>w){
            be = (int)(originalWidth/w);
        }else if (originalWidth<originalHeight&&originalHeight>h){
            be = (int)(originalHeight/h);
        }
        if (be<=0)
            be=1;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        inputStream = activity.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,bitmapOptions);
        inputStream.close();
        return compressImage(bitmap);
    }
    /*
            * 质量压缩方法
    *
            * @param image
    * @return
            */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }








}


