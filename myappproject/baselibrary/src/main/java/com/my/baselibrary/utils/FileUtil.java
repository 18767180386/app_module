package com.my.baselibrary.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by AIJU on 2017-06-05.
 */

public class FileUtil {
    /**
     * 个推的libs文件夹
     */
    public static final String GE_TUI_LIBS = "/libs";
    /**
     * 个推的数据库
     */
    public static final String GE_TUI_DB = "increment.db";

    public static int NET_WORD_ERROR = -1234;

    private static String filename="/youhuike_apk/";

    private static  String apkname="youhuike.apk";

    public static void deleteFile(String filePath){
        deleteFile(new File(filePath));
    }
    /**
     * 递归删除文件及文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            file.delete();
        }
    }

    public static File getGeTuiLibs() {
        String filePath = getSDcardPath() + GE_TUI_LIBS;
        return new File(filePath);
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDcardPath() {
        File sdDir = null;
        if (!isSdCardExist()) {
            return null;
        }
        sdDir = Environment.getExternalStorageDirectory();
        return sdDir.toString();
    }

    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 删除数据库
     *
     * @param context
     */
    public static void deleteDB(Context context) {

        final File file = context.getDatabasePath(GE_TUI_DB);
        boolean result = file.exists();
        if (result) {
            file.delete();
        }
    }

    /**
     * 下载文件
     *
     * @param serverpath
     *            文件服务器的路径
     * @param savedfilepath
     *            在本地保存的路径
     * @param pd
     *            进度条对话框
     * @return 下载成功 返回文件对象,下载失败返回null
     */
    public static File downLoad(String serverpath, String savedfilepath, ProgressDialog pd) {
        try {
            URL url = new URL(serverpath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == 200) {
                int max = conn.getContentLength();
                if(null != pd){
                    pd.setMax(100);
                }
                InputStream is = conn.getInputStream();
                File file = new File(savedfilepath);
                if (file.exists()) {
                    return file;
                } else {

                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    int len = 0;
                    byte[] buffer = new byte[100 * 1024];// 100K
                    int total = 0;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        total += len;
                        if(null != pd){
                            pd.setProgress(total*100/max);
                        }
                        Thread.sleep(30);
                    }
                    fos.flush();
                    fos.close();
                    is.close();
                    return file;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    /**
     * 下载文件
     *
     * @param serverpath
     *            文件服务器的路径
     * @param savedfilepath
     *            在本地保存的路径
     * @return 下载成功 返回文件对象,下载失败返回null
     */
    public static File downLoad(String serverpath, String savedfilepath, ProgressBar pb) {
        try {
            URL url = new URL(serverpath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            if (conn.getResponseCode() == 200) {
                int max = conn.getContentLength();
                if(null != pb){
                    pb.setMax(max/100);
                }
                InputStream is = conn.getInputStream();
                File file = new File(savedfilepath);
                if (file.exists()) {
                    return file;
                } else {

                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    int len = 0;
                    byte[] buffer = new byte[100 * 1024];// 100K
                    int total = 0;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        total += len;
                        if(null != pb){
                            pb.setProgress(total/100);
                        }
                        //Thread.sleep(30);
                        //HLog.d("down", total+"");
                    }
                    fos.flush();
                    fos.close();
                    is.close();
                    return file;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 下载apk
     * */
    public static void download(final String path, final String files, final Handler h) {
        new Thread() {
            public void run() {

                HttpURLConnection hc = null;
                FileOutputStream out = null;
                int allLength = 0;
                int downLength = 0;
                try {
                    URL url = new URL(path);
                    hc = (HttpURLConnection) url.openConnection();
                    hc.setConnectTimeout(10000);
                    hc.setReadTimeout(20000);
                    InputStream input = hc.getInputStream();
                    hc.connect();
                    allLength = hc.getContentLength();

                    File file = new File(files);

                    out = new FileOutputStream(file);

                    byte[] buf = new byte[1024 * 10];
                    int len = -1;
                    while ((len = input.read(buf)) != -1) {
                        out.write(buf, 0, len);
                        downLength += len;
                        Message msg = new Message();
                        msg.arg1 = downLength;
                        msg.arg2 = allLength;
                        msg.obj = file.getPath();
                        h.sendMessage(msg);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    h.sendEmptyMessage(NET_WORD_ERROR);

                } catch (IOException e) {
                    e.printStackTrace();
                    h.sendEmptyMessage(NET_WORD_ERROR);
                } finally {
                    try {
                        if (out != null)
                            out.close();
                        hc.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    public static String getFileName(String serverurl) {

        String fileName = "";
        fileName = serverurl.substring(serverurl.lastIndexOf("/") + 1);
        if(!fileName.endsWith("apk")){
            fileName = "ecbaohrm.apk";
        };
        return fileName;
    }


    /**
     * 获取图片宽 保持宽在700以下
     *
     * @param srcPath
     * @return
     */
    public static void compressImage(String srcPath, String desPath) {

        try {

            // ==================测量
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            float ww =480f; //600f;
            float hh = 800f;//1000f;
            // 缩放比。
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
            bitmap = BitmapRotateUtil.getInstance().checkBitmapAngleToAdjust(
                    srcPath, bitmap);
            // ==================压缩
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 压缩到小于800kb,
            while (baos.toByteArray().length / 1024 > 800) {
                baos.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                options -= 10;
            }
            // File myCaptureFile = new File( + fileName + ".jpg");
            BufferedOutputStream fbos;
            try {
                fbos = new BufferedOutputStream(new FileOutputStream(desPath));
                fbos.write(baos.toByteArray());
                // fbos = new BufferedOutputStream(baos);
                fbos.flush();
                fbos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

    }


    public static boolean getSDCardState() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static File createSDFile(String path, String filename)
            throws IOException {
        File f = new File(Environment.getExternalStorageDirectory().toString()
                + "/" + path);
        if (!f.exists()) {
            f.mkdirs();
        }
        f = new File(f.getPath(), filename);
        if (!f.exists()) {
            f.createNewFile();
        }else{
            f.delete();
            f.createNewFile();
        }
        return f;
    }



    /**
     * 获取图片宽 保持宽在700以下
     *
     * @param srcPath
     * @return
     */
    public static void compressImages(String srcPath, String desPath) {

        try {

            // ==================测量
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            float ww =480f; //600f;
            float hh = 800f;//1000f;
            // 缩放比。
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
            bitmap = BitmapRotateUtil.getInstance().checkBitmapAngleToAdjust(
                    srcPath, bitmap);
            // ==================压缩
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 压缩到小于800kb,
            while (baos.toByteArray().length / 1024 > 800) {
                baos.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                options -= 10;
            }
            // File myCaptureFile = new File( + fileName + ".jpg");
            BufferedOutputStream fbos;
            try {
                fbos = new BufferedOutputStream(new FileOutputStream(desPath));
                fbos.write(baos.toByteArray());
                // fbos = new BufferedOutputStream(baos);
                fbos.flush();
                fbos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取apk地址
     * */
    public static String getDownloadUrl() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            // 获取当前外部存储设备的目录
            String path = Environment.getExternalStorageDirectory() + filename ;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path, apkname);
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file.getPath();
        }
        return null;
    }
}