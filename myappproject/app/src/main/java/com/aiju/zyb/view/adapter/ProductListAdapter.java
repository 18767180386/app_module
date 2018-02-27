package com.aiju.zyb.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.view.viewholder.MyRecyclerViewHolder;
import com.my.baselibrary.utils.GlideTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AIJU on 2017-06-01.
 */

public class ProductListAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    public List<TaokeItemBean> mValues=new ArrayList<>();
    private boolean mIsStagger;

    public ProductListAdapter(Context context) {
        this.context = context;
    }


    public void switchMode(boolean mIsStagger) {
        this.mIsStagger = mIsStagger;
    }

    public void setData(List<TaokeItemBean> datas) {
        mValues = datas;
    }

    public void addDatas(List<TaokeItemBean> datas) {
        mValues.addAll(datas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.indexprolistgriditem, parent, false);
        return new MyRecyclerViewHolder(view,1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyRecyclerViewHolder viewHolder = (MyRecyclerViewHolder) holder;
            TaokeItemBean taoke=mValues.get(position);
            viewHolder.pro_name.setText(taoke.getTitle());
            viewHolder.pro_price.setText("￥"+taoke.getZk_final_price());
            viewHolder.old_price.setText("￥"+taoke.getReserve_price());
            viewHolder.old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰

            GlideTool.glideImageLoad(context,taoke.getPict_url(),viewHolder.pro_image,R.color.bgcolor);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener!=null)
                    {
                        mOnItemClickListener.onItemClick(v,position);
                    }
                }
            });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}