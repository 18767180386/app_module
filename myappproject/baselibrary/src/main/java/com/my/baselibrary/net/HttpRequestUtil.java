package com.my.baselibrary.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.my.baselibrary.base.BaseApplication;
import com.my.baselibrary.utils.HLog;
import com.my.baselibrary.utils.MD5Utils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by AIJU on 2017-04-14.
 */

public class HttpRequestUtil {
    private static final String TAG = "HttpRequestUtil";

    private static HttpRequestUtil instance;

    private OkHttpClient okHttpClient;

    private Handler httpHandler;

    private Gson gson;

   private   Map<String, String> netParams;//网络参数
   private   Map<String, String> netHeaders;//网络请求头设置


    public static synchronized HttpRequestUtil getInstance() {
        if (instance == null) {
            synchronized (HttpRequestUtil.class) {
                instance = new HttpRequestUtil();
            }
        }
        return instance;
    }

    public HttpRequestUtil() {
       // okHttpClient = new OkHttpClient();
        Context context = BaseApplication.getInstance().getContext();
        CookiesManager cookiesManager = new CookiesManager(context);

        okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
              .writeTimeout(20, TimeUnit.SECONDS)
              .readTimeout(20, TimeUnit.SECONDS)
               .cookieJar(new CookiesManager(BaseApplication.getInstance().getContext())).build();

        httpHandler = new Handler(Looper.getMainLooper());
        gson = new Gson();
    }

    /**
     * http get 请求
     *
     * @param url
     * @param callback
     */
    public void getRequest(Context context, String url, final HttpRequestCallback callback) {
        Request request = new Request.Builder().tag(getTagByContext(context)).url(url).get().build();
        okHttpClient.newCall(request).enqueue(getCallback(context, callback));
    }


    /**
     * http post 请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public void postRequest(Context context, String url, FormBody params, HttpRequestCallback callback) {
        Request request = new Request.Builder().tag(getTagByContext(context)).url(url).post(params).build();
        okHttpClient.newCall(request).enqueue(getCallback(context, callback));
    }


    /**
     * 网络请求
     *
     * @param context
     * @param url
     * @param params
     * @param header
     * @param method
     * @param callback
     */
    public  void  baseHttpRequest(Context context,String url, Map<String, String> params, Map<String, String> header,String method,HttpRequestCallback callback)
    {
        netHeaders=header;
        netParams=params;

        Request.Builder builder = new Request.Builder();

        if (netHeaders != null) {

            for (String key : netHeaders.keySet()) {
                builder.addHeader(key, netHeaders.get(key));
            }
        }

        Request request = null;

        if (method == null || method.equalsIgnoreCase("get")) {

            StringBuilder sb = new StringBuilder();
            sb.append(url);

            if (netParams != null && netParams.size() > 0) {
                sb.append("?");
                for (String key : netParams.keySet()) {
                    sb.append(key).append("=").append(netParams.get(key)).append("&");
                }
                sb.setLength(sb.length() - 1);
            }

            HLog.w("ret",sb.toString());

            request = builder.url(sb.toString()).get().build();

        } else {
            FormBody.Builder encodingBuilder = new FormBody.Builder();

            if (netParams != null) {
                for (String key : netParams.keySet()) {
                    encodingBuilder.add(key, netParams.get(key));
                }
            }
            request = builder.url(url).post(encodingBuilder.build()).build();
        }
         //   HLog.w("httppost", request.toString()+"---"+encodingBuilder.build().toString());

      //  Request request = new Request.Builder().tag(getTagByContext(context)).url(url).post(params).build();
        okHttpClient.newCall(request).enqueue(getCallback(context, callback));
    }

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
        target = target + "ecbao";
        if (!TextUtils.isEmpty(target))
            sign = MD5Utils.md5Signature(target);
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

    /**
     * 将Map转换成json字符串
     */

    public String toJson(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

    public String Md5(String args) {
        StringBuffer strBuf = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(args.getBytes("UTF-8"));
            byte[] encryption = md5.digest();
            strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return strBuf.toString();
    }


    /**
     * 阿里妈妈请求GET
     */
    public void getAliMMRequest(Context context, String url, final HttpRequestCallback callback) {
        String cookie ="";// DataManager.getInstance().getAliMMCookie();
        Request request = new Request.Builder()
                .addHeader("User-Agent", "User-Agent':'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36")
                .addHeader("cookie", cookie)
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .tag(getTagByContext(context)).url(url).get().build();
        okHttpClient.newCall(request).enqueue(getCallback(context, callback));
    }


    /**
     * 阿里妈妈请求POST
     */
    public void postAliMMRequest(String url, FormBody params, HttpRequestCallback callback) {
        String cookies =""; //DataManager.getInstance().getAliMMCookie();
        Request request = new Request.Builder()
                .addHeader("User-Agent", "User-Agent':'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36")
                .addHeader("cookie", cookies)
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .tag(getTagByContext(BaseApplication.getInstance().getContext())).url(url).post(params).build();
        okHttpClient.newCall(request).enqueue(getCallback(BaseApplication.getInstance().getContext(), callback));
    }

    /**
     * 通过context 生成http 请求tag
     * tag 用来标识 http 请求，可通过tag 来取消请求
     *
     * @param context
     * @return
     */
    private String getTagByContext(Context context) {
        return context != null ? context.getClass().getName() : null;
    }

    /**
     * 重新封装一层callback
     * 添加onStart 和 onFinish
     *
     * @param callback
     * @return
     */
    private Callback getCallback(final Context context, final HttpRequestCallback callback) {
        if (callback != null) {
            callback.onStart();
        }

        return new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    HttpResult httpResult = new HttpResult(HttpHandler.HTTP_FAILURE);
                    httpResult.callback = callback;
                    httpResult.exception = new HttpException(e);
                    httpResult.call = call;
                    httpHandler.post(new HttpHandler(httpResult));
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    if (response.code() == 200) {
                        HttpResult httpResult = new HttpResult(HttpHandler.HTTP_SUCCESS);
                        httpResult.callback = callback;
                        String ret=response.body().string();
                        httpResult.response = ret;//response.body().toString();
                        HLog.w("net",ret);
                        httpResult.call = call;
                        httpHandler.post(new HttpHandler(httpResult));
                    } else {
                        HttpResult httpResult = new HttpResult(HttpHandler.HTTP_FAILURE);
                        httpResult.callback = callback;
                        httpResult.call = call;
                        httpResult.exception = new HttpException(response.code());
                        httpHandler.post(new HttpHandler(httpResult));
                    }
                }
            }
        };
    }

    class HttpHandler implements Runnable {
        public static final int HTTP_SUCCESS = 1;
        public static final int HTTP_FAILURE = 2;

        private HttpResult httpResult;

        public HttpHandler(HttpResult httpResult) {
            this.httpResult = httpResult;
        }

        @Override
        public void run() {
            httpResult.callback.onFinish();

            if (httpResult.what == HTTP_SUCCESS) {
                // 当返回的类型是String

                if (httpResult.callback.type == String.class) {
                    httpResult.callback.onResponse(httpResult.response);

                } else {
                    try {
                        Object obj = gson.fromJson(httpResult.response, httpResult.callback.type);
                        httpResult.callback.onResponse(obj);
                    } catch (Exception e) {
                        Log.e(TAG, httpResult.response);
                        // 解析异常
                        httpResult.callback.onFailure(httpResult.call, new HttpException(HttpException.EXCEPTION_DATA));
                    }
                }
            } else {
                httpResult.callback.onFailure(httpResult.call, httpResult.exception);
            }
        }
    }

    class HttpResult {
        private HttpRequestCallback callback;
        private String response;
        private HttpException exception;
        private Call call;
        private int what;

        private Message msg;

        public HttpResult(int what) {
            this.msg = new Message();
            this.what = what;
        }

        public Message getMessage() {
            this.msg.what = what;
            msg.obj = this;
            return msg;

        }
    }


    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     */
    private void _postAsyn(String url, HttpRequestCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsyn(String url, HttpRequestCallback callback, File file, String fileKey) {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsyn(String url, HttpRequestCallback callback, File file, String fileKey, Param... params) {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }


    public void postAsyn(String url, HttpRequestCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException {
        getInstance()._postAsyn(url, callback, files, fileKeys, params);
    }


    public void postAsyn(String url, HttpRequestCallback callback, File file, String fileKey) {
        getInstance()._postAsyn(url, callback, file, fileKey);
    }


    public void postAsyn(String url, HttpRequestCallback callback, File file, String fileKey, Param... params) {
        getInstance()._postAsyn(url, callback, file, fileKey, params);
    }


    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else return params;
    }

    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private static final String SESSION_KEY = "Set-Cookie";
    private static final String mSessionKey = "JSESSIONID";

    private Map<String, String> mSessions = new HashMap<>();

    private void deliveryResult(final HttpRequestCallback callback, final Request request) {
        okHttpClient.newCall(request).enqueue(getCallback(BaseApplication.getInstance().getContext(), callback));
        /*
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                try
                {
                    final String string = response.body().string();
                    if (callback.mType == String.class)
                    {
                        sendSuccessResultCallback(string, callback);
                    } else
                    {
                        Object o = new Gson().fromJson(string, callback.mType);
                        sendSuccessResultCallback(o, callback);
                    }


                } catch (IOException e)
                {
                    sendFailedStringCallback(response.request(), e, callback);
                } catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    sendFailedStringCallback(response.request(), e, callback);
                }

            }
        });
        */
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        httpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        httpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Param[] params) {
        params = validateParam(params);

        /*
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

        for (Param param : params)
        {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null)
        {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                                "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }
        */


        MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (Param param : params) {
            mbody.addFormDataPart(param.key, param.value);
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String fileName = file.getName();
            Log.i("imageName:", file.getName());//经过测试，此处的名称不能相同，如果相同，只能保存最后一个图片，不知道那些同名的大神是怎么成功保存图片的。
            mbody.addFormDataPart(fileKeys[i], file.getName(), RequestBody.create(MediaType.parse(guessMimeType(fileName)), file));

        }

        /*
        RequestBody requestBody =mbody.build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url("http://192.168.1.105/interface/index.php?action=sendMultipart")
                .post(requestBody)
                .build();

                */

        RequestBody requestBody = mbody.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }


    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder();
        // FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }


    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }

    public static class Param {
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }
}
