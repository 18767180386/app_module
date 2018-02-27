package com.aiju.zyb.view.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.my.baselibrary.utils.Utils;

/**
 * Created by AIJU on 2017-05-29.
 */

public class BannerMenuChart extends LinearLayout {

    private int with;
    private Context context;
    private TextView mTextView;
    private BorderLinearLayout mBorderLinearLayout;
    private LayoutInflater mLayoutInflater;

    private onBannerClickListener mListener;

    public interface onBannerClickListener {
        void onBannerClick(int position);
    }

    public BannerMenuChart(Context context, BannerGroup group, onBannerClickListener listener) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mListener = listener;
        init();
        setData(group);
    }

    public BannerMenuChart(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        //mTextView = (TextView) mLayoutInflater.inflate(R.layout.banner_text, this, false);
        mTextView = (TextView) mLayoutInflater.inflate(R.layout.char_text, this, false);
        mBorderLinearLayout = (BorderLinearLayout) mLayoutInflater.inflate(R.layout.banner_border, this, false);
        mBorderLinearLayout.setDividerDrawable(context.getResources().getDrawable(R.drawable.spacer_medium_list));
        //mBorderLinearLayout.setBorderSize(1,1,10,1);
    }

    public void setData(final BannerGroup group) {

        if (group == null || group.getItems() == null) {
            return;
        }

        if (TextUtils.isEmpty(group.getTitle())) {
            ViewGroup.LayoutParams layoutParams = mTextView.getLayoutParams();
            layoutParams.height = Utils.dip2px(getContext(), 15);
        }
        mTextView.setText(group.getTitle());

        addView(mTextView);

        for (final BannerItem item : group.getItems()) {
            View itemView = mLayoutInflater.inflate(R.layout.banner_chart, mBorderLinearLayout, false);

            ImageView head = (ImageView) itemView.findViewById(R.id.iv_banner_item_head);
            ImageView end = (ImageView) itemView.findViewById(R.id.iv_banner_item_end);
            TextView title = (TextView) itemView.findViewById(R.id.tv_banner_item_title);
            TextView right_text=(TextView)itemView.findViewById(R.id.right_text);

            if(item.getLeftImage()>0) {
                head.setImageResource(item.getLeftImage());
                head.setVisibility(View.VISIBLE);
            }else{
                head.setVisibility(View.GONE);
            }
            if(item.getRightImage()>0) {
                end.setImageResource(item.getRightImage());
                end.setVisibility(View.VISIBLE);
            }else{
                end.setVisibility(View.GONE);
            }
            title.setText(item.getItemTitle());

            if(!TextUtils.isEmpty(item.getRightText()))
            {
                if(item.getRightText().equals("发现新版本"))
                {
                    right_text.setTextColor(context.getResources().getColor(R.color.ec_tab_press));
                }else{
                    right_text.setTextColor(context.getResources().getColor(R.color.color_99));
                }
                right_text.setText(item.getRightText());
                right_text.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(item.getItemDetail())) {
                TextView detail = (TextView) itemView.findViewById(R.id.tv_banner_item_detail);
                detail.setVisibility(View.VISIBLE);
                detail.setText(item.getItemDetail());
            } else {
                TextView detail = (TextView) itemView.findViewById(R.id.tv_banner_item_detail);
                detail.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onBannerClick(group.getItems().indexOf(item));
                    }
                }
            });

            mBorderLinearLayout.addView(itemView);
        }

        addView(mBorderLinearLayout);
    }

    public onBannerClickListener getmListener() {
        return mListener;
    }

    public void setonBannerListener(onBannerClickListener mListener) {
        this.mListener = mListener;
    }
}
