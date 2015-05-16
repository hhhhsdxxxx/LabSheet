package com.srtp.database;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

import java.util.Date;

/**
 * Created by Henry on 2015/5/11.
 */
public class Blood_chm extends BmobObject {
    private String uname;
    private String data;
    private String time;

    public void setUname(String uname){
        this.uname = uname;
    }

    public void setData(String blood_data){
        data = blood_data;
    }

    public void setTime(){
        BmobDate date = new BmobDate(new Date());
        time = date.getDate();
    }

    public String getUname(){
        return uname;
    }

    public String getBlood_data(){
        return data;
    }

    public String getTime(){
        return time;
    }
}
