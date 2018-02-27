package com.aiju.zyb.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.view.viewholder.MyRecyclerViewHolder;
import com.my.baselibrary.utils.GlideTool;
import com.my.baselibrary.utils.SettingUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AIJU on 2017-04-30.
 */

public class IndexChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ViewStatus.LoadStatus mLoadStatus = ViewStatus.LoadStatus.DEFAULT_STATUS;//上拉加载的状态
    private static final int VIEW_TYPE_FOOTER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private List<TaokeItemBean> mList;
    private Context mContext;
    public LayoutInflater mLayoutInflater;
    private int width= (SettingUtil.getDisplaywidthPixels()-SettingUtil.dip2px(20))/2;
    private int height=width;
    private  int showType=0;

    public IndexChildAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     *
     * 展示类型
     * @param type
     */
    public  void setShowType(int type)
    {
        this.showType=type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        } else if (viewType == VIEW_TYPE_ITEM) {
            return onCreateItemViewHolder(parent, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                onBindItemViewHolder(holder, position);
                break;
            case VIEW_TYPE_FOOTER:
                onBindFooterViewHolder(holder, position, mLoadStatus);
                break;
            default:
                break;
        }
    }

    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.footer_layout, parent,false);
        return new FooterViewHolder(view);
    }

    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(showType==0?R.layout.indexchildadapteritem:R.layout.indexprolistgriditem,parent,false);
        return new MyRecyclerViewHolder(view,showType);
    }

    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position, ViewStatus.LoadStatus loadStatus) {

        FooterViewHolder viewHolder = (FooterViewHolder) holder;
        switch (loadStatus) {
            case CLICK_LOAD_MORE:
                viewHolder.mLoadingLayout.setVisibility(View.GONE);
                viewHolder.mClickLoad.setVisibility(View.VISIBLE);
                break;
            case LOADING_MORE:
                viewHolder.mLoadingLayout.setVisibility(View.VISIBLE);
                viewHolder.mClickLoad.setVisibility(View.GONE);
                break;
            case DEFAULT_STATUS:
                viewHolder.mLoadingLayout.setVisibility(View.GONE);
                viewHolder.mClickLoad.setVisibility(View.GONE);
                break;
        }
    }

    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyRecyclerViewHolder viewHolder = (MyRecyclerViewHolder) holder;
        try {
            TaokeItemBean taoke=mList.get(position);
            viewHolder.pro_name.setText(taoke.getTitle());
            viewHolder.pro_price.setText("￥"+taoke.getZk_final_price());
            viewHolder.old_price.setText("￥"+taoke.getReserve_price());
            viewHolder.old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
            if(showType==1)
            {
             //  viewHolder.pro_image.getLayoutParams().width=width;
              // viewHolder.pro_image.getLayoutParams().height=height;
            }
            GlideTool.glideImageLoad(mContext,taoke.getPict_url(),viewHolder.pro_image,R.mipmap.ic_default);
          //  if(TextUtils.isEmpty(viewHolder.pro_name.getTag().toString()) || (!TextUtils.isEmpty(viewHolder.pro_name.getTag().toString()) && !taoke.getPict_url().equals(viewHolder.pro_name.getTag().toString()))) {

              //  viewHolder.pro_image.setTag(taoke.getPict_url());

          //  }

            viewHolder.pro_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener!=null)
                    {
                        mOnItemClickListener.onItemClick(v,position);
                    }
                }
            });




        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return mList.size() + 1;
    }

    public TaokeItemBean getItem(int position) {

        return mList.get(position);
    }

    public void addAll(List<TaokeItemBean> list) {

        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if (position + 1 == getItemCount()) {//最后一条为FooterView
            return VIEW_TYPE_FOOTER;
        }
        return VIEW_TYPE_ITEM;
    }

    public void reSetData(List<TaokeItemBean> list) {

        this.mList = list;
        notifyDataSetChanged();
    }

    public void setLoadStatus(ViewStatus.LoadStatus loadStatus) {

        this.mLoadStatus = loadStatus;
    }
}


class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextView;

    public ItemViewHolder(View itemView) {

        super(itemView);
        //mTextView = (TextView) itemView.findViewById(R.id.textView);
    }
}

class FooterViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout mLoadingLayout;
    public TextView mClickLoad;

    public FooterViewHolder(View itemView) {

        super(itemView);
        mLoadingLayout = (LinearLayout) itemView.findViewById(R.id.loading);
        mClickLoad = (TextView) itemView.findViewById(R.id.click_load_txt);
        mClickLoad.setOnClickListener(new View.OnClickListener() {//添加点击加载更多监听
            @Override
            public void onClick(View v) {

                // loadMore();
            }
        });
    }
}
