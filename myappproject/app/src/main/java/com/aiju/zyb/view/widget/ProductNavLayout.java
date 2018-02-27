package com.aiju.zyb.view.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.aiju.zyb.R;
import com.my.baselibrary.utils.HLog;

/**
 * Created by AIJU on 2017-05-26.
 */

public class ProductNavLayout extends LinearLayout implements NestedScrollingParent {
    private NestedScrollingParentHelper parentHelper = new NestedScrollingParentHelper(this);
    private View mTop;
    private View mNav;
    private ViewPager mViewPager;
    private int mTopViewHeight;
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;
    private float mLastY;
    private boolean mDragging;
    private LinearLayout fragment_content;

    public ProductNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }


    @Override
    protected void onFinishInflate() {
        mTop = findViewById(R.id.top_nav);
        // mNav = findViewById(R.id.id_stickynavlayout_indicator);
        //mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        fragment_content=(LinearLayout) findViewById(R.id.fragment_content);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTopViewHeight = mTop.getMeasuredHeight();

        //上面测量的结果是viewPager的高度只能占满父控件的剩余空间
        //重新设置viewPager的高度
        ViewGroup.LayoutParams layoutParams = fragment_content.getLayoutParams();
        layoutParams.height = getMeasuredHeight();  // - mNav.getMeasuredHeight();
        fragment_content.setLayoutParams(layoutParams);
    }

    @Override
    public void scrollTo(int x, int y) {
        //限制滚动范围

        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }

        super.scrollTo(x, y);

    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

//实现NestedScrollParent接口-------------------------------------------------------------------------

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        parentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        parentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }
    private boolean addHeight;
    private boolean isDeal=false;

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        HLog.w("height",dy+"----"+getScrollY()+"---"+ViewCompat.canScrollVertically(target, -1)+"---"+this.getTop());

        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() > 0&&!ViewCompat.canScrollVertically(target, -1);
      //  boolean _showTop=dy<0 && ViewCompat.canScrollVertically(target, -1);
        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }



        //处理子view传上来的事件
        //头部高度
     //   int headerHeight = headerView.getHeight();

        /*
        if (dy > 0) {
            //向上滑动


            if ( Math.abs(  this.getTop() - dy ) <= mTopViewHeight) {
                //header 在向上滑动的过程
                this.layout(this.getLeft(), this.getTop() - dy, this.getRight(), this.getBottom() - dy);
                if (!addHeight) {

                    //只增加一次 高度 height
                    addHeight = true;
                    ViewGroup.LayoutParams params = this.getLayoutParams();
                    params.height = mTopViewHeight+this.getHeight();
                    this.setLayoutParams(params);
                    requestLayout();
                }
                consumed[1] += dy;
            }
            else{
                //当用户滑动动作太大，一次位移太大就会把parentview滑动脱离底部屏幕
                if((this.getTop() + mTopViewHeight) > 0){
                    int offsetY  = mTopViewHeight + this.getTop();
                    this.layout(this.getLeft(), this.getTop() - offsetY, this.getRight(), this.getBottom() - offsetY);
                    consumed[1] += offsetY;
                }
            }
        }
        if (dy < 0) {
            //向下滑动
            if ((this.getTop() + Math.abs(dy)) <= 0) {
                //header在向下滑动的过程
                //this.gettop是负数dy也是负数所以需要+dy的绝对值
                this.layout(this.getLeft(), this.getTop() + Math.abs(dy), this.getRight(), this.getBottom() + Math.abs(dy));
                consumed[1] += dy;
            }
            else{
                if(this.getTop() < 0){
                    int offsetY = Math.abs(this.getTop());
                    this.layout(this.getLeft(), this.getTop() +offsetY, this.getRight(), this.getBottom() + offsetY);
                    consumed[1] += offsetY;
                }
            }
        }
        */


    }

    //boolean consumed:子view是否消耗了fling
    //返回值：自己是否消耗了fling。可见，要消耗只能全部消耗
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e("onNestedFling", "called");
        return false;
    }

    //返回值：自己是否消耗了fling。可见，要消耗只能全部消耗
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e("onNestedPreFling", "called");
    //    return  false;

        if (getScrollY() < mTopViewHeight) {
            fling((int) velocityY);
           return true;
        }else {
            return false;
        }


    }

    @Override
    public int getNestedScrollAxes() {
        return parentHelper.getNestedScrollAxes();
    }

    /**
     *
     *
     * @return
     */
    public int getCurScrolly()
    {
        return getScrollY();
    }

    private SwipeRefreshLayout swipeRefreshLayout;

    public  void setView(SwipeRefreshLayout view)
    {
        this.swipeRefreshLayout=view;
    }

}