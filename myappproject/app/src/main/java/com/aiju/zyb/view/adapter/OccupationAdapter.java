package com.aiju.zyb.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.OccupationCommentInfo;
import com.aiju.zyb.bean.OccupationReplyInfo;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.Type;
import com.jaydenxiao.common.commonutils.CollapsibleTextView;
import com.jaydenxiao.common.commonutils.GlideUtils;
import com.jaydenxiao.common.commonutils.TimeUtil;
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
             view.comment_li=(LinearLayout)convertView.findViewById(R.id.comment_li);
             view.tv=(CollapsibleTextView)convertView.findViewById(R.id.collaps_textview);
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
         //   view.comment_content.setText(item.getCommentContent());\
            view.tv.setDesc(item.getCommentContent(), view.tv, TextView.BufferType.NORMAL);
            view.comment_info.setText(TimeUtil.friendly_time(TimeUtil.stampToDate(item.getCommentTime()+""))+"");
            if(item.getReplyInfoList()!=null && item.getReplyInfoList().size()>0)
            {
                view.comment_li.removeAllViews();
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                List<OccupationReplyInfo> list=item.getReplyInfoList();
                TextView moreText=null;
                if(list.size()==5)
                {
                    list=list.subList(0,5);
                    moreText=new TextView(context);
                    moreText.setText("查看全部"+list.size()+"条回复");
                    moreText.setTextSize(14);
                    moreText.setTextColor(context.getResources().getColor(R.color.comment_txt));
                }
                for (OccupationReplyInfo m:list)
                {
                    TextView textView=new TextView(context);
                    textView.setText(m.getReplyContent());
                    textView.setTextColor(context.getResources().getColor(R.color.color_33));
                    textView.setTextSize(14);
                    view.comment_li.addView(textView);
                }
                if(moreText!=null)
                {
                    view.comment_li.addView(moreText);
                }
                view.comment_li.setVisibility(View.VISIBLE);
            }else{
                view.comment_li.setVisibility(View.GONE);
            }
        }
        return convertView;
    }


    private class MyView {
        private ImageView user_logo;
        private TextView user_name;
        private TextView parse_num;
        private TextView comment_content;
        private TextView comment_info;
        private LinearLayout comment_li;
        private CollapsibleTextView tv;

    }
}