package com.srtp.LabSheet;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by Henry Huang on 2015/3/4.
 */
public class BloodValue {
    final static int WBC    = 0x00000001;
    final static int RBC    = 0x00000002;
    final static int HGB    = 0x00000004;
    final static int HCT    = 0x00000008;
    final static int MCV    = 0x00000010;
    final static int MCH    = 0x00000020;
    final static int MCHC   = 0x00000040;
    final static int PLT    = 0x00000080;
    final static int LYMPHP = 0x00000100;
    final static int NEUTP  = 0x00000200;
    final static int MONOP  = 0x00000400;
    final static int EOP    = 0x00000800;
    final static int BASOP  = 0x00001000;
    final static int LYMPHN = 0x00002000;
    final static int NEUT   = 0x00004000;
    final static int MONON  = 0x00008000;
    final static int EON    = 0x00010000;
    final static int BASON  = 0x00020000;
    final static int RDW_CV = 0x00040000;
    final static int RDW_SD = 0x00080000;
    final static int PDW    = 0x00100000;
    final static int MPV    = 0x00200000;
    final static int PCT    = 0x00400000;
    final static int P_LCR  = 0x00800000;
    final static int ESR    = 0x01000000;
    final static int RC     = 0x02000000;
    private final static double[] bottom =
            { 4, 3.5, 110, 36,  82, 26, 320, 100, 20, 50, 3, 0.5, 0.0, 0.8, -1, 0.0, 0.05, 0.0, 10.9, 37,  9,  9, 0.17, 13, 0.0, 0.5};
    private final static double[] top =
            {10, 5.5, 160, 50, 100, 32, 360, 300, 40, 70, 8, 5.0, 1.0, 4.0, -1, 0.8,  0.5, 0.1, 15.4, 54, 17, 13, 0.35, 43,  15, 1.5};

    protected double[] value;
    protected double[] diff;

    public BloodValue() {
        value = new double[26];
        diff = new double[26];
        for (int i = 0; i < 26; i++)
            value[i] = diff[i] = -1;
    }

    protected final double differ(int i, double zb_value){
        double re = zb_value - top[i];
        if(re > 0)
            return re;
        re = zb_value - bottom[i];
        if(re < 0)
            return re;
        return 0.0;
    }

    public void setValue(int i, double n){
        if(i > 25 || i < 0)
            return;
        value[i] = n;
        diff[i] = differ(i, n);
    }

    public boolean setbyString(String in){
        String []snum = in.split(",");
        //double []dnum = new double[26];
        int i = 0;
        if(snum.length == 26) {
            for (String s : snum)
                setValue(i++, Double.parseDouble(s));
            return true;
        }
        else
            return false;
    }

    public double getValue(int i){
        return value[i];
    }

    public void clear(){
        for(int i = 0; i < 26; i++)
            value[i] = diff[i] = -1;
    }

    public static void get_ocr(String target, String []name, double[] sourse){
        int i = 0;
        for(String s : name){
            int k = target.indexOf(s);
            if(k != -1){
                char ch;
                int j = s.length();
                ch=target.charAt(k+j);
                while(ch<'0' || ch > '9') {
                    j++;
                    ch = target.charAt(k+j);
                }
                if(j > 8 || (j>=5 && s.equals("红细胞"))) {
                    sourse[i++] = -1;
                    continue;
                }
                int end = j+1;
                ch=target.charAt(k+end);
                while((ch>='0' && ch <= '9') || ch=='.'){
                    end++;
                    ch = target.charAt(k+end);
                }
                String num = target.substring(k+j, k+end);
                double re;
                try{
                    re = Double.parseDouble(num);
                    sourse[i] = re;
                }
                catch (Exception err){
                    sourse[i] = -1;
                }
            }
            else{
                sourse[i] = -1;
            }
            i++;
        }
    }

    public void copy(double[] s) {
        if (s.length == 26) {
            int i = 0;
            while(i < 27 && s[i] != -1) {
                value[i] = s[i];
                i++;
            }
        }
    }
}
