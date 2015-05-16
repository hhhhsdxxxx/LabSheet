package com.srtp.LabSheet;

import java.net.PortUnreachableException;

/**
 * Created by Henry Huang on 2015/5/9.
 */
public class Disease {
    //base
    public final static String DH_RBC = "红细胞指标高，可能真性红细胞增多症，肺原性心脏病，长期居住于高山地区";  //结合血红蛋白
    public final static String DL_RBC = "红细胞指标低，可能贫血，有出血部位";
    public final static String DH_WBC = "白细胞和中性粒细胞指标高，可能局部有感染，炎症";       //结合中性粒细胞
    public final static String DL_WBC = "淋巴细胞指标高，白细胞指标低，可能病毒感染，(也可能药物作用)"; //结合淋巴细胞升高
    public final static String DL_LYMPHN = "淋巴细胞指标低，可能免疫缺陷（可能）";
    public final static String DL_PLT = "血小板指标低，有出血倾向，很多症状会导致该指标降低，可与医生交流";
    public final static String DH_PLT = "若血小板指标特别高，可与医生交流";
    public final static String DH_ESR = "血沉指标高，可能急性炎症， 结缔组织病，严重贫血，恶性肿瘤，结核病";
    public final static String DL_ESR = "血沉指标低，可能脱水，红细胞增多症"; //可结合血红蛋白和红细胞
    public final static String DH_RC = "网织红细胞指标高，可能溶血性贫血，或有大量出血症状";
    public final static String DL_RC = "网织红细胞指标低，可能再生障碍性贫血";
    public final static String DH_MON = "单核细胞指标高，可能结核，伤寒，疟疾";
    public final static String DH_EOP = "嗜酸性细胞比例高，可能有过敏性疾病，寄生虫病";
    public final static String DH_BASOP = "嗜碱性细胞比例高，过敏反应，或皮肤病";
    public final static String NORMAL = "基本健康";
    //chm
    public final static String DH_TC = "胆固醇和甘油三脂超标，一般为肥胖所致";
    public final static String DH_URIC = "尿酸高，可能是肾功能不全或痛风，注意饮食，少食高蛋白食物";
    public final static String DH_GLU = "可能糖尿病，及时做进一步测试";
    public final static String DH_NA = "钠超标，可能缺水，请多及时补充水分";
    public final static String DH_K = "钾指标高，可能少尿，发热，考虑可服用利尿剂";
    public final static String DH_CL = "氯指标高，可能少尿，呼吸性碱中毒";
    public final static String DH_CA = "钙指标高，可能应用维生素D过量，甲状旁腺机能亢进";
    public final static String DH_P = "磷指标高，可能维生素D过量，肾功能不全";
    public final static String DH_MG = "镁指标高，可能肾小球肾炎，少尿";
    public final static String DH_ALT = "可能有肝部病变";    // GGT ALP
    public final static String DH_AST = "考虑心肌梗塞，及时与医生咨询";
    public final static String DH_TBIL_D = "可能黄疸";     // DBIL/TBIL > 0.2
    public final static String DH_TBIL_N = "可能休息不足";
    public final static String DH_TP = "脱水所致的血液浓缩，及时就医";  //ALB
    public final static String DL_TP = "体内水分过多，或肾病，及时就医"; //ALB
    public final static String DH_TBA_H = "急性肝炎"; //TBA*10~100
    public final static String DH_TBA_L = "慢性肝炎，肝损伤"; //TBA*1~10
    public final static String DL_PA = "儿童营养不良，肝脏疾病";
    public final static String DH_UREA = "肾脏疾病";

}
