package com.srtp.LabSheet;

import com.srtp.database.Blood;

import java.util.List;

/**
 * Created by Henry Huang on 2015/3/5.
 */
public class MyBloodData {
    private String uname;
    private int size;
    private String time;
    private double[] present;
    private List<Blood> data;

    public MyBloodData(String uname, List<Blood> down){
        setUname(uname);
        size = 0;
        size = down.size();
        time = null;
        if(size > 0) {
            for (Blood e : down)
                data.add(e);
            present = new double[25];
            String[] number = data.get(0).getBlood_data().split(",");
            int i = 0;
            for(String s : number) {
                present[i] = Double.parseDouble(s);
                i++;
            }
            time = data.get(0).getTime();
        }
    }

    public void setUname(String name){
        uname = name;
    }

    public int size(){
        return size;
    }

    public double[] getData(int i){
        if(i < size && i >= 0) {
            String[] number = data.get(i).getBlood_data().split(",");
            int k = 0;
            for (String s : number) {
                present[k] = Double.parseDouble(s);
                k++;
            }
            return present;
        }
        return null;
    }

    public double[] getData(){
        return size > 0 ? present : null;
    }

    public String getTime(int i){
        if(i < size && i >= 0) {
            time = data.get(i).getTime();
            return time;
        }
        return null;
    }

    public String getTime(){
        return time;
    }
}
