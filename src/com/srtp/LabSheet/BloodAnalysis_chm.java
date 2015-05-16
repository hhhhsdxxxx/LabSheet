package com.srtp.LabSheet;

import java.util.HashMap;

/**
 * Created by Henry on 2015/5/10.
 */
public class BloodAnalysis_chm {
    final static int ALT    = 0x00000001;
    final static int AST    = 0x00000002;
    final static int GGT    = 0x00000004;
    final static int ALP    = 0x00000008;
    final static int TP     = 0x00000010;
    final static int ALB    = 0x00000020;
    final static int GLB    = 0x00000040;
    final static int A_G    = 0x00000080;
    final static int PA     = 0x00000100;
    final static int TBIL   = 0x00000200;
    final static int DBIL   = 0x00000400;
    final static int IBIL   = 0x00000800;
    final static int ECO2   = 0x00001000;
    final static int CR_S   = 0x00002000;
    final static int UREA   = 0x00004000;
    final static int GLU    = 0x00008000;
    final static int URIC   = 0x00010000;
    final static int TBA    = 0x00020000;
    final static int MAO    = 0x00040000;
    final static int K      = 0x00080000;
    final static int NA     = 0x00100000;
    final static int CL     = 0x00200000;
    final static int CA     = 0x00400000;
    final static int PHOS   = 0x00800000;
    final static int MG     = 0x01000000;
    final static int TC     = 0x02000000;
    final static int TG     = 0x04000000;
    private final static double[] bottom =
            { 5,  5,  5,  40, 60, 35, 18, 1.2, 180,  3, 0,  3, 21,  40, 2.0, 3.5, 140,  0.0, 0.0, 3.5, 135,  95, 2.1, 0.7, 0.6, 2.8, 0.56};
    private final static double[] top =
            {42, 42, 40, 179, 80, 55, 32, 2.5, 500, 25, 9, 16, 30, 133, 6.9, 6.0, 416, 25.0, 8.8, 5.3, 145, 108, 2.7, 1.6, 1.1, 6.0, 1.70};


    private static HashMap<String, Integer> name = new HashMap<String, Integer>();

    private double[] value;
    private double[] diff;
    private int df, hl;

    public static void Initial(){
        name.put("ALT", 0);
        name.put("AST", 1);
        name.put("GGT", 2);
        name.put("ALP", 3);
        name.put("TP", 4);
        name.put("ALB", 5);
        name.put("GLB", 6);
        name.put("A/G", 7);
        name.put("PA", 8);
        name.put("TBIL", 9);
        name.put("DBIL", 10);
        name.put("IBIL", 11);
        name.put("ECO2", 12);
        name.put("CR-S", 13);
        name.put("UREA", 14);
        name.put("GLU", 15);
        name.put("URIC", 16);
        name.put("TBA", 17);
        name.put("MAO", 18);
        name.put("K", 19);
        name.put("NA", 20);
        name.put("CL", 21);
        name.put("CA", 22);
        name.put("PHOS", 23);
        name.put("MG", 24);
        name.put("TC", 25);
        name.put("TG", 26);
    }

    public BloodAnalysis_chm(){
        value = new double[27];
        diff = new double[27];
        for(int i = 0; i < 27; ++i)
            value[i] = diff[i] = -1;
    }

    public static int getnId(String zb_name){
        return name.get(zb_name);
    }

    private double differ(int i, double zb_value){
        double re = zb_value - top[i];
        if(re > 0)
            return re;
        re = zb_value - bottom[i];
        if(re < 0)
            return re;
        return 0.0;
    }

    public void setValue(int i, double n){
        if(i > 26 || i < 0)
            return;
        value[i] = n;
        diff[i] = differ(i, n);
    }

    public boolean setbyString(String in){
        String []snum = in.split(",");
        //double []dnum = new double[26];
        int i = 0;
        if(snum.length == 27) {
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
        for(int i = 0; i < 25; ++i)
            value[i] = diff[i] = -1;
    }

    private void DataAnalysis(){
        df = hl = 0;
        for(int i = 26; i >= 0; --i){
            if(Math.abs(diff[i]-0.0)<0.0001 || Math.abs(diff[i]+1)<0.0001) {
                df = df << 1;
                hl = hl << 1;
            }
            else{
                df = (df << 1) + 1;
                if(diff[i]>0)
                    hl = (hl << 1) + 1;
                else
                    hl = hl << 1;
            }
        }
    }

    private boolean n_tf(int i){
        return i != 0;
    }

    public String TellDisease(){
        DataAnalysis();
        int l = (df&(~hl));
        StringBuilder re = new StringBuilder("");
        if(n_tf(hl&TC) && n_tf(hl&TG))
            re.append(Disease.DH_TC+"\n");
        if(n_tf(hl&URIC))
            re.append(Disease.DH_URIC+"\n");
        if(n_tf(hl&GLU))
            re.append(Disease.DH_GLU+"\n");
        if(n_tf(hl&NA))
            re.append(Disease.DH_NA+"\n");
        if(n_tf(hl&K))
            re.append(Disease.DH_K+'\n');
        if(n_tf(hl&CL))
            re.append(Disease.DH_CL+"\n");
        if(n_tf(hl&CA))
            re.append(Disease.DH_CA+"\n");
        if(n_tf(hl&PHOS))
            re.append(Disease.DH_P+"\n");
        if(n_tf(hl&MG))
            re.append(Disease.DH_MG+"\n");
        if(n_tf(hl&(ALT|GGT|ALP)))
            re.append(Disease.DH_ALT+"\n");
        if(n_tf(hl&ALT))
            re.append(Disease.DH_AST+"\n");
        if(n_tf(hl&TBIL)) {
            if(value[11]/value[10]>0.2)
                re.append(Disease.DH_TBIL_D + "\n");
            else
                re.append(Disease.DH_TBIL_N+"\n");
        }
        if(n_tf(hl&TP))
            re.append(Disease.DH_TP+"\n");
        if(n_tf(l&TP))
            re.append(Disease.DL_TP+"\n");
        if(n_tf(l&PA))
            re.append(Disease.DL_PA+"\n");
        if(n_tf(hl%TBA)){
            if(value[17]/top[17]>10)
                re.append(Disease.DH_TBA_H+"\n");
            else
                re.append(Disease.DH_TBA_L+"\n");
        }
        if(n_tf(hl&UREA))
            re.append(Disease.DH_UREA+"\n");
        if(re.length() != 0)
            re.deleteCharAt(re.length()-1);
        else
            re.append(Disease.NORMAL);
        return re.toString();
    }
}
