package com.my.baselibrary.net;

import okhttp3.FormBody;

/**
 * Created by AIJU on 2017-04-14.
 */

public class RequestParams {
    private FormBody.Builder builder;

    public RequestParams() {
        builder = new FormBody.Builder();
    }

    public void put(String key, String value) {
        builder.add(key, value);
    }

    public FormBody toParams() {
        return builder.build();
    }
}
