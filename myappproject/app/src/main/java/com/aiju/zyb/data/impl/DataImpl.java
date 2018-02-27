package com.aiju.zyb.data.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by AIJU on 2017-04-17.
 */

public abstract class DataImpl<T> implements DataSerializeInterface<T> {
    private static String Tag = "DataSerializeInterface";
    @Override
    public String serialize(T object) throws IOException {
        // EcBaoLogger.v(Tag, "写入缓存开始：");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        //  EcBaoLogger.v("ECBAO","写入缓存成功:"+serStr);
        return serStr;
    }

    @Override
    public T deSerialization(String str) throws IOException, ClassNotFoundException {
        //  EcBaoLogger.v("ECBAO","读取缓存开始:"+str);
        if(str == null) return null;
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        T obj = (T) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        //  EcBaoLogger.v(Tag, "读取缓存结束");
        return obj;
    }
}
