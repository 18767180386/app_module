package com.aiju.zyb.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.aiju.zyb.R;
import com.aiju.zyb.TaokeApplication;
import com.my.baselibrary.base.BaseApplication;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by john on 2018/2/8.
 */

public class GifView extends ImageView {
    private InputStream in;
    private boolean firstFrame;
    private AniThread curAniThread;
    private String filePath;
    private String url;
    private static int BACKGROUD_COLOR = 0x00000000;
    private boolean curBmpShowed = true;
    /**
     * 已循环播放的次数
     */
    private int readRound;

    private ImageChangedListener imageChangedListener = new ImageChangedListener() {
        @Override
        public boolean imageChanged(final Bitmap bitmap) {
            // ULog.d("imageChanged " + bitmap.toString());
            BaseApplication.handler.post(new Runnable() {
                @Override
                public void run() {
                    setImageBitmap(bitmap);
                }
            });
            return true;
        }
    };
    private String assets;

    // construct - refer for java
    public GifView(Context context) {
        this(context, null);
    }

    public int getImgWidth() {
        return width;
    }

    public int getImgHeight() {
        return height;
    }

    // construct - refer for xml
    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.gifView);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.gifView_src:
                    int id = ta.getResourceId(R.styleable.gifView_src, 0);
                    setSrc(id);
                    break;
                case R.styleable.gifView_stop:
                    boolean sp = ta.getBoolean(R.styleable.gifView_stop, false);
                    if (!sp) {
                        stop();
                    }
                    break;
            }
        }
        ta.recycle();
    }

    public static interface ImageChangedListener {
        public boolean imageChanged(Bitmap bitmap);
    }

    public void setImageChangedListener(ImageChangedListener listener) {
        imageChangedListener = listener;
    }

    public void stop() {
        curAniThread.stop = true;
    }

    public void start() {
        readRound = 0;
        if (null != curAniThread) {
            curAniThread.stop = true;
        }
        curBmpShowed = true;
        curAniThread = new AniThread();
        curAniThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            start();
        }
        return super.onTouchEvent(event);
    }

    private class AniThread extends Thread {
        public boolean stop = false;
        private int errorTimes = 0;

        @Override
        public void run() {
           // ULog.d("gif started");
            while (readRound < 5 && !stop) {
                try {
                    if (curBmpShowed) {
                        curBmpShowed = false;
                        if (null == nextBitmap()) {
                           // ULog.d("curFrame or image is null");
                        }
                        if (null != imageChangedListener) {
                            if (imageChangedListener
                                    .imageChanged(curFrame.image)) {
                                // ULog.d("gif go");
                                curBmpShowed = true;
                            }
                        } else {
                            curBmpShowed = true;
                            //ULog.d("imageChangedListener is null");
                        }
                    }
                    if (status == STATUS_OPEN_ERROR) {
                       // ULog.e("STATUS_OPEN_ERROR");
                        stop = true;
                    }
                    Thread.sleep(nextDelay());// / delta
                } catch (Exception ex) {
                    ex.printStackTrace();
                    curBmpShowed = true;
                    if (errorTimes++ > 5) {
                        stop = true;
                    }
                } catch (OutOfMemoryError e) {
                }
            }
           // ULog.d("gif thread stoped");
        }
    }

    ;

    public void setSrc(int id) {
        read(getResources().openRawResource(id));
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        init();
        start();
    }

    public void setSrc(String filePath) {
       // ULog.d("gif:" + filePath);
        this.assets = null;
        this.filePath = filePath;
        init();
        start();
    }

    public void setAsset(String assets) {
      //  ULog.d("gif:" + assets);
        this.assets = assets;
        init();
        start();
    }

    // ================================================================
    // ============================ �ָ��� =============================
    // ================================================================
    // to store *.gif data, Bitmap & delay
    class GifFrame {
        // to access image & delay w/o interface
        public Bitmap image;
        public int delay;

        public GifFrame(Bitmap im, int del) {
            image = im;
            delay = del;
        }

    }

    // to define some error type
    public static final int STATUS_OK = 0;
    public static final int STATUS_FORMAT_ERROR = 1;
    public static final int STATUS_OPEN_ERROR = 2;

    protected int status;

    protected int width; // full image width
    protected int height; // full image height
    protected boolean gctFlag; // global color table used
    protected int gctSize; // size of global color table
    protected int loopCount = 1; // iterations; 0 = repeat forever

    protected int[] gct; // global color table
    protected int[] lct; // local color table
    protected int[] act; // active color table

    protected int bgIndex; // background color index
    protected int bgColor; // background color
    protected int lastBgColor; // previous bg color
    protected int pixelAspect; // pixel aspect ratio

    protected boolean lctFlag; // local color table flag
    protected boolean interlace; // interlace flag
    protected int lctSize; // local color table size

    protected int ix, iy, iw, ih; // current image rectangle
    protected int lrx, lry, lrw, lrh;
    protected Bitmap image; // current frame
    protected Bitmap lastImage; // previous frame
    protected int frameindex = 0;

    protected byte[] imageData;

    public int getFrameindex() {
        return frameindex;
    }

    // public void setFrameindex(int frameindex) {
    // this.frameindex = frameindex;
    // if (frameindex > frames.size() - 1) {
    // frameindex = 0;
    // }
    // }

    protected byte[] block = new byte[256]; // current data block
    protected int blockSize = 0; // block size

    // last graphic control extension info
    protected int dispose = 0;
    // 0=no action; 1=leave in place; 2=restore to bg; 3=restore to prev
    protected int lastDispose = 0;
    protected boolean transparency = false; // use transparent color
    protected int delay = 0; // delay in milliseconds
    protected int transIndex; // transparent color index

    protected static final int MaxStackSize = 4096;
    // max decoder pixel stack size

    // LZW decoder working arrays
    protected short[] prefix;
    protected byte[] suffix;
    protected byte[] pixelStack;
    protected byte[] pixels;

    // protected Vector<GifFrame> frames; // frames read from current file
    GifFrame lastFrame;
    GifFrame curFrame;
    protected int frameCount;

    // /**
    // * Gets display duration for specified frame.
    // *
    // * @param n
    // * int index of frame
    // * @return delay in milliseconds
    // */
    // public int getDelay(int n) {
    // delay = -1;
    // if ((n >= 0) && (n < frameCount)) {
    // delay = ((GifFrame) frames.elementAt(n)).delay;
    // }
    // return delay;
    // }

    public int getFrameCount() {
        return frameCount;
    }

    public Bitmap getImage() {
        // return getFrame(0);
        return curFrame.image;
    }

    public int getLoopCount() {
        return loopCount;
    }

    public InputStream getStream() {
        return new ByteArrayInputStream(imageData);
    }

    protected void setPixels() {
        int[] dest = new int[width * height];
        // fill in starting image contents based on last image's dispose code
        if (lastDispose > 0) {
            if (lastDispose == 3) {
                // use image before last
                int n = frameCount - 2;
                if (n > 0) {
                    lastImage = lastFrame.image;
                } else {
                    lastImage = null;
                }
            }
            if (lastImage != null) {
                lastImage.getPixels(dest, 0, width, 0, 0, width, height);
                // copy pixels
                if (lastDispose == 2) {
                    // fill last image rect area with background color
                    int c = 0;
                    if (!transparency) {
                        c = lastBgColor;
                    } else {
                        c = BACKGROUD_COLOR;
                    }
                    for (int i = 0; i < lrh; i++) {
                        int n1 = (lry + i) * width + lrx;
                        int n2 = n1 + lrw;
                        for (int k = n1; k < n2; k++) {
                            dest[k] = c;
                        }
                    }
                }
            }
        }

        // copy each source line to the appropriate place in the destination
        int pass = 1;
        int inc = 8;
        int iline = 0;
        for (int i = 0; i < ih; i++) {
            int line = i;
            if (interlace) {
                if (iline >= ih) {
                    pass++;
                    switch (pass) {
                        case 2:
                            iline = 4;
                            break;
                        case 3:
                            iline = 2;
                            inc = 4;
                            break;
                        case 4:
                            iline = 1;
                            inc = 2;
                    }
                }
                line = iline;
                iline += inc;
            }
            line += iy;
            if (line < height) {
                int k = line * width;
                int dx = k + ix; // start of line in dest
                int dlim = dx + iw; // end of dest line
                if ((k + width) < dlim) {
                    dlim = k + width; // past dest edge
                }
                int sx = i * iw; // start of line in source
                while (dx < dlim) {
                    // map color and insert in destination
                    int index = ((int) pixels[sx++]) & 0xff;
                    int c = act[index];
                    if (c != 0) {
                        dest[dx] = c;
                    }
                    dx++;
                }
            }
        }
        try {
            image = Bitmap.createBitmap(dest, width, height, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError err) {
//			if (image != null && !image.isRecycled())
//				image.recycle();
            //ULog.e("图片内存溢出gif；" + url);

        }

    }

    // public Bitmap getFrame(int n) {
    // Bitmap im = null;
    // if ((n >= 0) && (n < frameCount)) {
    // im = ((GifFrame) frames.elementAt(n)).image;
    // }
    // return im;
    // }

    public Bitmap nextBitmap() {
        // XXX
        if (firstFrame) {
            readFile();
        }
        readContents();
        if (curFrame == null || curFrame.image == null) {
            return null;
        }
        return curFrame.image;
    }

    public int nextDelay() {
        // int delay=((GifFrame) frames.elementAt(frameindex)).delay;
        if (null == curFrame) {
            return 1000;
        }
        int delay = curFrame.delay;
        return delay > 0 ? delay : 120;
    }

    // to read & parse all *.gif stream
    public int read(InputStream is) {
        init();
        if (is != null) {
            in = is;
            readHeader();
            if (!err()) {
                readContents();
                if (frameCount < 0) {
                    status = STATUS_FORMAT_ERROR;
                }
            }
        } else {
            status = STATUS_OPEN_ERROR;
        }
        try {
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * XXX
     *
     * @param
     * @return
     */
    public int readFile() {
        try {
            if (filePath != null) {
                in = new FileInputStream(new File(filePath));
            } else if (assets != null) {
                in = TaokeApplication.getContext().getResources().getAssets().open(assets);
            } else if (assets != null) {
                in = TaokeApplication.getContext().getResources().getAssets().open(assets);
            } else {
                URL net = new URL(url);
                in = (InputStream) net.getContent();
            }
            firstFrame = false;
            readHeader();
            // if (!err()) {
            // readContents();
            // if (frameCount < 0) {
            // status = STATUS_FORMAT_ERROR;
            // }
            // }
        } catch (FileNotFoundException e) {
            status = STATUS_OPEN_ERROR;
            e.printStackTrace();
        } catch (IOException e) {
            status = STATUS_OPEN_ERROR;
            e.printStackTrace();
        } catch (Exception e) {
            status = STATUS_OPEN_ERROR;
            e.printStackTrace();
        }
        return bgColor;
    }

    protected void decodeImageData() {
        int NullCode = -1;
        int npix = iw * ih;
        int available, clear, code_mask, code_size, end_of_information, in_code, old_code, bits, code, count, i, datum, data_size, first, top, bi, pi;

        if ((pixels == null) || (pixels.length < npix)) {
            pixels = new byte[npix]; // allocate new pixel array
        }
        if (prefix == null) {
            prefix = new short[MaxStackSize];
        }
        if (suffix == null) {
            suffix = new byte[MaxStackSize];
        }
        if (pixelStack == null) {
            pixelStack = new byte[MaxStackSize + 1];
        }
        // Initialize GIF data stream decoder.
        data_size = read();
        clear = 1 << data_size;
        end_of_information = clear + 1;
        available = clear + 2;
        old_code = NullCode;
        code_size = data_size + 1;
        code_mask = (1 << code_size) - 1;
        for (code = 0; code < clear; code++) {
            prefix[code] = 0;
            suffix[code] = (byte) code;
        }

        // Decode GIF pixel stream.
        datum = bits = count = first = top = pi = bi = 0;
        for (i = 0; i < npix; ) {
            if (top == 0) {
                if (bits < code_size) {
                    // Load bytes until there are enough bits for a code.
                    if (count == 0) {
                        // Read a new data block.
                        count = readBlock();
                        if (count <= 0) {
                            break;
                        }
                        bi = 0;
                    }
                    datum += (((int) block[bi]) & 0xff) << bits;
                    bits += 8;
                    bi++;
                    count--;
                    continue;
                }
                // Get the next code.
                code = datum & code_mask;
                datum >>= code_size;
                bits -= code_size;

                // Interpret the code
                if ((code > available) || (code == end_of_information)) {
                    break;
                }
                if (code == clear) {
                    // Reset decoder.
                    code_size = data_size + 1;
                    code_mask = (1 << code_size) - 1;
                    available = clear + 2;
                    old_code = NullCode;
                    continue;
                }
                if (old_code == NullCode) {
                    pixelStack[top++] = suffix[code];
                    old_code = code;
                    first = code;
                    continue;
                }
                in_code = code;
                if (code == available) {
                    pixelStack[top++] = (byte) first;
                    code = old_code;
                }
                while (code > clear) {
                    pixelStack[top++] = suffix[code];
                    code = prefix[code];
                }
                first = ((int) suffix[code]) & 0xff;
                // Add a new string to the string table,
                if (available >= MaxStackSize) {
                    break;
                }
                pixelStack[top++] = (byte) first;
                prefix[available] = (short) old_code;
                suffix[available] = (byte) first;
                available++;
                if (((available & code_mask) == 0)
                        && (available < MaxStackSize)) {
                    code_size++;
                    code_mask += available;
                }
                old_code = in_code;
            }

            // Pop a pixel off the pixel stack.
            top--;
            pixels[pi++] = pixelStack[top];
            i++;
        }
        for (i = pi; i < npix; i++) {
            pixels[i] = 0; // clear missing pixels
        }
    }

    protected boolean err() {
        return status != STATUS_OK;
    }

    // to initia variable
    public void init() {
        readRound++;
        status = STATUS_OK;
        frameCount = 0;
        // frames = new Vector<GifFrame>();
        gct = null;
        lct = null;
        firstFrame = true;
        lastFrame = null;
        curFrame = null;
    }

    protected int read() {
        int curByte = 0;
        try {
            curByte = in.read();
        } catch (Exception e) {
            status = STATUS_FORMAT_ERROR;
        }
        return curByte;
    }

    protected int readBlock() {
        blockSize = read();
        int n = 0;
        if (blockSize > 0) {
            try {
                int count = 0;
                while (n < blockSize) {
                    count = in.read(block, n, blockSize - n);
                    if (count == -1) {
                        break;
                    }
                    n += count;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (n < blockSize) {
                status = STATUS_FORMAT_ERROR;
            }
        }
        return n;
    }

    // Global Color Table
    protected int[] readColorTable(int ncolors) {
        int nbytes = 3 * ncolors;
        int[] tab = null;
        byte[] c = new byte[nbytes];
        int n = 0;
        try {
            n = in.read(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (n < nbytes) {
            status = STATUS_FORMAT_ERROR;
        } else {
            tab = new int[256]; // max size to avoid bounds checks
            int i = 0;
            int j = 0;
            while (i < ncolors) {
                int r = ((int) c[j++]) & 0xff;
                int g = ((int) c[j++]) & 0xff;
                int b = ((int) c[j++]) & 0xff;
                tab[i++] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
        }
        return tab;
    }

    /**
     * read GIF file content blocks
     */
    protected void readContents() {
        boolean gifReadDone = false;
        while (!(gifReadDone || err())) {// XXX
            int code = read();
            switch (code) {
                case 0x2C: // image separator
                    readImage();
                    return;
                case 0x21: // extension
                    code = read();
                    switch (code) {
                        case 0xf9: // graphics control extension
                            readGraphicControlExt();
                            break;
                        case 0xff: // application extension
                            readBlock();
                            String app = "";
                            for (int i = 0; i < 11; i++) {
                                app += (char) block[i];
                            }
                            if (app.equals("NETSCAPE2.0")) {
                                readNetscapeExt();
                            } else {
                                skip(); // don't care
                            }
                            break;
                        default: // uninteresting extension
                            skip();
                    }
                    break;
                case 0x3b: // terminator
                    gifReadDone = true;
                    init();
                    break;
                case 0x00: //
                    break;
                default:
                    status = STATUS_FORMAT_ERROR;
            }
        }
    }

    protected void readGraphicControlExt() {
        read(); // block size
        int packed = read(); // packed fields
        dispose = (packed & 0x1c) >> 2; // disposal method
        if (dispose == 0) {
            dispose = 1; // elect to keep old image if discretionary
        }
        transparency = (packed & 1) != 0;
        delay = readShort() * 10; // delay in milliseconds
        transIndex = read(); // transparent color index
        read(); // block terminator
    }

    // to get Stream - Head
    protected void readHeader() {
        String id = "";
        for (int i = 0; i < 6; i++) {
            id += (char) read();
        }
        if (!id.startsWith("GIF")) {
            status = STATUS_FORMAT_ERROR;
            return;
        }
        readLSD();
        if (gctFlag && !err()) {
            gct = readColorTable(gctSize);
            bgColor = gct[bgIndex];
        }
    }

    protected void readImage() {
        // offset of X
        ix = readShort(); // (sub)image position & size
        // offset of Y
        iy = readShort();
        // width of bitmap
        iw = readShort();
        // height of bitmap
        ih = readShort();

        // Local Color Table Flag
        int packed = read();
        lctFlag = (packed & 0x80) != 0; // 1 - local color table flag

        // Interlace Flag, to array with interwoven if ENABLE, with order
        // otherwise
        interlace = (packed & 0x40) != 0; // 2 - interlace flag
        // 3 - sort flag
        // 4-5 - reserved
        lctSize = 2 << (packed & 7); // 6-8 - local color table size
        if (lctFlag) {
            lct = readColorTable(lctSize); // read table
            act = lct; // make local table active
        } else {
            act = gct; // make global table active
            if (bgIndex == transIndex) {
                bgColor = BACKGROUD_COLOR;
            }
        }
        int save = 0;
        if (transparency) {
            save = act[transIndex];
            act[transIndex] = 0; // set transparent color if specified
        }
        if (act == null) {
            status = STATUS_FORMAT_ERROR; // no color table defined
        }
        if (err()) {
            return;
        }
        decodeImageData(); // decode pixel data
        skip();
        if (err()) {
            return;
        }
        frameCount++;
        // create new image to receive frame data
        image = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        // createImage(width, height);
        setPixels(); // transfer pixel data to image
        // frames.addElement(new GifFrame(image, delay)); // add image to frame
        // XXX
        // list
        lastFrame = curFrame;
        curFrame = new GifFrame(image, delay);
        if (transparency) {
            act[transIndex] = save;
        }
        resetFrame();
    }

    // Logical Screen Descriptor
    protected void readLSD() {
        // logical screen size
        width = readShort();
        height = readShort();
        // packed fields
        int packed = read();
        gctFlag = (packed & 0x80) != 0; // 1 : global color table flag
        // 2-4 : color resolution
        // 5 : gct sort flag
        gctSize = 2 << (packed & 7); // 6-8 : gct size
        bgIndex = read(); // background color index
        pixelAspect = read(); // pixel aspect ratio
    }

    protected void readNetscapeExt() {
        do {
            readBlock();
            if (block[0] == 1) {
                // loop count sub-block
                int b1 = ((int) block[1]) & 0xff;
                int b2 = ((int) block[2]) & 0xff;
                loopCount = (b2 << 8) | b1;
            }
        } while ((blockSize > 0) && !err());
    }

    // read 8 bit data
    protected int readShort() {
        // read 16-bit value, LSB first
        return read() | (read() << 8);
    }

    protected void resetFrame() {
        lastDispose = dispose;
        lrx = ix;
        lry = iy;
        lrw = iw;
        lrh = ih;
        lastImage = image;
        lastBgColor = bgColor;
        dispose = 0;
        transparency = false;
        delay = 0;
        lct = null;
    }

    /**
     * Skips variable length blocks up to and including next zero length block.
     */
    protected void skip() {
        do {
            readBlock();
        } while ((blockSize > 0) && !err());
    }
}