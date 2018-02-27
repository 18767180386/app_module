package com.aiju.zyb.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.Type;
import com.my.baselibrary.utils.GlideTool;
import com.my.baselibrary.utils.SettingUtil;

import java.util.List;

/**
 * Created by AIJU on 2017-05-02.
 */

public class Pro_type_adapter  extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<TaokeItemBean> list;
    private Context context;
    private Type type;
    private int  width= ((SettingUtil.getDisplaywidthPixels()/4)*3-SettingUtil.dip2px(50))/2;

    public Pro_type_adapter(Context context, List<TaokeItemBean> list) {
        this.context=context;
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }


    public  void  setList(List<TaokeItemBean> list)
    {
        this.list=list;
    }


    @Override
    public int getCount() {
        if (list != null && list.size() > 0)
            return list.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyView view;
        if (convertView == null) {
            view = new MyView();
            convertView = mInflater.inflate(R.layout.list_pro_type_item, null);
            view.pro_img = (ImageView) convertView.findViewById(R.id.pro_img);
           // view.name = (TextView) convertView.findViewById(R.id.text);
            view.pro_img.getLayoutParams().width=width;
            view.pro_img.getLayoutParams().height=width;
            convertView.setTag(view);
        } else {
            view = (MyView) convertView.getTag();
        }
        TaokeItemBean item=list.get(position);
        if(item!=null)
        {
            GlideTool.glideImageLoad(context,item.getPict_url(),view.pro_img,R.color.bgcolor);
        }
        return convertView;
    }


    private class MyView {
        private ImageView pro_img;
        private TextView name;
    }


}