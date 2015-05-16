package com.srtp.LabSheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import com.srtp.database.Blood;
import com.srtp.database.Blood_result;

import java.util.List;

/**
 * Created by Henry Huang on 2015/5/9.
 */
public class Result extends Activity {
    private String uname;
    private Result main_result = this;
    private String re;
    private double[] cp1;
    TextView et;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("result");
        re = bundle.getString("result");
        uname = bundle.getString("uname");
        et = (TextView)findViewById(R.id.r_result_text);
        et.setText(re);
        et.setMovementMethod(ScrollingMovementMethod.getInstance());

        Button back = (Button)findViewById(R.id.r_btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button upload = (Button)findViewById(R.id.r_btn_uplaod);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!uname.equals("Hello, Kitty!")) {
                    Blood_result add = new Blood_result();
                    add.setResult(re);
                    add.setUname(uname);
                    add.setTime();
                    add.save(main_result, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast toast = Toast.makeText(getApplicationContext(), "上传成功！", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast toast = Toast.makeText(getApplicationContext(), "上传失败！", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "请登录！", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        Button download = (Button)findViewById(R.id.r_btn_download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!uname.equals("Hello, Kitty!")) {
                    BmobQuery<Blood_result> query = new BmobQuery<Blood_result>();
                    query.addWhereEqualTo("uname", uname);
                    query.findObjects(main_result, new FindListener<Blood_result>() {
                        @Override
                        public void onSuccess(List<Blood_result> list) {
                            if (list.size() > 0) {
                                StringBuilder show = new StringBuilder("");
                                for(Blood_result s : list){
                                    show.append(String.format("%s:\n%s\n", s.getTime(), s.getResult()));
                                }
                                et.setText(show.toString());
                            } else
                                et.setText("没有历史记录！");
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "请登录！", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }
}