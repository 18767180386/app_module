package com.jaydenxiao.common.commonwidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.jaydenxiao.common.R;

/**
 * Created by john on 2018/2/9.
 */

public class XListView extends ListView implements AbsListView.OnScrollListener {
    private float mLastY = -1;
    private Scroller mScroller;
    private OnScrollListener mScrollListener;
    private IXListViewListener mListViewListener;
    private XListViewHeader mHeaderView;
    private RelativeLayout mHeaderViewContent;
    private int mHeaderViewHeight; // header view's height
    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false; // is refreashing.
    private XListViewFooter mFooterView;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;
    private int mTotalItemCount;
    private int itemcount;
    private int visibleLastIndex;
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 400;
    private final static int PULL_LOAD_MORE_DELTA = 50;
    private final static float OFFSET_RADIO = 1.8f;  //1.8

    private int SCROLL_LOADING = 0;
    private static int SCROLL_COUNT = 20;

    private boolean isCanTouch = true;

    private final static int LIMIT=300;

    private boolean noMoreData;
    private boolean isResh=false;


    private boolean isShowFoot=true;

    /**
     * @param context
     */
    public XListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("InlinedApi") private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(this);
        mHeaderView = new XListViewHeader(context);
        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
        addHeaderView(mHeaderView);
        mFooterView = new XListViewFooter(context);
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
        setPullLoadEnable(true);
        setPullRefreshEnable(true);
        try
        {
            if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
                this.setOverScrollMode(View.OVER_SCROLL_NEVER);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (mIsFooterReady == false) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) {
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
            setFooterDividersEnabled(false);
        } else {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
            setFooterDividersEnabled(true);
//			mFooterView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					if(mFooterView.getLookMoreVisibility()!= VISIBLE){
//						startLoadMore();
//					}
//				}
//			});
        }
    }

    public void stopRefresh() {
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    public void stopLoadMore() {
        SCROLL_LOADING = 0;
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(XListViewFooter.STATE_HIDE);
        }

        mTotalItemCount = itemcount;

    }

    public void setmTotalItemCount() {
        mTotalItemCount = 0;
        SCROLL_LOADING = 0;
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(XListViewFooter.STATE_HIDE);
        }
    }

    public void setCanTouch(boolean canTouch ) {
        isCanTouch = canTouch;
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta,boolean flag) {
        mHeaderView.setVisiableHeight((int) delta
                + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                if(!flag)
                {
                    mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                }else{
                    mHeaderView.setState(XListViewHeader.STATE_READY);
                }
            } else {
                mHeaderView.setState(XListViewHeader.STATE_NORMAL);
            }
        }
        setSelection(0);
    }

    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0;
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
                // more.
                mFooterView.setState(XListViewFooter.STATE_READY);
            } else {
                mFooterView.setState(XListViewFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);
    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        mFooterView.setState(XListViewFooter.STATE_LOADING);
        if (SCROLL_LOADING == 1) {
            return;
        }
        mPullLoading = true;
        if (mListViewListener != null) {
            SCROLL_LOADING = 1;
            mListViewListener.onLoadMore();
        }
    }


    public void setNoMoreData(boolean noMoreData)
    {
        this.noMoreData=noMoreData;
    }

    public void setIshowFoot(boolean flag)
    {
        this.isShowFoot=flag;
    }

    public void setIsResh(boolean flag)
    {
        this.isResh=flag;
    }


    private void showFooterView() {
        mPullLoading = true;
        mFooterView.setState(XListViewFooter.STATE_LOADING);
    }

    @SuppressWarnings("static-access")
    public void showLookMore() {
        if(mFooterView.getLookMoreVisibility() != VISIBLE){
            mFooterView.setState(XListViewFooter.STATE_LOOK_MORE);
        }
    }


    public  void hideFoot()
    {
        //mFooterView.setState(DsbListViewFooter.STATE_LOOK_MORE);
        mFooterView.setVisibility(View.GONE);
    }

    public void hideLookMore() {
        mFooterView.setState(XListViewFooter.STATE_NORMAL);
    }

    public void hideNoMoreTip() {
        mFooterView.setState(XListViewFooter.STATE_NORMAL);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0
                        && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0) && !isResh) {
                    updateHeaderHeight(deltaY / OFFSET_RADIO,true);
                    invokeOnScrolling();
                }
                // else if (getLastVisiblePosition() == mTotalItemCount - 1
                // && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
                // updateFooterHeight(-deltaY / OFFSET_RADIO);
                // }
                break;
            default:
                mLastY = -1;
                // 上滑
                if (getFirstVisiblePosition() == 0) {
                    if(!isResh)
                    {
                        if (mEnablePullRefresh && !mPullRefreshing
                                && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                            mPullRefreshing = true;
                            mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                            if (mListViewListener != null) {
                                mListViewListener.onRefresh();
                                mFooterView.setState(XListViewFooter.STATE_HIDE_ALL);
                            }
                        }
                        resetHeaderHeight();
                    }
                }
                // 下滑
                else if (getLastVisiblePosition() == mTotalItemCount - 1) {
//				 if (mEnablePullLoad
//				 && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA
//				 && !mPullLoading) {
//				 showFooterView();
//				 }
//				 resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if(mListViewListener!=null)
        {
            mListViewListener.onShowTopView(firstVisibleItem);
        }
        itemcount = totalItemCount;
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
        // 一个值和另外一个值比，如果相等，就隐藏，


        /*
        if(!noMoreData)
        {
            if (getLastVisiblePosition() > 10
                    && getLastVisiblePosition() >= totalItemCount - 10) {

                //Log.d("scroll","mTotalItemCount2 : " + mTotalItemCount + "    totalItemCount : " + totalItemCount);

                startLoadMore();
                resetFooterHeight();
            }

        }else{
            hideFooterView();
            if (getLastVisiblePosition() > 10) {
                resetFooterHeight();
            }
            if(totalItemCount > 2){
                showLookMore();
            }
        }
        */

        if (mTotalItemCount == totalItemCount) {
            hideFooterView();
            if (getLastVisiblePosition() > 10) {
                resetFooterHeight();
            }
            if(totalItemCount > 2){

                if(isShowFoot) {
                    showLookMore();

                }else{
                    hideFoot();
                }
            }
        } else {
            // 判断是否到底部
            if (getLastVisiblePosition() > 10
                    && getLastVisiblePosition() >= totalItemCount - 10) {

                //Log.d("scroll","mTotalItemCount2 : " + mTotalItemCount + "    totalItemCount : " + totalItemCount);

                startLoadMore();
                resetFooterHeight();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if( isCanTouch ) {
            return super.onInterceptTouchEvent(ev);
        } else {
            Log.w("LaMaListView", "此touch事件被取消了");
            return true; //不将touch 事件传递下去
        }
    }


    public void initRefresh() {
        try {
            updateHeaderHeight(LIMIT / OFFSET_RADIO,false);
            invokeOnScrolling();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (mEnablePullRefresh
                            && !mPullRefreshing
                            && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true;
                        mHeaderView
                                .setState(XListViewHeader.STATE_REFRESHING);
                        if (mListViewListener != null) {
                            mListViewListener.onRefresh();
                            mFooterView.setState(XListViewFooter.STATE_HIDE_ALL);
                        }
                    }
                    resetHeaderHeight();
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideFooterView() {
        SCROLL_LOADING = 0;
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(XListViewFooter.STATE_HIDE);
        }
    }

    public void setIsAllowShowLoadMore(boolean isAllow){
        mFooterView.setIsShowLookMore(isAllow);
    }

    public void setXListViewListener(IXListViewListener l) {
        mListViewListener = l;
    }

    public void setOnLookMoreOnClickListener(XListViewFooter.ILookMoreOnClickListener listener){
        mFooterView.setOnLookMoreOnClickListener(listener);
    }

    public interface OnXScrollListener extends OnScrollListener {
        public void onXScrolling(View view);
    }

    public interface IXListViewListener {
        public void onRefresh();

        public void onLoadMore();

        public void onShowTopView(int firstVisibleItem);
    }
}
