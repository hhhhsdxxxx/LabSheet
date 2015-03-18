package com.srtp.database;


import cn.bmob.v3.BmobObject;


/**
 * Created by Henry Huang on 2015/2/2.
 */
public class myUser extends BmobObject{
    private String uid;
    private String uname;
    private String password;
    private String ujob;
    private int age;

    public void setUid(String uid){
        this.uid = uid;
    }

    public void setUname(String uname){
        this.uname = uname;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setUjob(String ujob){
        this.ujob = ujob;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getUid(){
        return uid;
    }

    public String getUname(){
        return uname;
    }

    public String getPassword(){
        return password;
    }

    public String getUjob(){
        return ujob;
    }

    public int getAge(){
        return age;
    }
}
