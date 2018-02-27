package com.aiju.zyb.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.my.baselibrary.utils.SettingUtil;

/**
 * Created by AIJU on 2017-04-30.
 */

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
    private int type=0;
    public TextView pro_name;
    public TextView pro_price;
    public TextView old_price;
    public ImageView pro_image;
    public RelativeLayout index_item_re;
    private int width= (SettingUtil.getDisplaywidthPixels()-SettingUtil.dip2px(20))/2;
    private int height=width;

    public MyRecyclerViewHolder(View itemView,int type) {
        super(itemView);
        this.type=type;
        pro_name= (TextView) itemView.findViewById(R.id.pro_name);
        pro_price=(TextView)itemView.findViewById(R.id.pro_price);
        old_price=(TextView)itemView.findViewById(R.id.old_price);
        pro_image=(ImageView)itemView.findViewById(R.id.pro_img);
        index_item_re=(RelativeLayout)itemView.findViewById(R.id.index_item_re);
        if(type==1)
        {
           pro_image.getLayoutParams().width=width;
           pro_image.getLayoutParams().height=height;
        }


    }
}