package com.aiju.zyb.model.base;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.my.baselibrary.net.HttpRequestCallback;
import com.my.baselibrary.net.HttpRequestUtil;
import com.my.baselibrary.utils.HLog;
import com.my.baselibrary.utils.MD5Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by AIJU on 2017-04-28.
 */

public abstract class BaseModel implements IBaseModel {

    /**
     *
     *get
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    protected void sendGetRequest(Context context, String url, Map<String,Object> params, HttpRequestCallback callback)
    {
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent","User-Agent':'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
        header.put("cookie", "cookie");
        header.put("X-Requested-With", "XMLHttpRequest");
        if(params!=null) {
            HLog.w("net_ret_param", params.toString());
        }
        HttpRequestUtil.getInstance().baseHttpRequest(context,BaseUrl+url,getBaseParam(params),null,"get",callback);
    }


    /**
     *
     * post
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    protected void sendPostRequest(Context context, String url, Map<String, Object> params,HttpRequestCallback callback)
    {
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent","User-Agent':'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
        header.put("cookie", "cookie");
        header.put("X-Requested-With", "XMLHttpRequest");
        if(params!=null) {
            HLog.w("net_ret_param", params.toString());
        }
        HttpRequestUtil.getInstance().baseHttpRequest(context,BaseUrl+url,getBaseParam(params),null,"post",callback);
    }


    /**
     *
     * 参数处理
     * @param params
     * @return
     */
    private    Map<String,String> getBaseParam(Map<String, Object> params)
    {
        Map<String,String> _param=new LinkedHashMap<>();
        if(params!=null)
        {
            String args=new Gson().toJson(params);
            String sign = getBaseSign(args);
            _param.put("args", urlEncoder(args));
            _param.put("sign", sign);
        }else{
            String args="{}";
            String sign = getBaseSign(args);
            _param.put("args", urlEncoder(args));
            _param.put("sign", sign);
        }
        return _param;
    }


    /**
     *
     *
     * @param args
     * @return
     */
    private  String getBaseSign(String args)
    {
        String sign = "";
        try {
            String param = args;
            sign = MD5Utils.md5Signature("args" + param + httpkey).toLowerCase();
        } catch (Exception e) {

        }
        return sign;
    }


    private String httpkey="zhiyebao_key";
    /**
     * 签名工具
     *
     * @return
     */
    private String getSign(Map<String, Object> mParamsMap) {
        String target = "";
        String sign = "";

        Iterator<String> it = mParamsMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = mParamsMap.get(key).toString();
            target = target + key + value;
        }
        target = target + httpkey;
        if (!TextUtils.isEmpty(target)) {
            sign = MD5Utils.md5Signature(target);
        }
        HLog.w("http_param",target);
        // EcBaoLogger.e(LogTAG + ":BEFORE sign：", target);
        // EcBaoLogger.e(LogTAG + ":After sign：", sign);
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
