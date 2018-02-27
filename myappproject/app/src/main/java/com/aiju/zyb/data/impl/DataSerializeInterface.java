package com.aiju.zyb.data.impl;

import java.io.IOException;

/**
 * Created by AIJU on 2017-04-17.
 */

public interface DataSerializeInterface<T> {
    /**
     * 序列化对象
     * @param object
     * @return
     */
    String serialize(T object) throws IOException;

    /**
     * 反序列化
     * @param str
     * @return
     */
    T deSerialization(String str) throws IOException, ClassNotFoundException;

}

