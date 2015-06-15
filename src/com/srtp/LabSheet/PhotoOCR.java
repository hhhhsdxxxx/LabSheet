package com.srtp.LabSheet;

import android.app.Activity;

import java.io.File;
import java.io.FileNotFoundException;


import android.widget.*;
import com.googlecode.tesseract.android.TessBaseAPI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

public class PhotoOCR extends Activity {
    private static final int PHOTO_CAPTURE = 0x11;// ����
    private static final int PHOTO_RESULT = 0x12;// ���
    private static String IMG_PATH = getSDPath() + java.io.File.separator
            + "ocrtest";

    private static String LANGUAGE = "chi_sim";
    private static TextView tvResult;
    private static Bitmap bitmapSelected;
    private static Bitmap bitmapTreated;
    private static ImageView ivSelected;
    private static ImageView ivTreated;
    private static String textResult;
    private static final int SHOWRESULT = 0x101;
    private static final int SHOWTREATEDIMG = 0x102;

    public static Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOWRESULT:
                    if (textResult.equals(""))
                        tvResult.setText("未识别");
                    else
                        tvResult.setText(textResult);
                    break;
                case SHOWTREATEDIMG:
                    tvResult.setText("识别中......");
                    showPicture(ivTreated, bitmapTreated);
                    break;
            }
            super.handleMessage(msg);
        }

    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoocr);

        //
        File path = new File(IMG_PATH);
        if (!path.exists()) {
            path.mkdirs();
        }

        Button takephoto = (Button)findViewById(R.id.take);
        Button choosephoto = (Button)findViewById(R.id.choose);
        tvResult = (TextView) findViewById(R.id.text);
        ivSelected = (ImageView) findViewById(R.id.iv_selected);
        ivTreated = (ImageView) findViewById(R.id.iv_treated);

        takephoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(IMG_PATH, "temp.jpg")));
                PhotoOCR.this.startActivityForResult(intent, PHOTO_CAPTURE);
            }
        });

        choosephoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("scale", true);
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(IMG_PATH, "temp_cropped.jpg")));
                intent.putExtra("outputFormat",
                        Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("noFaceDetection", true); // no face detection
                startActivityForResult(intent, PHOTO_RESULT);
            }
        });

    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// ��ȡ���Ŀ¼
        }
        return sdDir.toString();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_CANCELED)
            return;

        if (requestCode == PHOTO_CAPTURE) {
            //tvResult.setText("abc");
            startPhotoCrop(Uri.fromFile(new File(IMG_PATH, "temp.jpg")));
        }

        // ������
        if (requestCode == PHOTO_RESULT) {
            bitmapSelected = decodeUriAsBitmap(Uri.fromFile(new File(IMG_PATH,
                    "temp_cropped.jpg")));
            tvResult.setText("......");
            tvResult.setText("......");
            //
            showPicture(ivSelected, bitmapSelected);

            //
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bitmapTreated = ImgPretreatment
                            .doPretreatment(bitmapSelected);
                    Message msg = new Message();
                    msg.what = SHOWTREATEDIMG;
                    myHandler.sendMessage(msg);
                    textResult = doOcr(bitmapTreated, LANGUAGE);
                    Message msg2 = new Message();
                    msg2.what = SHOWRESULT;
                    myHandler.sendMessage(msg2);
                }

            }).start();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(IMG_PATH, "temp_cropped.jpg")));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, PHOTO_RESULT);
    }

    /**
     *
     *
     * @param uri
     * @return
     */
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static void showPicture(ImageView iv, Bitmap bmp){
        iv.setImageBitmap(bmp);
    }

    /**
     *
     *
     * @param bitmap
     *
     * @param language
     *
     * @return 字符串
     */
    public String doOcr(Bitmap bitmap, String language) {
        TessBaseAPI baseApi = new TessBaseAPI();


        baseApi.init(getSDPath()+"/Download/tessdata/", language);

        //
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        baseApi.setImage(bitmap);

        String text = baseApi.getUTF8Text();

        baseApi.clear();
        baseApi.end();

        return text;
    }

}