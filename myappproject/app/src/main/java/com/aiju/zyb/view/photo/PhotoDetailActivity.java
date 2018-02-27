package com.aiju.zyb.view.photo;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.view.widget.GifView;
import com.aiju.zyb.view.widget.ImgViewPager;
import com.jaydenxiao.common.commonwidget.Image;
import com.my.baselibrary.utils.ToastUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhotoDetailActivity extends Activity implements View.OnClickListener {
    private PhotoDetailActivity instance;
    public ImgViewPager vpMain;
    private ArrayList<UserSationPhotoView> idelViewList = new ArrayList<UserSationPhotoView>();
    private TextView tvIndicator;
    private TextView tvNum;
    public List<SationPhotoEntity> list=new ArrayList<SationPhotoEntity>();
    private String str;
    private Button deleteImage;
    private RelativeLayout photo_re;
    int current = 0;
    private int p=0;
    private int listPos = 0;
    private TextView save_pic;
    private PhotoView photoView;
    public static void launch(Context context, int listPos, String str, int p) {
        Intent intent = new Intent(context,  PhotoDetailActivity.class);
        intent.putExtra("listPos", listPos);
        intent.putExtra("photo", str);
        intent.putExtra("p",p);
        //intent.putParcelableArrayListExtra("photo", (ArrayList<? extends Parcelable>) list);
        context.startActivity(intent);
    }

    public static void launchActivity(Context context, int listPos, String str) {
        Intent intent = new Intent(context,  PhotoDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("listPos",listPos);
        bundle.putString("photo",str);
        intent.putExtras(bundle);
        //intent.putParcelableArrayListExtra("photo", (ArrayList<? extends Parcelable>) list);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        instance=this;
        vpMain = (ImgViewPager) findViewById(R.id.vpMain);
        tvIndicator = ((TextView) findViewById(R.id.tvIndicator));
        save_pic=(TextView) findViewById(R.id.save_pic);
        save_pic.setOnClickListener(this);
        photo_re=(RelativeLayout)findViewById(R.id.photo_re);
        deleteImage=((Button) findViewById(R.id.deleteImage));
        findViewById(R.id.backIvs).setOnClickListener(this);
        tvNum = ((TextView) findViewById(R.id.tvNum));
        Bundle bundle=getIntent().getExtras();
        str= bundle.getString("photo");
        //p=bundle.getInt("p",0);
        listPos = bundle.getInt("listPos", -1);
        String[] arr=str.split(",");
        SationPhotoEntity p=null;
        if(arr!=null && arr.length>0)
        {
            for(int i=0;i<arr.length;i++)
            {
                p=new SationPhotoEntity();
                p.setImgurl(arr[i]);
                list.add(p);
            }
        }
        onCallPermission();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIvs:
                finish();
                break;
            case R.id.save_pic:
                /*
                if (idelViewList != null && idelViewList.get(current) != null) {
                    showSaveImageMenus(idelViewList.get(current));
                }
                */

                if(photoView!=null)
                {
                   // showSaveImageMenus(photoView);
                }

               // UserSationPhotoView  photoView=(UserSationPhotoView)vp
                //

                /*
                  ImgViewPager view = (ImgViewPager) vpMain.findViewWithTag(vpMain.getCurrentItem());
                if(view!=null)
                {
                    PhotoView img=view.findViewById()
                    showSaveImageMenus(img);
                }
                */
                try {
                    saveBitmapToJpg(PhotoDetailActivity.this, (Bitmap) UserSationPhotoView.pic.get(list.get(current).getImgurl()));
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void initData() {
        idelViewList.clear();
        idelViewList.add(new UserSationPhotoView(PhotoDetailActivity.this));
        idelViewList.add(new UserSationPhotoView(PhotoDetailActivity.this));
        idelViewList.add(new UserSationPhotoView(PhotoDetailActivity.this));
        idelViewList.add(new UserSationPhotoView(PhotoDetailActivity.this));
        vpMain.setAdapter(new MyPagerAdapter());
        if (listPos != -1) {
            vpMain.setCurrentItem(listPos, false);

        } else {
            // changeIndicator(-1);
        }
        tvIndicator.setText((listPos %list.size() + 1)
                + "");

        tvNum.setText("/" + list.size());
        vpMain.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;


    /**
     *
     * 申请权限
     */
    public void onCallPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(instance, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(instance,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_ASK_CALL_PHONE);
                return;
            }else{
               initData();
            }
        } else {
           initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   initData();
                } else {
                  ToastUtil.setToast("申请权限失败");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    class MyPagerAdapter extends PagerAdapter {

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public Object instantiateItem(View vp, int pos) {
            Log.d("cjy", "instantiateItem " + pos);

            UserSationPhotoView v = null;
            try
            {
                v = idelViewList.get(0);
                idelViewList.remove(0);
                ((ViewPager) vp).addView(v);
                if (null != list) {
                    SationPhotoEntity item = list.get(pos % list.size());
                    item.setPos(pos);
                    // Log.d("--------------1",item.getUrl());
                    v.setiPhotoViewCallBack(new UserSationPhotoView.IPhotoViewCallBack() {
                        @Override
                        public void photoCallBack(PhotoView photoViews) {
                           photoView=photoViews;
                        }
                    });
                    v.setItem(item,p);
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            return v;

        }

        @Override
        public int getCount() {
            if (list.size() > 1) {
                return list.size();
            }
            return 1;

        }

        @Override
        public void finishUpdate(View arg0) {

        }

        @Override
        public void destroyItem(View arg0, int pos, Object arg2) {
            Log.d("cjy", "destroyItem " + pos + "  getChildCount"
                    + ((ViewPager) arg0).getChildCount());
            UserSationPhotoView contentView = (UserSationPhotoView) arg2;
            ((ViewPager) arg0).removeView(contentView);
            idelViewList.add(0, contentView);
        }
    }

    private PagerAdapter homePagerAdapter = new PagerAdapter() {

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public Object instantiateItem(View vp, int pos) {

            Log.d("cjy", "instantiateItem " + pos);
            UserSationPhotoView v = null;
            try
            {
                v = idelViewList.get(0);
                idelViewList.remove(0);
                //MsgBox.toast((pos+1)+"");
                ((ViewPager) vp).addView(v);
                if (null != list) {
                    SationPhotoEntity item = list.get(pos % list.size());
                    item.setPos(pos);
                    //Log.d("--------------1",item.getUrl());
                    v.setItem(item,p);
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            return v;

        }

        @Override
        public int getCount() {
            if (list.size() > 1) {
                return list.size();
            }
            return 1;

        }

        @Override
        public void finishUpdate(View arg0) {

        }

        @Override
        public void destroyItem(View arg0, int pos, Object arg2) {
            Log.d("cjy", "destroyItem " + pos + "  getChildCount"
                    + ((ViewPager) arg0).getChildCount());
            UserSationPhotoView contentView = (UserSationPhotoView) arg2;
            ((ViewPager) arg0).removeView(contentView);
            idelViewList.add(0, contentView);
        }
    };


    //implements OnPageChangeListener

    class  MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(final int pos) {
            vpMain.setTag(pos);
            current=pos;
            tvIndicator.setText(""+(pos % list.size() + 1));
            tvNum.setText("/" + list.size());
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg1) {

        }
    }

    private ViewPager.OnPageChangeListener pgEvent = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int pos) {
            tvIndicator.setText(""+(pos % list.size() + 1));
            tvNum.setText("/" + list.size());
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg1) {

        }
    };
    String name;
    private void showSaveImageMenus(final View imageView) {
        if (imageView != null) {
            Random rdm = new Random(System.currentTimeMillis());
            int min = 10000;
            int max = 99999;
            final String fileName = name + ""
                    + (rdm.nextInt(max) % (max - min + 1) + min);
            if (imageView instanceof GifView) {
                try {
                    final String url=((GifView) imageView).getUrl();
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                final byte[] buffer = getUrlFileData(url);
                                runOnUiThread( new Runnable() {
                                    public void run() {
                                        saveFile(buffer, fileName + ".gif");
                                    }
                                });
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }


                        }
                    }).start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                PhotoView iv = (PhotoView) imageView;
                Drawable d = iv.getDrawable();
              //  saveBitmapToJpg(PhotoDetailActivity.this,drawable2Bitmap(d));
                if (d != null) {
                    saveBitmapToJpg(PhotoDetailActivity.this,drawable2Bitmap(d));
                    /*
                    InputStream in = bitmap2InputStream(drawable2Bitmap(d), 100);
                    byte[] buffer;
                    try {
                        buffer = new byte[in.available()];
                        in.read(buffer);
                        saveFile(buffer, fileName + ".jpg");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */
                }
            }
        }
    }

    private String saveBitmapToJpg(Context context, Bitmap faceBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            //Log.i(TAG, "SD *****>> SD卡不存在");
        } else {
            //Log.i(TAG, "SD *****>> SD卡 存在");
        }
        // 创建图片保存目录
        File faceImgDir = new File(Environment.getExternalStorageDirectory(), "zyb_ForumAlbum");
        if (!faceImgDir.exists()) {
            faceImgDir.mkdir();
        }
        // 以系统时间命名文件
        String faceImgName = "dsb_forum_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        File file = new File(faceImgDir, faceImgName);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            faceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 保存后要扫描一下文件，及时更新到系统目录（一定要加绝对路径，这样才能更新）
        ToastUtil.setToast("图片已保存到你的相册");
        MediaScannerConnection.scanFile(context,
                new String[]{Environment.getExternalStorageDirectory() + File.separator + "zyb_ForumAlbum" + File.separator + faceImgName}, null, null);

        //String url=(Environment.getExternalStorageDirectory() + File.separator + "zyb_ForumAlbum" + File.separator + faceImgName);

        return (Environment.getExternalStorageDirectory() + File.separator + "zyb_ForumAlbum" + File.separator + faceImgName);
    }



    private byte[] getUrlFileData(String fileUrl) throws Exception {
        URL url = new URL(fileUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.connect();
        InputStream cin = httpConn.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = cin.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        cin.close();
        byte[] fileData = outStream.toByteArray();
        outStream.close();
        return fileData;
    }

    private void saveFile(byte[] buffer, String fileName) {
        try {
            ContentResolver cr = getContentResolver();
            Bitmap bmp = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
            String    url  = MediaStore.Images.Media.insertImage(cr, bmp, fileName, "");


//			String albumPath = Environment.getExternalStorageDirectory()
//					+ "/DCIM/Camera/";
//			File dirFile = new File(albumPath);
//			if (!dirFile.exists()) {
//				dirFile.mkdir();
//			}
//
//			fileName = albumPath + fileName;
//			File myCaptureFile = new File(fileName);
//			BufferedOutputStream bos = new BufferedOutputStream(
//					new FileOutputStream(myCaptureFile));
//			bos.write(buffer);
//			bos.flush();
//
//			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//					Uri.parse("file://" + fileName)));

            //MediaScannerConnection.scanFile(instance, new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + fileName)});

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            ToastUtil.setToast("图片已保存到你的相册");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public InputStream bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance=this;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
