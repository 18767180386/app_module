package in.srain.cube.views.ptr;

import android.content.Context;
import android.util.AttributeSet;

public class PtrClassicFrameLayout extends PtrFrameLayout {
    private boolean disallowInterceptTouchEvent = false;
    private PtrClassicDefaultHeader mPtrClassicHeader;
    private PtrClassicDefaultFooter mPtrClassicFooter;

    public PtrClassicFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
        mPtrClassicFooter = new PtrClassicDefaultFooter(getContext());
        setFooterView(mPtrClassicFooter);
        addPtrUIHandler(mPtrClassicFooter);
    }

    public PtrClassicDefaultHeader getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        setLastUpdateTimeHeaderKey(key);
        setLastUpdateTimeFooterKey(key);
    }

    public void setLastUpdateTimeHeaderKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    public void setLastUpdateTimeFooterKey(String key) {
        if (mPtrClassicFooter != null) {
            mPtrClassicFooter.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        setLastUpdateTimeHeaderRelateObject(object);
        setLastUpdateTimeFooterRelateObject(object);
    }

    public void setLastUpdateTimeHeaderRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }

    public void setLastUpdateTimeFooterRelateObject(Object object) {
        if (mPtrClassicFooter != null) {
            mPtrClassicFooter.setLastUpdateTimeRelateObject(object);
        }
    }

    /**设置底部消失*/
    public void setBottomViewHiden(boolean isHiden){
        if(isHiden)
            mPtrClassicFooter.setVisibility(GONE);
        else
            mPtrClassicFooter.setVisibility(VISIBLE);

    }

    /*
    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        disallowInterceptTouchEvent = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (disallowInterceptTouchEvent) {
            return dispatchTouchEventSupper(e);
        }
        return super.dispatchTouchEvent(e);
    }
    */

    private int mLastMotionY=0;
    private int mLastMotionX=0;


    /*
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int y = (int) e.getRawY();
        int x = (int) e.getRawX();
        boolean resume = false;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 发生down事件时,记录y坐标
                mLastMotionY = y;
                mLastMotionX = x;
                resume = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // deltaY > 0 是向下运动,< 0是向上运动
                int deltaY = y - mLastMotionY;
                int deleaX = x - mLastMotionX;

                if (Math.abs(deleaX) > Math.abs(deltaY)) {
                    resume = false;
                } else {
                    //当前正处于滑动
                  //  if (isRefreshViewScroll(deltaY)) {
                      //  resume = true;
                   // }
                    resume=true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return resume;
    }
    */


}
