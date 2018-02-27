package com.aiju.zyb.view.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aiju.zyb.R;
import com.aiju.zyb.view.BaseFragment;
/*
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
*/

import java.util.HashMap;
import java.util.Map;


public class ShopCartFragment extends BaseFragment {
    private int orderType = 0;//订单页面参数，仅在H5方式下有效
   // private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;//yhhpass参数
    private WebView webView;
    public ShopCartFragment() {
        // Required empty public constructor
    }


    public static ShopCartFragment newInstance() {
        ShopCartFragment fragment = new ShopCartFragment();
        return fragment;
    }

    @Override
    protected   void  initView(View view)
    {
        hideBack(0);
        webView = (WebView)view.findViewById(R.id.webView);
      //  alibcShowParams = new AlibcShowParams(OpenType.H5, false);
        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改


     //   AlibcBasePage alibcBasePage = new AlibcMyCartsPage();
        //AlibcTrade.show(getActivity(), alibcBasePage, alibcShowParams, null, exParams, new DemoTradeCallback());

      //  AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.H5, false);
       // AlibcTrade.show(ProductDetailActivity.this,webView, null, null,alibcBasePage,alibcShowParams,alibcTaokeParams,exParams, new DemoTradeCallback());

        //实例化我的购物车打开page

        //initCar();
    }



    public  void  initCar()
    {
       // AlibcBasePage myCartsPage = new AlibcMyCartsPage();

        /**
         * 打开电商组件,支持使用外部webview
         *
         * @param activity             必填
         * @param webView              外部 webView
         * @param webViewClient        webview的webViewClient
         * @param webChromeClient      webChromeClient客户端
         * @param tradePage            页面类型,必填，不可为null，详情见下面tradePage类型介绍
         * @param showParams           show参数
         * @param taokeParams          淘客参数
         * @param trackParam           yhhpass参数
         * @param tradeProcessCallback 交易流程的回调，必填，不允许为null；
         * @return 0标识跳转到手淘打开了,1标识用h5打开,-1标识出错
         */
       // AlibcTrade.show(getActivity(), webView, webViewClient, null, myCartsPage, alibcShowParams, null, exParams, new DemoTradeCallback());


    }



    public void showMyCartsPage(View view){

    }


    WebViewClient webViewClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (url.startsWith("http") || url.startsWith("https")) {
                return super.shouldInterceptRequest(view, url);
            } else {
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(in);
                return null;
            }
        }
    };


    WebChromeClient  webChromeClient=new WebChromeClient(){
        //获取网站标题
        @Override
        public void onReceivedTitle(WebView view, String title) {

        }


        //获取加载进度
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }
    };




    protected void showWebView(String url) {
       // webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //WebView加载web资源
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) {
                    return super.shouldInterceptRequest(view, url);
                } else {
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
                    return null;
                }
            }

        });

        //设置WebChromeClient类
        webView.setWebChromeClient(new WebChromeClient() {


            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("标题在这里");
                //mtitle.setText(title);
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    //loading.setText(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    //loading.setText(progress);
                }
            }
        });

    }


    /**
     *
     * 返回webview
     */
    public  boolean goBack()
    {
        if(webView==null)
        {
            return  false;
        }
        if(webView.canGoBack())
        {
            webView.goBack();//返回上一页面
            return true;
        }
        else
        {
           return  false;
        }
    }


    @Override
    protected   void  initListener()
    {

    }

    @Override
    protected  void initData()
    {

    }
    @Override
    protected String getTextTitle() {
        return "购物车";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_cart;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        //调用了AlibcTrade.show方法的Activity都需要调用AlibcTradeSDK.destory()
        //AlibcTradeSDK.destory();
        super.onDestroy();
    }

    @Override
   public void onStart()
   {
       super.onStart();
   }


   @Override
   public  void onResume()
   {
       super.onResume();
       initCar();
   }

}
