package com.srtp.database;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import java.util.Date;

/**
 * Created by Henry Huang on 2015/5/9.
 */
public class Blood_result extends BmobObject{
    private String uname;
    private String result;
    private String time;

    public void setUname(String name){
        uname = name;
    }

    public void setResult(String result){
        this.result = result;
    }

    public void setTime(){
        BmobDate date = new BmobDate(new Date());
        time = date.getDate();
    }

    public String getUname(){
        return uname;
    }

    public String getResult(){
        return result;
    }

    public String getTime(){
        return time;
    }
}
