package com.my.baselibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by AIJU on 2017-04-17.
 */

public class BitmapRotateUtil {
    private static BitmapRotateUtil bitmapRotateUtils;
    private BitmapRotateUtil(){};
    public static BitmapRotateUtil getInstance()
    {
        if(null == bitmapRotateUtils)
        {
            bitmapRotateUtils = new BitmapRotateUtil();
        }
        return bitmapRotateUtils;
    }

    /**
     *得到 图片旋转 的角度
     *@param filepath
     *@return
     **/
    private int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
            if (exif != null)
            {
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
                if (orientation != -1) {
                    switch (orientation)
                    {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            degree = 90;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            degree = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            degree = 270;
                            break;
                    }
                }
            }
        } catch (IOException ex) { }
        return degree;
    }


    /**
     * @param angle 图片发生旋转的角度
     * @param bitmap 要旋转的bitmap
     * @return
     */
    private Bitmap rotateBitmap(int angle, Bitmap bitmap)
    {
        try {
            if(angle!=0)
            {  //如果照片出现了 旋转 那么 就更改旋转度数
                Matrix matrix = new Matrix();
                matrix.postRotate(angle);
                bitmap = Bitmap.createBitmap(bitmap,0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
        } catch (Exception e) {}
        return bitmap;
    }

    /**
     * 检查bitmap角度发生变换，如果发生变化进行校验，防止图片颠倒
     * @param filePath
     * @param bitmap
     * @return
     */
    public Bitmap checkBitmapAngleToAdjust(String filePath,Bitmap bitmap)
    {
        int angle = getExifOrientation(filePath);
        return rotateBitmap(angle, bitmap);
    }
}
