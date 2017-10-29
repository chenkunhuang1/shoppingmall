package com.example.chen.shoppingmail.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chen.shoppingmail.R;
import com.example.chen.shoppingmail.home.bean.GoodsBean;
import com.example.chen.shoppingmail.shoppingcart.utils.CartStorage;
import com.example.chen.shoppingmail.utils.Constants;

public class GoodsInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private LinearLayout ll_root;
    private TextView tv_more_share;
    private TextView tv_more_search;
    private TextView tv_more_home;
    private Button btn_more;
    private GoodsBean goodsBean;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-10-21 09:12:10 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        tv_more_share = (TextView) findViewById(R.id.tv_more_share);
        tv_more_search = (TextView) findViewById(R.id.tv_more_search);
        tv_more_home = (TextView) findViewById(R.id.tv_more_home);
        btn_more = (Button) findViewById(R.id.btn_more);
        ibGoodInfoBack = (ImageButton)findViewById( R.id.ib_good_info_back );
        ibGoodInfoMore = (ImageButton)findViewById( R.id.ib_good_info_more );
        ivGoodInfoImage = (ImageView)findViewById( R.id.iv_good_info_image );
        tvGoodInfoName = (TextView)findViewById( R.id.tv_good_info_name );
        tvGoodInfoDesc = (TextView)findViewById( R.id.tv_good_info_desc );
        tvGoodInfoPrice = (TextView)findViewById( R.id.tv_good_info_price );
        tvGoodInfoStore = (TextView)findViewById( R.id.tv_good_info_store );
        tvGoodInfoStyle = (TextView)findViewById( R.id.tv_good_info_style );
        wbGoodInfoMore = (WebView)findViewById( R.id.wb_good_info_more );
        llGoodsRoot = (LinearLayout)findViewById( R.id.ll_goods_root );
        tvGoodInfoCallcenter = (TextView)findViewById( R.id.tv_good_info_callcenter );
        tvGoodInfoCollection = (TextView)findViewById( R.id.tv_good_info_collection );
        tvGoodInfoCart = (TextView)findViewById( R.id.tv_good_info_cart );
        btnGoodInfoAddcart = (Button)findViewById( R.id.btn_good_info_addcart );

        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);

        tvGoodInfoCallcenter.setOnClickListener(this);
        tvGoodInfoCollection.setOnClickListener(this);
        tvGoodInfoCart.setOnClickListener(this);

        tv_more_share.setOnClickListener(this);
        tv_more_search.setOnClickListener(this);
        tv_more_home.setOnClickListener(this);
        btn_more.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-10-21 09:12:10 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == ibGoodInfoBack ) {
            finish();
            // Handle clicks for ibGoodInfoBack
        } else if ( v == ibGoodInfoMore ) {
            ll_root.setVisibility(View.VISIBLE);
            // Handle clicks for ibGoodInfoMore
        } else if ( v == btnGoodInfoAddcart ) {
            CartStorage.getInstance().addDate(goodsBean);
            Toast.makeText(this,"添加到购物车成功",Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }else if ( v == tvGoodInfoCallcenter ) {
            Toast.makeText(this,"客服中心",Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }else if ( v == tvGoodInfoCollection ) {
            Toast.makeText(this,"收藏",Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }else if ( v == tvGoodInfoCart ) {
            Toast.makeText(this,"购物车",Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }else if ( v == tvGoodInfoCart ) {
            Toast.makeText(this,"分享",Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }else if ( v == tvGoodInfoCart ) {
            Toast.makeText(this,"搜索",Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }else if ( v == tvGoodInfoCart ) {
            Toast.makeText(this,"主页面",Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        findViews();
        //接收数据
        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goodsBean");
        if (goodsBean != null){
            //ToastHelper.showToast(this,"getBean"+goodsBean.toString(),Toast.LENGTH_SHORT);
            setDataForView(goodsBean);
        }
    }

    /**
     * 设置数据
     * @param goodsBean
     */

    private void setDataForView(GoodsBean goodsBean) {
        //设置图片
        Glide.with(this).load(Constants.IMAGE_URL+goodsBean.getFigure()).into(ivGoodInfoImage);
        //设置名称
        tvGoodInfoName.setText(goodsBean.getName());
        //设置价格
        tvGoodInfoPrice.setText("￥"+goodsBean.getCover_price());
        setWebViewData(goodsBean.getProduct_id());
    }

    private void setWebViewData(String product_id) {
        if (product_id != null){
            wbGoodInfoMore.loadUrl("http://www.baidu.com");
            WebSettings settings = wbGoodInfoMore.getSettings();
            //设置支持双击变大变小
            settings.setUseWideViewPort(true);
            //设置支持javascript
            settings.setJavaScriptEnabled(true);
            //设置优先使用缓存
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            wbGoodInfoMore.setWebViewClient(new WebViewClient(){
                @Override
                public boolean  shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是 true 的时候控制去 WebView 打开，为 false 调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.loadUrl(request.getUrl().toString());
                    }
                    return true;
                }
            });
        }
    }
}
