package com.srtp.LabSheet;

import java.util.HashMap;

/**
 * Created by Henry Huang on 2015/3/4.
 */
public class BloodValue {
    private static HashMap<String, Integer> name = new HashMap<String, Integer>();
    private static double[] bottom = new double[25];
    private static double[] top = new double[25];
    private final static String[] zb_name =
            {       "白细胞（WBC）", "红细胞（RBC）", "血红蛋白（HGB）", "红细胞压积（HCT）", "红细胞平均体积（MCV）", "平均血红蛋白量（MCH）",
                    "平均血红蛋白浓度（MCHC）", "血小板（PLT）", "淋巴细胞比率（LYMPHP）", "中性细胞比率（NEUTP）", "单核细胞比率（MONOP）",
                    "嗜酸性粒细胞比率（EOP）", "嗜碱性粒细胞比率（BASOP）", "淋巴细胞数（LYMPHN）", "中性粒细胞数（NEUT）", "单核细胞（MONON）",
                    "嗜酸性粒细胞（EON）", "嗜碱性粒细胞（BASON）", "红细胞分布宽度-CV（RDW-CV）", "红细胞分布宽度-SD（RDW-SD）", "血小板分布宽度（PDW）",
                    "平均血小板体积（MPV）", "血小板压积（PCT）", "大型血小板比率（P-LCR）", "血沉（ESR）"
            };
    private double[] value;

    public BloodValue(){
        value = new double[25];
        for(int i = 0; i < 25; i++)
            value[i] = -1;
    }

    public void Initial(){
        name.put("白细胞（WBC）", 0);
        name.put("红细胞（RBC）", 1);
        name.put("血红蛋白（HGB）", 2);
        name.put("红细胞压积（HCT）", 3);
        name.put("红细胞平均体积（MCV）", 4);
        name.put("平均血红蛋白量（MCH）", 5);
        name.put("平均血红蛋白浓度（MCHC）", 6);
        name.put("血小板（PLT）", 7);
        name.put("淋巴细胞比率（LYMPHP）", 8);
        name.put("中性细胞比率（NEUTP）", 9);
        name.put("单核细胞比率（MONOP）", 10);
        name.put("嗜酸性粒细胞比率（EOP）", 11);
        name.put("嗜碱性粒细胞比率（BASOP）", 12);
        name.put("淋巴细胞数（LYMPHN）", 13);
        name.put("中性粒细胞数（NEUT）", 14);
        name.put("单核细胞（MONON）", 15);
        name.put("嗜酸性粒细胞（EON）", 16);
        name.put("嗜碱性粒细胞（BASON）", 17);
        name.put("红细胞分布宽度-CV（RDW-CV）", 18);
        name.put("红细胞分布宽度-SD（RDW-SD）", 19);
        name.put("血小板分布宽度（PDW）", 20);
        name.put("平均血小板体积（MPV）", 21);
        name.put("血小板压积（PCT）", 22);
        name.put("大型血小板比率（P-LCR）", 23);
        name.put("血沉（ESR）", 24);
        bottom[0] = 4; top[0] = 10;
        bottom[1] = 3.5; top[1] = 5.5;
        bottom[2] = 110; top[2] = 160;
        bottom[3] = 36; top[3] = 50;
        bottom[4] = 82; top[4] = 100;
        bottom[5] = 26; top[5] = 32;
        bottom[6] = 320; top[6] = 360;
        bottom[7] = 100; top[7] = 300;
        bottom[8] = 20; top[8] = 40;
        bottom[9] = 50; top[9] = 70;
        bottom[10] = 3; top[10] = 8;
        bottom[11] = 0.5; top[11] = 5;
        bottom[12] = 0; top[12] = 1;
        bottom[13] = 0.8; top[13] = 4;
        bottom[14] = -1; top[14] = -1;
        bottom[15] = 0; top[15] = 0.8;
        bottom[16] = 0.05; top[16] = 0.5;
        bottom[17] = 0; top[17] = 0.1;
        bottom[18] = 10.9; top[18] = 15.4;
        bottom[19] = 37; top[19] = 54;
        bottom[20] = 9; top[20] = 17;
        bottom[21] = 9; top[21] = 13;
        bottom[22] = 0.17; top[22] = 0.35;
        bottom[23] = 13; top[23] = 43;
        bottom[24] = 0; top[24] = 15;
    }

    public final int getnId(String zb_name){
        return name.get(zb_name);
    }

    public final double getvalue_bottom(String zb_name){
        return bottom[name.get(zb_name)];
    }

    public final double getvalue_top(String zb_name){
        return top[name.get(zb_name)];
    }

    public final double differ(String zb_name, double zb_value){
        double re = zb_value - top[name.get(zb_name)];
        if(re > 0)
            return re;
        re = zb_value - bottom[name.get(zb_name)];
        if(re < 0)
            return re;
        return 0.0;
    }

    public void setValue(int i, double n){
        if(i > 24 || i < 0)
            return;
        value[i] = n;
    }

    public void copyValue(double[] copy){
        if(copy.length == 25){
            System.arraycopy(copy, 0, value, 0, 25);
        }
    }

    public double getValue(int i){
        return value[i];
    }

    public void clear(){
        for(int i = 0; i < 25; i++)
            value[i] = -1;
    }

    public final String getZname(int i){
        return zb_name[i];
    }
}
