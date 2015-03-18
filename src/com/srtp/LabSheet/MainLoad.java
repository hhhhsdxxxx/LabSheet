package com.srtp.LabSheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import cn.bmob.v3.listener.SaveListener;
import com.srtp.database.Blood;

/**
 * Created by Henry Huang on 2015/2/4.
 */
public class MainLoad extends Activity {
    private BloodValue myBlood = null;
    private MyBloodData myBloodData = null;
    private Spinner zbd;
    private MainLoad mainload = this;
    private int former = 0;
    private String uname;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainload);

        final EditText re_value = (EditText)findViewById(R.id.ky_value_et);

        ImageButton wholeWord = (ImageButton)findViewById(R.id.ml_get_whole_word);
        ImageButton takePhoto = (ImageButton)findViewById(R.id.ml_get_photo);
        ImageButton showAnalysis = (ImageButton)findViewById(R.id.ml_make_analysis);
        ImageButton showResult = (ImageButton)findViewById(R.id.ml_show_result);

        final Spinner type = (Spinner)findViewById(R.id.ky_spinner_type);
        String[] mTypes = getResources().getStringArray(R.array.zb_total);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTypes);
        type.setAdapter(adapter);
        myBlood = new BloodValue();
        myBlood.Initial();
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if((adapterView.getSelectedItem()).equals("血常规")){
                    if(myBlood != null) {
                        myBlood = new BloodValue();
                        myBlood.Initial();
                    }
                    zbd = (Spinner)findViewById(R.id.ky_spinner_zb);
                    String[] mzb = getResources().getStringArray(R.array.zb_blood);
                    ArrayAdapter<String> zb = new ArrayAdapter<String>(mainload, android.R.layout.simple_list_item_1, mzb);
                    zbd.setAdapter(zb);
                    zbd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String num = re_value.getText().toString();
                            if(!num.equals(""))
                                myBlood.setValue(former, Double.parseDouble(num));
                            if(myBlood.getValue(adapterView.getSelectedItemPosition()) != -1)
                                re_value.setText(String.valueOf(myBlood.getValue(adapterView.getSelectedItemPosition())));
                            else
                                re_value.setText("");
                            former = adapterView.getSelectedItemPosition();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            Toast toast = Toast.makeText(getApplicationContext(), "请选择相应的项目！", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
                else {
                    Spinner blank = (Spinner)findViewById(R.id.ky_spinner_zb);
                    String[] mzb = getResources().getStringArray(R.array.zb_blank);
                    ArrayAdapter<String> zb = new ArrayAdapter<String>(mainload, android.R.layout.simple_list_item_1, mzb);
                    blank.setAdapter(zb);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Spinner blank = (Spinner)findViewById(R.id.ky_spinner_zb);
                String[] mzb = getResources().getStringArray(R.array.zb_blank);
                ArrayAdapter<String> zb = new ArrayAdapter<String>(mainload, android.R.layout.simple_list_item_1, mzb);
                blank.setAdapter(zb);
            }
        });

        Button clear = (Button)findViewById(R.id.ky_btn_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                re_value.setText("");
                myBlood.clear();
            }
        });

        Button enter = (Button)findViewById(R.id.ky_btn_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int k = zbd.getSelectedItemPosition();
                if(type.getSelectedItem().equals("血常规")) {
                    String num = re_value.getText().toString();
                    if(!num.equals(""))
                        myBlood.setValue(k, Double.parseDouble(num));
                    k = ((k+1 == 25) ? 0 : k+1);
                    zbd.setSelection(k);
                }
                if(myBlood.getValue(k) != -1)
                    re_value.setText(String.valueOf(myBlood.getValue(k)));
                else
                    re_value.setText("");
            }
        });

        Button upload = (Button)findViewById(R.id.ml_btn_upl);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Bundle bundle = intent.getBundleExtra("uname");
                uname = bundle.getString("uname");
                if(!uname.equals("Hello, Kitty!")){
                    Blood add = new Blood();
                    StringBuilder data = new StringBuilder("");
                    for(int i = 0; i < 25; i++) {
                        data.append(myBlood.getValue(i));
                        data.append(",");
                    }
                    data.deleteCharAt(data.length()-1);
                    add.setUname(uname);
                    add.setData(data.toString());
                    add.setTime();
                    add.save(mainload, new SaveListener() {
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
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "请登录！", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        wholeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
            }
        });
    }
}