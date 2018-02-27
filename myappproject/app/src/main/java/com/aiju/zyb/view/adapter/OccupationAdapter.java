package com.aiju.zyb.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.OccupationCommentInfo;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.Type;
import com.jaydenxiao.common.commonutils.GlideUtils;
import com.my.baselibrary.utils.GlideTool;
import com.my.baselibrary.utils.HLog;
import com.my.baselibrary.utils.SettingUtil;

import java.util.List;

/**
 * Created by john on 2018/2/24.
 */

public class OccupationAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<OccupationCommentInfo> list;
    private Context context;
    private Type type;
    private int width = ((SettingUtil.getDisplaywidthPixels() / 4) * 3 - SettingUtil.dip2px(50)) / 2;

    public OccupationAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    public void setList(List<OccupationCommentInfo> list) {
        this.list = list;
    }

    public void setData(List<OccupationCommentInfo> datas) {
        list= datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<OccupationCommentInfo> datas) {
        list.addAll(datas);
        notifyDataSetChanged();
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
            convertView = mInflater.inflate(R.layout.occupationitemadapter, null);
             view.user_logo = (ImageView) convertView.findViewById(R.id.user_logo);
             view.user_name = (TextView) convertView.findViewById(R.id.user_name);
             view.parse_num = (TextView) convertView.findViewById(R.id.parse_num);
             view.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
             view.comment_info = (TextView) convertView.findViewById(R.id.comment_info);
             convertView.setTag(view);
        } else {
            view = (MyView) convertView.getTag();
        }
        OccupationCommentInfo item = list.get(position);
        if (item != null) {
            HLog.w("net_url",item.getUserInfo().getUserImg());
            GlideUtils.getCicleImg(context, item.getUserInfo().getUserImg(), view.user_logo);
            view.user_name.setText(item.getUserInfo().getNickName());
            view.parse_num.setText(item.getCommentNum()+"");
            view.comment_content.setText(item.getCommentContent());
            view.comment_info.setText(item.getCommentTime()+"");
        }
        return convertView;
    }


    private class MyView {
        private ImageView user_logo;
        private TextView user_name;
        private TextView parse_num;
        private TextView comment_content;
        private TextView comment_info;

    }
}