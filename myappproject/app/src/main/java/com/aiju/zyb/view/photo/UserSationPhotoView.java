package com.aiju.zyb.view.photo;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.aiju.zyb.R;
import com.aiju.zyb.TaokeApplication;
import com.aiju.zyb.view.widget.GifView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jaydenxiao.common.commonutils.FileUtils;
import com.my.baselibrary.base.BaseApplication;
import com.my.baselibrary.utils.SettingUtil;
import com.my.baselibrary.utils.ToastUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by john on 2018/2/8.
 */

public class UserSationPhotoView extends RelativeLayout implements View.OnClickListener {

    private SationPhotoEntity item;
    private PhotoView ivImg;
    private Button deleteImage;
    private Context mContext;
    private PhotoDetailActivity activity;
    private boolean loading = false;

    public static Map<String,Object>  pic=new HashMap<>();

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    public UserSationPhotoView(PhotoDetailActivity context) {
        super(context);
        Log.i("yhnujm", context + "");
        mContext = context;
        activity = context;
        addView(LayoutInflater.from(context).inflate(R.layout.usersationview, null));
        ivImg = (PhotoView) findViewById(R.id.ivImg);
    }


    public void setItem(final SationPhotoEntity item, final int p) {
        this.item = item;
        ivImg = (PhotoView) findViewById(R.id.ivImg);
       // Glide.with(activity).load(item.getImgurl()).into(ivImg);
        Glide.with(mContext).load(item.getImgurl()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
               // image.setImageBitmap(resource);
                ivImg.setImageBitmap(resource);
                pic.put(item.getImgurl(),resource);
            }
        }); //方法中设置asBitmap可以设置回调类型
         if(iPhotoViewCallBack!=null)
         {
             iPhotoViewCallBack.photoCallBack(ivImg);
         }
        ivImg.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //saveBitmapToJpg(activity,(Bitmap)pic.get(item.getImgurl()));
                showSaveImageMenus(ivImg);
                return true;
            }
        });
    }

    private Bitmap measureImage(String path) {
        try {

            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            float ww = 720f;
            float hh = 1280f;
            int be = 1;
            if (w > h && w > ww) {
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;
            bitmap = BitmapFactory.decodeFile(path, newOpts);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //第一种方法
    public Bitmap getHttpBitmap(String data) {
        Bitmap bitmap = null;
        try {
            //初始化一个URL对象
            URL url = new URL(data);
            //获得HTTPConnection网络连接对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5 * 1000);
            connection.setDoInput(true);
            connection.connect();
            //得到输入流
            InputStream is = connection.getInputStream();
            // Log.i("TAG", "*********inputstream**"+is);
            bitmap = BitmapFactory.decodeStream(is);
            // Log.i("TAG", "*********bitmap****"+bitmap);
            //关闭输入流
            is.close();
            //关闭连接
            connection.disconnect();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }


    public void layoutSet(final String url) {
        //DialogTools.showWaittingDialog(activity);
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
                try {
                    Bitmap img = getHttpBitmap(url);
                    // handler.sendEmptyMessage(0);
                    // Message msg = Message.obtain();
                    Message msg = new Message();
                    msg.what = 0;
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    // bundle.p
                    msg.setData(bundle);
                    msg.obj = img;
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                       // DialogTools.closeWaittingDialog();
                        Bitmap img = (Bitmap) msg.obj;
                        String url = msg.getData().getString("url");//接受msg传递过来的
                        if (img != null) {
                            int w = img.getWidth();
                            int h = img.getHeight();
                            int screen = SettingUtil.getDisplaywidthPixels();
                            ivImg.setImageBitmap(img);
                            //int _w=(screen*w)/720;
                            // int _h=(_w*h)/w;

                            /*
                            iv_dialog_pic.getLayoutParams().width = _w;
                            iv_dialog_pic.getLayoutParams().height = _h;
                            //ImageLoader.getInstance().displayImage(picUrl,iv_dialog_pic);
                            dia_frame.setVisibility(View.VISIBLE);
                            iv_dialog_pic.setImageBitmap(img);
                            */

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        ;
    };


    @Override
    public void onClick(View v) {

		/*
		switch (v.getId()) {
		case R.id.deleteImage:
			try {
				String id = v.getTag().toString();
				String[] arr = id.split("\\|");
				String pid = arr[0];
				String pos = arr[1];
				String ava = arr[2];
				String date = getStrToDate(arr[3]);
				if (ava.equals("1")) {
					MsgBox.toast("该图片为用户图像不能删除！");
					return;
				}
				Log.d("ret-----mmsdsd", pid + "----" + pos + "---" + ava
						+ "---" + date);
				deletephoto(pid, pos, date);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		*/

    }

    public String getStrToDate(String str) {
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        // System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = null;
        try {
            today = sdf.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sdf.format(today);
    }


	/*
	public void deletephoto(final String id,final String pos,String date) {
		DialogTools.showWaittingDialog(mContext);
		NetService.getIns().DeletePhotoByID(id,date,new AjaxCallBack<String>() {
					@Override
					public void onSuccess(String t) {
						super.onSuccess(t);
						DialogTools.closeWaittingDialog();
						ULog.d("删除照片" + t);
						try {
							JSONObject json = new JSONObject(t);
							if (json.getString("code").equals("Success")){
								MsgBox.toast(json.getString("msg"));
								Log.d("pos------1",pos);
								//MyPhotoLookActivity.instance.vpMain.setCurrentItem(Integer.valueOf(pos),false);
								if(MyPhotoLookActivity.instance.list.size()==1)
								{

									   activity.finish();
									   return;
								}



								Iterator<MyPhotoEntity> sListIterator = MyPhotoLookActivity.instance.list.iterator();
								while(sListIterator.hasNext()){
									MyPhotoEntity e = sListIterator.next();
								    if(e.getPhotoID()==Integer.valueOf(id)){
								       sListIterator.remove();
								    }
								}
							  try
							   {

								  MyPhotoLookActivity.instance.deleteLoad(Integer.valueOf(pos));
							   }catch(Exception e)
							   {
								   e.printStackTrace();
							   }


								//MsgBox.toast(MyPhotoLookActivity.instance.list.size()+"");


								//MyPhotoLookActivity.instance.list.remove(Integer.valueOf(pos));
								//MyPhotoLookActivity.instance.deleteLoad(Integer.valueOf(pos));
							   //Log.d("ppppp",MyPhotoLookActivity.instance.list.size()+"");

								//MyPhotoActivity.instance.initData();


							} else {
								MsgBox.toast(json.getString("msg"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(Throwable t, String strMsg) {
						super.onFailure(t, strMsg);
						DialogTools.closeWaittingDialog();
					}
				});

	}
	*/

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
                    final String url = ((GifView) imageView).getUrl();
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                final byte[] buffer = getUrlFileData(url);
                                activity.runOnUiThread(new Runnable() {
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
                if (d != null) {

                    saveBitmapToJpg(activity, drawable2Bitmap(d));

					/*
					InputStream in = bitmap2InputStream(drawable2Bitmap(d), 100);
					byte[] buffer;
					try {
						buffer = new byte[in.available()];
						in.read(buffer);
						saveFile(buffer, fileName + ".jpg");
						saveBitmapToJpg();
					} catch (IOException e) {
						e.printStackTrace();
					}
					*/

                }
            }
        }
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
            ContentResolver cr = mContext.getContentResolver();
            Bitmap bmp = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
            String url = MediaStore.Images.Media.insertImage(cr, bmp, fileName, "");


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
            //UtilToast.show("图片已保存到你的相册");
            //activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));

            //	MediaScannerConnection.scanFile(activity,new String[] { Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()+ "/"+ filePath.getParentFile().getAbsolutePath() }, null,null);

            //MediaScannerConnection.scanFile(activity,new String[] { Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()+ "/"+  }, null,null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String saveBitmapToJpg(Context context, Bitmap faceBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Log.i("", "SD *****>> SD卡不存在");
        } else {
            Log.i("", "SD *****>> SD卡 存在");
        }
       // new FileUtils().createFile("zyb");

        // 创建图片保存目录
        File faceImgDir = new File(Environment.getExternalStorageDirectory(), "ZYB_ForumAlbum");
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

        ToastUtil.setToast("图片已保存到你的相册");
        // 保存后要扫描一下文件，及时更新到系统目录（一定要加绝对路径，这样才能更新）

        MediaScannerConnection.scanFile(context,
                new String[]{Environment.getExternalStorageDirectory() + File.separator + "ZYB_ForumAlbum" + File.separator + faceImgName}, null, null);

        return (Environment.getExternalStorageDirectory() + File.separator + "ZYB_ForumAlbum" + File.separator + faceImgName);
    }


    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }


    MediaScannerConnection msc = new MediaScannerConnection(TaokeApplication.getContext(), new MediaScannerConnection.MediaScannerConnectionClient() {
        public void onMediaScannerConnected() {
            msc.scanFile("/sdcard/image.jpg", "image/jpeg");
        }

        public void onScanCompleted(String path, Uri uri) {
            //Log.v(TAG, "scan completed");
            msc.disconnect();
        }
    });

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


    public  interface  IPhotoViewCallBack{
         void photoCallBack(PhotoView photoView);
    }


    public  IPhotoViewCallBack iPhotoViewCallBack;


    public  void setiPhotoViewCallBack(IPhotoViewCallBack iPhotoViewCallBack)
    {
        this.iPhotoViewCallBack=iPhotoViewCallBack;
    }
}