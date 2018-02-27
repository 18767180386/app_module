package com.jaydenxiao.common.commonutils;

/**
 * Created by john on 2018/2/8.
 */

public class CommonUtil {
    public static final String join(String join,String[] strAry){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<strAry.length;i++){
            if(i==(strAry.length-1)){
                sb.append(strAry[i]);
            }else{
                sb.append(strAry[i]).append(join);
            }
        }

        return new String(sb);
    }
}
