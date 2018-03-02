package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaydenxiao.common.R;

/**
 * Created by john on 2018/3/2.
 */

public class CollapsibleTextView extends LinearLayout implements View.OnClickListener {
    /**
     * 最大显示的行数
     */
    private static final int DEFAULT_MAX_LINE_COUNT = 8;
    /**
     * 实际展示的行数
     */
    private static final int DEFAULT_SHOW_LINE_COUNT = 6;


    private static final int COLLAPSIBLE_STATE_NONE = 0;
    /**
     * View处于展开状态
     **/
    private static final int COLLAPSIBLE_STATE_SHRINKUP = 1;
    /**
     * view收缩时状态
     **/
    private static final int COLLAPSIBLE_STATE_SPREAD = 2;
    /**
     * 显示内容的View
     */
    private TextView tv_context;
    /**
     * 展开/收起按钮
     */
    private TextView bt_spread;
    private String shrinkup;
    private String spread;
    /**
     * 当前正处于的状态
     */
// private int mState;
    private boolean flag;
    private int nowType;
    private CollapsibleTextView coTextView;
    /**
     * 判断是不是点击了查看更多、收起
     */
    private boolean isClicke = false;
    private int lookCount = 0;

    public CollapsibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        shrinkup = "收起";
        spread = "查看全文";
        View view = inflate(context, R.layout.collapsible_textview, this);
        view.setPadding(0, -1, 0, 0);
        tv_context = (TextView) view.findViewById(R.id.tv_context);
        bt_spread = (TextView) view.findViewById(R.id.bt_spread);
        bt_spread.setOnClickListener(this);
    }

    public CollapsibleTextView(Context context) {
        this(context, null);
    }

    /**
     * 赋值
     */
    public final void setDesc(CharSequence charSequence,CollapsibleTextView tv, TextView.BufferType bufferType) {
        this.coTextView = tv;
         // 对内容中的网址进行处理;
        tv_context.setAutoLinkMask(Linkify.WEB_URLS);
        tv_context.setMovementMethod(LinkMovementMethod.getInstance());
        tv_context.setText(charSequence, bufferType);
        // 初始类型
        if (lookCount == 0) {
            coTextView.setNowType(COLLAPSIBLE_STATE_SPREAD);
        }
        lookCount += 1;
        // TODO LYL 放到ListView中需要加下句:falg=false;一般情况去掉就可
        flag = false;
        requestLayout();
    }


    @Override
    public void onClick(View v) {
        flag = false;
        isClicke = true;
        requestLayout();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!flag) {
            flag = true;
            if (tv_context.getLineCount() <= DEFAULT_MAX_LINE_COUNT) {
                bt_spread.setVisibility(View.GONE);
                tv_context.setMaxLines(DEFAULT_MAX_LINE_COUNT + 1);
                coTextView.setNowType(COLLAPSIBLE_STATE_NONE);
            } else {
                post(new InnerRunnable());
            }
        }
    }


    class InnerRunnable implements Runnable {
        @Override
        public void run() {
            int zType = 0;
            // 第一次进入操作（没有点击并且是第一次进入）；
            System.out.println("lookCount:" + lookCount);
            if (!isClicke && lookCount == 1) {
                if (coTextView.getNowType() == COLLAPSIBLE_STATE_SPREAD) {
                    tv_context.setMaxLines(DEFAULT_SHOW_LINE_COUNT);
                    bt_spread.setVisibility(View.VISIBLE);
                    bt_spread.setText(spread);
                    zType = COLLAPSIBLE_STATE_SHRINKUP;
                } else if (coTextView.getNowType() == COLLAPSIBLE_STATE_SHRINKUP) {
                    tv_context.setMaxLines(Integer.MAX_VALUE);
                    bt_spread.setVisibility(View.VISIBLE);
                    bt_spread.setText(shrinkup);
                    zType = COLLAPSIBLE_STATE_SPREAD;
                }
                coTextView.setNowType(zType);
                // 点击了查看更多、收起转换状态；
            } else if (isClicke) {
                isClicke = false;
                if (coTextView.getNowType() == COLLAPSIBLE_STATE_SPREAD) {
                    tv_context.setMaxLines(DEFAULT_SHOW_LINE_COUNT);
                    bt_spread.setVisibility(View.VISIBLE);
                    bt_spread.setText(spread);
                    coTextView.setNowType(COLLAPSIBLE_STATE_SHRINKUP);
                } else if (coTextView.getNowType() == COLLAPSIBLE_STATE_SHRINKUP) {
                    tv_context.setMaxLines(Integer.MAX_VALUE);
                    bt_spread.setVisibility(View.VISIBLE);
                    bt_spread.setText(shrinkup);
                    coTextView.setNowType(COLLAPSIBLE_STATE_SPREAD);
                }
                // 滑动listView 从新载入到可见界面 不做操作，保持原有状态；(为了后面看得人能够更理解写上)
            } else if (!isClicke && lookCount != 1) {

            }

        }
    }


    public int getNowType() {
        return nowType;
    }


    public void setNowType(int nowType) {
        this.nowType = nowType;
    }

}
