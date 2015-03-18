package com.srtp.LabSheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import com.srtp.database.myUser;
import com.srtp.key.AllKey;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Henry Huang on 2015/2/2.
 */
public class Register extends Activity {
    private  Register whole = this;
    private boolean re_register = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText userName = (EditText)findViewById(R.id.rg_get_user_name);
        final EditText passWord = (EditText)findViewById(R.id.rg_get_user_password);
        final EditText cpassWord = (EditText)findViewById(R.id.rg_get_user_confirm_password);
        final EditText userJob = (EditText)findViewById(R.id.rg_get_user_job);
        final EditText userAge = (EditText)findViewById(R.id.rg_get_user_age);

        Button btn_cancel = (Button)findViewById(R.id.rg_btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToLogin();
            }
        });

        Button btn_register = (Button)findViewById(R.id.rg_btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = userName.getText().toString();
                if(uname.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "用户名不能为空！", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                checkUname(uname);
                if(!re_register){
                    Toast toast = Toast.makeText(getApplicationContext(), "该用户已经存在！", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                String pword = passWord.getText().toString();
                if(pword.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "密码不能为空！", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                String cpwrd = cpassWord.getText().toString();
                if(!pword.equals(cpwrd)){
                    Toast toast = Toast.makeText(getApplicationContext(), "两次密码输入不同！", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                String job = userJob.getText().toString();
                int age = checkInt(userAge.getText().toString());
                if(age < 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "年龄输入格式错误！", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                myUser add = new myUser();
                add.setUname(uname);
                try {
                    add.setPassword(AllKey.toMD5(pword.getBytes()));
                    String uid = AllKey.toMD5(uname.getBytes());
                    add.setUid(uid);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(), "注册失败！", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                add.setUjob(job);
                add.setAge(age);
                add.save(whole, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast toast = Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT);
                        toast.show();
                        backToLogin();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast toast = Toast.makeText(getApplicationContext(), "注册失败！", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        setResult(RESULT_OK);
        finish();
    }

    private void backToLogin(){
        Intent intent = new Intent();
        intent.setClass(Register.this, Login.class);
        startActivityForResult(intent, 0);
    }

    public void isFail(){
        re_register = false;
    }

    public void isSucesseed(){
        re_register = true;
    }

    private int checkInt(String str){
        if(str.equals(""))
            return 30;
        try{
            int i = Integer.parseInt(str);
            return i;
        }catch(Exception err){
            return -1;
        }
    }

    private void checkUname(String uname){
        BmobQuery<myUser> bmobQuery = new BmobQuery<myUser>();
        bmobQuery = bmobQuery.addWhereEqualTo("uname", uname);
        bmobQuery.findObjects(whole, new FindListener<myUser>() {
            @Override
            public void onSuccess(List<myUser> list) {
                if(list.size() > 0) {
                    isFail();
                }
                else {
                    isSucesseed();
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.w("Error", s);
                Toast toast = Toast.makeText(getApplicationContext(), "服务器出错！", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}