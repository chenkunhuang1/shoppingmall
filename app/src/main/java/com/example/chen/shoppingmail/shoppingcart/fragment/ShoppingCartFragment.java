package com.example.chen.shoppingmail.shoppingcart.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chen.shoppingmail.R;
import com.example.chen.shoppingmail.base.BaseFragment;
import com.example.chen.shoppingmail.home.bean.GoodsBean;
import com.example.chen.shoppingmail.shoppingcart.adapter.ShoppingCartAdapter;
import com.example.chen.shoppingmail.shoppingcart.utils.CartStorage;

import java.util.List;

/**
 * Created by chen on 2017/10/16.
 * 作用：
 */

public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener {
    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;
    private ImageView ivEmpty;
    private TextView tvEmptyCartTobuy;
    private LinearLayout ll_empty_shopcart;
    private ShoppingCartAdapter mShoppingCartAdapter;

    /**
     *  编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     *  完成状态
     */
    private static final int ACTION_COMPLETE = 1;
     

    @Override
    public void onClick(View v) {
        if ( v == btnCheckOut ) {
            // Handle clicks for btnCheckOut
        } else if ( v == btnDelete ) {
            // 删除选中
            mShoppingCartAdapter.deleteData();
            //校验状态
            mShoppingCartAdapter.checkAll();
            //数据大小为0
            if (mShoppingCartAdapter.getItemCount() == 0){
                emptyShoppingCart();
            }
        } else if ( v == btnCollection ) {
            // Handle clicks for btnCollection
        }
    }


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart,null);
        tvShopcartEdit = (TextView)view.findViewById( R.id.tv_shopcart_edit );
        recyclerview = (RecyclerView)view.findViewById( R.id.recyclerview );
        llCheckAll = (LinearLayout)view.findViewById( R.id.ll_check_all );
        checkboxAll = (CheckBox)view.findViewById( R.id.checkbox_all );
        tvShopcartTotal = (TextView)view.findViewById( R.id.tv_shopcart_total );
        btnCheckOut = (Button)view.findViewById( R.id.btn_check_out );
        llDelete = (LinearLayout)view.findViewById( R.id.ll_delete );
        cbAll = (CheckBox)view.findViewById( R.id.cb_all );
        btnDelete = (Button)view.findViewById( R.id.btn_delete );
        btnCollection = (Button)view.findViewById( R.id.btn_collection );
        ivEmpty = (ImageView)view.findViewById( R.id.iv_empty );
        tvEmptyCartTobuy = (TextView)view.findViewById( R.id.tv_empty_cart_tobuy );
        ll_empty_shopcart = (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);
        btnCheckOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCollection.setOnClickListener(this);
        
        initListener();
        return view;
    }

    private void initListener() {
        //设置默认的编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action = (int) tvShopcartEdit.getTag();
                if (action == ACTION_EDIT){
                    //切换为完成状态
                    showDelete();
                }else {
                    //切换为编辑状态
                    hideDelete();
                }
            }
        });

    }

    private void hideDelete() {
        //设置状态和文本 -编辑
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        //变为全勾选
        if(mShoppingCartAdapter != null){
            mShoppingCartAdapter.checkAll_none(true);
            mShoppingCartAdapter.checkAll();
            mShoppingCartAdapter.showTotalPrice();
        }
        //隐藏删除按钮
        llDelete.setVisibility(View.GONE);
        //显示结算视图
        llCheckAll.setVisibility(View.VISIBLE);

    }

    private void showDelete() {
        //设置状态和文本 -完成
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");
        //变成非勾选
        if(mShoppingCartAdapter != null){
            mShoppingCartAdapter.checkAll_none(false);
            mShoppingCartAdapter.checkAll();
        }
        //显示删除按钮
        llDelete.setVisibility(View.VISIBLE);
        //隐藏结算视图
        llCheckAll.setVisibility(View.GONE);

    }

    @Override
    public void initData() {

        super.initData();


    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    /**
     * 数据的展现
     */

    private void showData() {
        List<GoodsBean> list = CartStorage.getInstance().getAllData();
        if(list != null && list.size() > 0){
            //有数据
            tvShopcartEdit.setVisibility(View.VISIBLE);
            llCheckAll.setVisibility(View.VISIBLE);
            //隐藏没有数据的布局
            ll_empty_shopcart.setVisibility(View.GONE);
            //设置设配器
            mShoppingCartAdapter = new ShoppingCartAdapter(mContext,list,tvShopcartTotal,checkboxAll,cbAll);
            recyclerview.setAdapter(mShoppingCartAdapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

        }else {
            //没数据
            //显示没有数据的设配器
            emptyShoppingCart();
        }
    }

    private void emptyShoppingCart() {
        ll_empty_shopcart.setVisibility(View.VISIBLE);
        tvShopcartEdit.setVisibility(View.GONE);
        llDelete.setVisibility(View.GONE);
    }
}
