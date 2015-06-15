package com.srtp.LabSheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import com.srtp.database.Blood;
import com.srtp.database.Blood_chm;

import java.util.List;

/**
 * Created by Henry Huang on 2015/2/4.
 */
public class MainLoad extends Activity {
    private BloodAnalysis_base myBlood_base = null;
    private BloodAnalysis_chm myBlood_chm = null;
    private MyBloodData myBloodData = null;
    private Spinner zbd, d_select, type;
    private MainLoad mainload = this;
    private int former = 0;
    private String uname;
    private String []item_list;
    private static Handler handle = new Handler();
    private ArrayAdapter<String> adapter_list;
    private String time;
    private EditText re_value;
    String[] blood_btype, blood_ctype;
    ArrayAdapter<String> blood_base, blood_chm;

    void initial(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("uname");
        uname = bundle.getString("uname");

        item_list = new String[1];
        item_list[0] = "当前数据";

        re_value = (EditText)findViewById(R.id.ky_value_et);
        d_select = (Spinner)findViewById(R.id.ky_former);
        zbd = (Spinner) findViewById(R.id.ky_spinner_zb);
        type = (Spinner)findViewById(R.id.ky_spinner_type);

        myBlood_base = new BloodAnalysis_base();
        myBlood_chm = new BloodAnalysis_chm();

        blood_btype = getResources().getStringArray(R.array.zb_blood_base);
        blood_ctype = getResources().getStringArray(R.array.zb_blood_chm);
        blood_base = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, blood_btype);
        blood_chm = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, blood_ctype);

        double[] test = new double[26];
        BloodValue.get_ocr("白细胞7.33 10 9/L 2 RBC红1IIJiI4 4.76 10'12/L 3 1iGB un．红蛋白151 gIL 4 MCi红细胞压积44. 1% 5 I1CV红细胞平均体积92.6 fL 6 MCI-I平均血红蛋白量31.7 pg 7 MCHC平均血红蛋白浓度342 g/L 8 PLT血小扳215 10\"9/L .9 LYMPHP淋巴细胞比率32. 10% 10 NELITP中性181胞比率57. 20% 11 MONOP单核1111胞比率9. 10%t 12 EOP I嗜酸性粒细胞比率1. 50% 13 BASOP U誓碱性粒细胞比率0. 10% 14 LYMPIIN淋巴ill'胞数2. 35 10'9/1 15 NEU'I‘中I性细月包数4. 19 核收时间：2009-03-21 08:45报告时问： 备注： \n" +
                "参考值 4--lU 3 .5一5.5 110-- 160 36--50 82-- 100 26--32 320--360 100--300 20--40 50--70 .3--8 0. 5--S 0--i 0. 8--4 \n" +
                "费别：标本编号：31 送检医师：许沛然条码编号：0300341757 标本种类：临床诊断： 序号代码项目名称结果单位参考值 16 MONON单核细胞0.67 10一O/L 0-0. 8 17 EON嗜酸性粒细胞0. 11 1O'9/L 0. 05-0. 5 18 BASON嗜碱性粒181胞0. 01 1O'O/L O--O. 1 19 RDW-CV红细胞分布宽度-CVI1.9 10'9/L 10.9-15.4 20 RDW-SD红1111胞分布宽度-SD 39. 3%37--54 21 PD%V血小板分布宽度10. 1 fL 9--17 22 MPV平均血小板体积9. 1 fL 9--13 23 PCT血小板压积0. 20%0. 17--U. 35 24 P-LCR大型血小板比率19. 1%1 3--43 25 ESR血沉8男：0-15 ", blood_btype, test);
        for(double d : test)
            Log.d("result:", String.valueOf(d));

        adapter_list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item_list);
        d_select.setAdapter(adapter_list);

        String[] types = getResources().getStringArray(R.array.zb_total);
        ArrayAdapter<String> mtypes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types);
        type.setAdapter(mtypes);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if ((adapterView.getSelectedItem()).equals("血常规")) {
                    re_value.setText("");
                    former = 0;
                    zbd.setAdapter(blood_base);
                }
                else if((adapterView.getSelectedItem()).equals("血生化")){
                    re_value.setText("");
                    former = 0;
                    zbd.setAdapter(blood_chm);
                }
                else {
                    Spinner blank = (Spinner) findViewById(R.id.ky_spinner_zb);
                    String[] mzb = getResources().getStringArray(R.array.zb_blank);
                    ArrayAdapter<String> zb = new ArrayAdapter<String>(mainload, android.R.layout.simple_list_item_1, mzb);
                    blank.setAdapter(zb);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Spinner blank = (Spinner) findViewById(R.id.ky_spinner_zb);
                String[] mzb = getResources().getStringArray(R.array.zb_blank);
                ArrayAdapter<String> zb = new ArrayAdapter<String>(mainload, android.R.layout.simple_list_item_1, mzb);
                blank.setAdapter(zb);
            }
        });

        zbd.setAdapter(blood_base);
        zbd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String num = re_value.getText().toString();
                if((type.getSelectedItem()).equals("血常规")) {
                    if (!num.equals(""))
                        myBlood_base.setValue(former, Double.parseDouble(num));
                    if (myBlood_base.getValue(adapterView.getSelectedItemPosition()) != -1)
                        re_value.setText(String.valueOf(myBlood_base.getValue(adapterView.getSelectedItemPosition())));
                    else
                        re_value.setText("");
                }
                else if(type.getSelectedItem().equals("血生化")){
                    if (!num.equals(""))
                        myBlood_chm.setValue(former, Double.parseDouble(num));
                    if(myBlood_chm.getValue(adapterView.getSelectedItemPosition()) != -1)
                        re_value.setText(String.valueOf(myBlood_chm.getValue(adapterView.getSelectedItemPosition())));
                    else
                        re_value.setText("");
                }
                former = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast toast = Toast.makeText(getApplicationContext(), "请选择相应的项目！", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainload);
        initial();
        d_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time = (String)adapterView.getSelectedItem();
                if(!time.equals("当前数据")){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handle.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(type.getSelectedItem().equals("血常规")) {
                                        BmobQuery<Blood> query = new BmobQuery<Blood>();
                                        query.addWhereEqualTo("uname", uname);
                                        query.addWhereEqualTo("time", time);
                                        query.findObjects(mainload, new FindListener<Blood>() {
                                            @Override
                                            public void onSuccess(List<Blood> list) {
                                                if (list.size() > 0) {
                                                    for (Blood s : list)
                                                        myBlood_base.setbyString(s.getBlood_data());
                                                }
                                                if (myBlood_base.getValue(zbd.getSelectedItemPosition()) != -1)
                                                    re_value.setText(String.valueOf(myBlood_base.getValue(zbd.getSelectedItemPosition())));
                                                else
                                                    re_value.setText("");
                                            }

                                            @Override
                                            public void onError(int i, String s) {
                                                Toast toast = Toast.makeText(getApplicationContext(), "载入失败！", Toast.LENGTH_SHORT);
                                                toast.show();
                                            }
                                        });
                                    }
                                    else if(type.getSelectedItem().equals("血生化")){
                                        BmobQuery<Blood_chm> query = new BmobQuery<Blood_chm>();
                                        query.addWhereEqualTo("uname", uname);
                                        query.addWhereEqualTo("time", time);
                                        query.findObjects(mainload, new FindListener<Blood_chm>() {
                                            @Override
                                            public void onSuccess(List<Blood_chm> list) {
                                                Log.d("len", String.valueOf(list.size()));
                                                if (list.size() > 0) {
                                                    for (Blood_chm s : list)
                                                        myBlood_chm.setbyString(s.getBlood_data());
                                                }
                                                if (myBlood_chm.getValue(zbd.getSelectedItemPosition()) != -1)
                                                    re_value.setText(String.valueOf(myBlood_chm.getValue(zbd.getSelectedItemPosition())));
                                                else
                                                    re_value.setText("");
                                            }

                                            @Override
                                            public void onError(int i, String s) {
                                                Toast toast = Toast.makeText(getApplicationContext(), "载入失败！", Toast.LENGTH_SHORT);
                                                toast.show();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }).start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ImageButton wholeWord = (ImageButton)findViewById(R.id.ml_get_whole_word);
        ImageButton takePhoto = (ImageButton)findViewById(R.id.ml_get_photo);
        ImageButton showAnalysis = (ImageButton)findViewById(R.id.ml_make_analysis);

        showAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handle.post(new Runnable() {
                            @Override
                            public void run() {
                                if(!uname.equals("Hello, Kitty!")) {
                                    if(type.getSelectedItem().equals("血常规")) {
                                        BmobQuery<Blood> query = new BmobQuery<Blood>();
                                        query.addWhereEqualTo("uname", uname);
                                        query.findObjects(mainload, new FindListener<Blood>() {
                                            @Override
                                            public void onSuccess(List<Blood> list) {
                                                Log.d("l", String.valueOf(list.size()));
                                                if (list.size() > 0) {
                                                    item_list = new String[list.size() + 1];
                                                    int i = 0;
                                                    for (Blood s : list)
                                                        item_list[++i] = s.getTime();
                                                } else
                                                    item_list = new String[1];
                                                item_list[0] = "当前数据";
                                                Log.d("debug", item_list[0]);
                                            }

                                            @Override
                                            public void onError(int i, String s) {
                                                item_list = new String[1];
                                                item_list[0] = "当前数据";
                                                Toast toast = Toast.makeText(getApplicationContext(), "载入失败！", Toast.LENGTH_SHORT);
                                                toast.show();
                                            }
                                        });
                                    }
                                    else if(type.getSelectedItem().equals("血生化")){
                                        BmobQuery<Blood_chm> query = new BmobQuery<Blood_chm>();
                                        query.addWhereEqualTo("uname", uname);
                                        query.findObjects(mainload, new FindListener<Blood_chm>() {
                                            @Override
                                            public void onSuccess(List<Blood_chm> list) {
                                                Log.d("l", String.valueOf(list.size()));
                                                if (list.size() > 0) {
                                                    item_list = new String[list.size() + 1];
                                                    int i = 0;
                                                    for (Blood_chm s : list)
                                                        item_list[++i] = s.getTime();
                                                } else
                                                    item_list = new String[1];
                                                item_list[0] = "当前数据";
                                                Log.d("debug", item_list[0]);
                                            }

                                            @Override
                                            public void onError(int i, String s) {
                                                item_list = new String[1];
                                                item_list[0] = "当前数据";
                                                Toast toast = Toast.makeText(getApplicationContext(), "载入失败！", Toast.LENGTH_SHORT);
                                                toast.show();
                                            }
                                        });
                                    }
                                }
                                else{
                                    item_list = new String[1];
                                    item_list[0] = "当前数据";
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainload, android.R.layout.simple_list_item_1, item_list);
                                d_select.setAdapter(adapter);
                            }
                        });
                    }
                }).start();
            }
        });

        Button clear = (Button)findViewById(R.id.ky_btn_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                re_value.setText("");
                myBlood_base.clear();
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
                        myBlood_base.setValue(k, Double.parseDouble(num));
                    k = ((k+1 == 26) ? 0 : k+1);
                    zbd.setSelection(k);
                    if(myBlood_base.getValue(k) != -1)
                        re_value.setText(String.valueOf(myBlood_base.getValue(k)));
                    else
                        re_value.setText("");
                }
                else if(type.getSelectedItem().equals("血生化")){
                    String num = re_value.getText().toString();
                    if(!num.equals(""))
                        myBlood_chm.setValue(k, Double.parseDouble(num));
                    k = ((k+1 == 27) ? 0 : k+1);
                    zbd.setSelection(k);
                    if(myBlood_chm.getValue(k) != -1)
                        re_value.setText(String.valueOf(myBlood_chm.getValue(k)));
                    else
                        re_value.setText("");
                }
            }
        });

        Button upload = (Button)findViewById(R.id.ml_btn_upl);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!uname.equals("Hello, Kitty!")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handle.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(type.getSelectedItem().equals("血常规")) {
                                        Blood add = new Blood();
                                        StringBuilder data = new StringBuilder("");
                                        for (int i = 0; i < 26; i++) {
                                            data.append(myBlood_base.getValue(i));
                                            data.append(",");
                                        }
                                        data.deleteCharAt(data.length() - 1);
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
                                    else if(type.getSelectedItem().equals("血生化")){
                                        Blood_chm add = new Blood_chm();
                                        StringBuilder data = new StringBuilder("");
                                        for (int i = 0; i < 27; i++) {
                                            data.append(myBlood_chm.getValue(i));
                                            data.append(",");
                                        }
                                        data.deleteCharAt(data.length() - 1);
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
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "请登录！", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        wholeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainload, Result.class);
                String str = null;
                if(type.getSelectedItem().equals("血常规"))
                    str = myBlood_base.TellDisease();
                else if(type.getSelectedItem().equals("血生化"))
                    str = myBlood_chm.TellDisease();
                Bundle bundle = new Bundle();
                Log.d("result", str);
                bundle.putString("result", str);
                bundle.putString("uname", uname);
                intent.putExtra("result", bundle);
                startActivity(intent);
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainLoad.this, PhotoOCR.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}