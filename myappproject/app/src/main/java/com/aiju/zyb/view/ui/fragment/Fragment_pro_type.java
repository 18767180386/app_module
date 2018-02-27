package com.aiju.zyb.view.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aiju.zyb.R;
import com.aiju.zyb.bean.TaokeItemBean;
import com.aiju.zyb.bean.TaokeProSort;
import com.aiju.zyb.bean.Type;
import com.aiju.zyb.bean.base.ResultPageList;
import com.aiju.zyb.presenter.TaokePresenter;
import com.aiju.zyb.view.adapter.Pro_type_adapter;
import com.aiju.zyb.view.ui.ISortView;

import java.util.ArrayList;

/**
 * Created by AIJU on 2017-05-02.
 */

public class Fragment_pro_type extends Fragment  implements ISortView{
    private ArrayList<Type> list;
    private ImageView hint_img;
    private GridView listView;
    private Pro_type_adapter adapter;
    private Type type;
    private ProgressBar progressBar;
    private String typename;
    private int sortType=0;
    private TaokePresenter taokePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro_type, null);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        hint_img=(ImageView) view.findViewById(R.id.hint_img);
        listView = (GridView) view.findViewById(R.id.listView);
        typename=getArguments().getString("typename");
        sortType=getArguments().getInt("type",0);
        ((TextView)view.findViewById(R.id.toptype)).setText(typename);
        taokePresenter=new TaokePresenter(this);
        taokePresenter.getProListByType(getActivity(),sortType,20);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
                if(adapter!=null)
                {
                    showDetail(((TaokeItemBean)adapter.getItem(position)).getNum_iid());
                }
            }
        });

        return view;
    }

    /**
     *
     * 商品详情
     * @param itemId
     */
    public void showDetail(String  itemId) {

        /*
        AlibcBasePage alibcBasePage=null;
        alibcBasePage = new AlibcDetailPage(itemId);
        if (isTaoke) {
            alibcTaokeParams = new AlibcTaokeParams("mm_26632322_6858406_23810104", "", "");
        } else {
            alibcTaokeParams = null;
        }
        */
        //Intent intent=new Intent(getActivity(),ProductDetailActivity.class);
       // intent.putExtra("taokeid",itemId);
      //  intent.putExtra("istaoke",true);
       // startActivity(intent);
        // startActivity(new Intent(getActivity(), ProductDetailActivity.class));
        // AlibcTrade.show(getActivity(), alibcBasePage, alibcShowParams, alibcTaokeParams, exParams , new DemoTradeCallback());
    }


    @Override
    public void getProSortList(ResultPageList<TaokeProSort> result)
    {
       // progressBar.setVisibility(View.GONE);

    }

    @Override
    public void getProListByType(ResultPageList<TaokeItemBean> result)
    {

         if(result.getCode().equals("000"))
         {
             progressBar.setVisibility(View.GONE);
             if(result.getData()!=null && result.getData().size()>0)
             {
                 adapter = new Pro_type_adapter(getActivity(),result.getData());
                 listView.setAdapter(adapter);
             }
         }
    }

}