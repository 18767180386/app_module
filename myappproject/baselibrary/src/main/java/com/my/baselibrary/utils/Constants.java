package com.my.baselibrary.utils;

import android.os.Environment;

/**
 * Created by AIJU on 2017-04-17.
 */

public class Constants {
    // 应用名称
    public static String APP_NAME = "";

    // 保存参数文件夹名称
    public static final String SHARED_PREFERENCE_NAME = "itau_jingdong_prefs";

    // SDCard路径
    public static final String SD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();

    // 图片存储路径
    public static final String BASE_PATH = SD_PATH + "/dianshangbao/";

    // 缓存图片路径
    public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";

    // 需要分享的图片
    public static final String SHARE_FILE = BASE_PATH + "QrShareImage.png";

    /**
     * 配置文件的参数
     */
    public static final String CONFIG_PREFERENCE_NAME = "StoreVersion"; // 放置一些存储信息
    /**
     * 创建快捷方式
     */
    public static final String CREATE_SHORTCUT = "createShortcut";
}
