package com.aiju.zyb.view.manage;

import com.google.gson.Gson;
import com.my.baselibrary.utils.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AIJU on 2017-04-20.
 */

public class CommonParams {

    private String httpkey="ecbao";
    public Map<String,String> getBaseParams(Map<String,Object>  params, String method)
    {
        String param = new Gson().toJson(params);
        String sign=getHttpSign(method,param);
        Map<String,String> paraPost = new HashMap<>();
        paraPost.put("method", method);
        paraPost.put("sign",sign);
        paraPost.put("param", urlEncoder(param));
       // HLog.w("post_params","base_url"+HttpManager.BASEURLPOST+"--"+paraPost.toString());
        return  paraPost;
    }

    public String getHttpSign(String method, String params) {
        String sign = "";
        try {
            String param = params;
            sign = Util.md5("method" + method + "param" + param + httpkey).toLowerCase();
        } catch (Exception e) {

        }
        return sign;
    }

    /**
     * 将参数进行编码码
     */

    public String urlEncoder(String json) {
        String encoder = null;
        try {
            encoder = URLEncoder.encode(json, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encoder;
    }
}
