package com.srtp.LabSheet;

/**
 * Created by Henry Huang on 2015/3/30.
 */
public class BloodAnalysis_base extends BloodValue{
    private int df, hl;
    public BloodAnalysis_base(){
        df = hl = 0;
    }

    private void DataAnalysis(){
        df = hl = 0;
        for(int i = 25; i >= 0; --i){
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

    boolean n_tf(int i){
        return i != 0;
    }

    public String TellDisease(){
        DataAnalysis();
        int l = (df&(~hl));
        StringBuilder re = new StringBuilder("");
        if(n_tf(hl & WBC) && n_tf(hl & NEUTP))
            re.append(Disease.DH_WBC+"\n");
        if(n_tf(hl&RBC) && n_tf(hl&HGB)) {
            if(n_tf(l&ESR))
                re.append(Disease.DH_RBC+"\n");
            else
                re.append(Disease.DL_ESR+"\n");
        }
        if(n_tf(hl&PLT))
            re.append(Disease.DH_PLT+"\n");
        if(n_tf(hl&ESR))
            re.append(Disease.DH_ESR+"\n");
        if(n_tf(hl&RC))
            re.append(Disease.DH_RC+"\n");
        if(n_tf(hl&MONON))
            re.append(Disease.DH_MON+"\n");
        if(n_tf(l&RBC) && n_tf(l&HGB) && n_tf(hl&ESR))
            re.append(Disease.DL_RBC+"\n");
        if(n_tf(l&WBC) && n_tf(hl&LYMPHN))
            re.append(Disease.DL_WBC+"\n");
        if(n_tf(l&PLT))
            re.append(Disease.DL_PLT+"\n");
        if(n_tf(l&RC))
            re.append(Disease.DL_RC+"\n");
        if(n_tf(l&LYMPHN))
            re.append(Disease.DL_LYMPHN+"\n");
        if(n_tf(hl&EOP) || n_tf(hl&EON))
            re.append(Disease.DH_EOP+"\n");
        if((hl&BASOP)%2==1 || n_tf(hl&BASON))
            re.append(Disease.DH_BASOP+"\n");
        if(re.length() == 0)
            re.append(Disease.NORMAL);
        else
            re.deleteCharAt(re.length()-1);
        return re.toString();
    }


}
