package com.jaydenxiao.common.commonwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaydenxiao.common.R;

/**
 * Created by john on 2018/2/9.
 */

public class XListViewFooter extends LinearLayout {
    public final static int STATE_HIDE_ALL = -1;
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;
    public final static int STATE_HIDE = 3;
    public final static int STATE_LOOK_MORE = 4;
    private Context mContext;

    private View mContentView;
    private View mProgressBar;
    private Button mBtnLookMore;
    private LinearLayout mLookMoreLayout;
    private TextView mTxtNoMoreData;

    private boolean mIsShowLookMore = false;
    private ILookMoreOnClickListener mListener;

    public XListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public XListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setState(int state) {
        mContentView.setVisibility(View.GONE);
        mTxtNoMoreData.setVisibility(GONE);
        if (state == STATE_LOADING) {
            mContentView.setVisibility(View.VISIBLE);
            setLookMoreVisible(View.GONE);
        } else if (state == STATE_HIDE) {
            mContentView.setVisibility(View.GONE);
            setLookMoreVisible(View.VISIBLE);
        } else if (state == STATE_LOOK_MORE) {
            mTxtNoMoreData.setVisibility(VISIBLE);
            setLookMoreVisible(View.VISIBLE);
        } else if (state == STATE_HIDE_ALL) {
            mTxtNoMoreData.setVisibility(GONE);
            setLookMoreVisible(GONE);
        } else {
            setLookMoreVisible(View.VISIBLE);
        }
    }

    public void setBottomMargin(int height) {
        if (height < 0)
            return;
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        return lp.bottomMargin;
    }

    public void normal() {
        mContentView.setVisibility(View.GONE);
    }

    public void loading() {
        mContentView.setVisibility(View.VISIBLE);
        Log.i("test", "loading ....");
    }

    public void hide() {
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        lp.height = 0;
        mContentView.setLayoutParams(lp);
    }

    public void show() {
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mContentView.setLayoutParams(lp);
    }

    public int getLookMoreVisibility() {
        return mLookMoreLayout.getVisibility();
    }

    public void setIsShowLookMore(boolean isShow) {
        Log.i("test", "setIsShowLookMore ... " + isShow);

        mIsShowLookMore = isShow;
        if (isShow) {
            setLookMoreVisible(VISIBLE);
        } else {
            mLookMoreLayout.setVisibility(GONE);
            mTxtNoMoreData.setVisibility(GONE);
        }
    }

    private void setLookMoreVisible(int visible) {
        if (!mIsShowLookMore) {
            return;
        }
        Log.i("test", "setLookMoreVisible ... " + visible);
        mLookMoreLayout.setVisibility(visible);
        mTxtNoMoreData.setVisibility(GONE);
    }

    private void initView(Context context) {
        mContext = context;
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.lama_listview_footer, null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

        mContentView = moreView.findViewById(R.id.xlistview_footer_content);
        mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
        mTxtNoMoreData = (TextView) moreView.findViewById(R.id.no_more);
        mLookMoreLayout = (LinearLayout) moreView
                .findViewById(R.id.layout_look_more);
        mBtnLookMore = (Button) moreView.findViewById(R.id.btn_look_more);
        mBtnLookMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (null != mListener) {
                    mListener.onClick();
                }
            }
        });
    }

    public void setOnLookMoreOnClickListener(ILookMoreOnClickListener listener) {
        mListener = listener;
    }

    public interface ILookMoreOnClickListener {
        void onClick();
    }
}