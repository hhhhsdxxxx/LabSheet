package com.srtp.LabSheet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;
import com.srtp.database.myUser;
import com.srtp.key.AllKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * Created by Henry Huang on 2015/2/2.
 */
public class Login extends Activity {
    private Login main = this;
    private String upassword = null;
    private String name = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        getWindow().setBackgroundDrawableResource(R.drawable.ic_launcher);
        Bmob.initialize(this, "207dd34588ba9eb84483919f25525e0d");
        final EditText username = (EditText)findViewById(R.id.lg_et_user);
        final EditText password = (EditText)findViewById(R.id.lg_pass);
        Button login = (Button)findViewById(R.id.lg_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString();
                try {
                    upassword = AllKey.toMD5(password.getText().toString().getBytes());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                BmobQuery<myUser> bmobQuery = new BmobQuery<myUser>();
                bmobQuery = bmobQuery.addWhereEqualTo("uname", uname);
                name = uname;
                bmobQuery.findObjects(main, new FindListener<myUser>() {
                    @Override
                    public void onSuccess(List<myUser> list) {
                        if(list.size() > 0) {
                            for (myUser s : list) {
                                Log.w("result", s.getPassword());
                                if (s.getPassword().equals(main.getUpassword())) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT);
                                    toast.show();
                                    Intent intent = new Intent(main, MainLoad.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("uname", name);
                                    intent.putExtra("uname", bundle);
                                    startActivity(intent);
                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "密码或用户名错误！", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        }
                        else {
                            Toast toast = Toast.makeText(getApplicationContext(), "该用户不存在！", Toast.LENGTH_SHORT);
                            toast.show();
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
        });

        Button register = (Button)findViewById(R.id.lg_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Login.this, Register.class);
                startActivityForResult(intent, 0);
            }
        });

        Button glogin = (Button)findViewById(R.id.guide_login);
        glogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Login.this, MainLoad.class);
                Bundle bundle = new Bundle();
                bundle.putString("uname", "Hello, Kitty!");
                intent.putExtra("uname", bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    public final String getUpassword(){
        return upassword;
    }
}
