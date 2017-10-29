package com.example.chen.shoppingmail.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chen.shoppingmail.R;
import com.example.chen.shoppingmail.home.bean.GoodsBean;
import com.example.chen.shoppingmail.shoppingcart.utils.CartStorage;
import com.example.chen.shoppingmail.shoppingcart.view.AddSubView;
import com.example.chen.shoppingmail.utils.Constants;

import java.util.List;

/**
 * Created by chen on 2017/10/29.
 * 作用：
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private final Context mContext;
    private final List<GoodsBean> datas;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;
    private final CheckBox cbAll;


    public ShoppingCartAdapter(Context mContext, List<GoodsBean> datas, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox cbAll) {
        this.mContext = mContext;
        this.datas = datas;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        showTotalPrice();
        //设置点击事件
        setListener();
        //校验是否全选
        checkAll();
    }
    //设置checkBox的点击事件


    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //根据位置找到bean对象
                GoodsBean goodsbean = datas.get(position);
                //设置取反状态
                goodsbean.setChecked(!goodsbean.isChecked());
                //刷新数据
                notifyItemChanged(position);
                //校验是否全选
                checkAll();
                //重新计算价格
                showTotalPrice();

            }
        });
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到状态
                boolean isCheck = checkboxAll.isChecked();
                //根据状态设置全选和非全选
                checkAll_none(isCheck);
                //计算总价格
                showTotalPrice();
            }
        });
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到状态
                boolean isCheck = cbAll.isChecked();
                //根据状态设置全选和非全选
                checkAll_none(isCheck);

            }
        });
    }

    /**
     * 设置全选和非全选
     * @param isCheck
     */

    public void checkAll_none(boolean isCheck) {
        if (datas != null && datas.size() > 0){
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsbean = datas.get(i);
                goodsbean.setChecked(isCheck);
                notifyItemChanged(i);
            }

        }
    }

    public void checkAll() {
        if (datas != null && datas.size() > 0){
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsbean = datas.get(i);
                if (!goodsbean.isChecked()){
                    //非全选
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                }else {
                    //选中
                    number ++;
                }
            }
            if (number == datas.size()){
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }
        }else {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);

        }
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("合计："+getTotalPrice());
    }

    /**
     * 获得总价格
     * @return
     */

    private double getTotalPrice() {
        double totalPrice = 0.00;
        if (datas != null && datas.size() > 0){
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodbean = datas.get(i);
                if (goodbean.isChecked()){
                    totalPrice = totalPrice + Double.valueOf(goodbean.getNumber()) * Double.valueOf(goodbean.getCover_price());
                }
            }
        }
        return totalPrice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_shop_cart,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //根据位置得到对应的Bean对象
        final GoodsBean goodsBean = datas.get(position);
        //设置数据
        holder.cb_gov.setChecked(goodsBean.isChecked());
        Glide.with(mContext).load(Constants.IMAGE_URL+goodsBean.getFigure())
                .into(holder.iv_gov);
        holder.tv_desc_gov.setText(goodsBean.getName());
        holder.tv_price_gov.setText("￥"+goodsBean.getCover_price());
        holder.addSubView.setValue(goodsBean.getNumber());
        holder.addSubView.setMinValue(1);
        holder.addSubView.setMaxValue(8);
        //设置商品数量变化的监听
        holder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void OnNumberChange(int value) {
                goodsBean.setNumber(value);
                //本地更新
                CartStorage.getInstance().updateDate(goodsBean);
                //刷新设配器
                notifyItemChanged(position);
                //再次计算价格
                showTotalPrice();
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void deleteData() {
        if (datas != null && datas.size() > 0){
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsbean= datas.get(i);
                if (goodsbean.isChecked()){
                    //从内存中移除数据
                    datas.remove(goodsbean);
                    //刷新数据
                    CartStorage.getInstance().deleteDate(goodsbean);
                    //保存到本地
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private AddSubView addSubView;

        public ViewHolder(View itemView) {
            super(itemView);
            cb_gov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            iv_gov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tv_price_gov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            tv_desc_gov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            addSubView = (com.example.chen.shoppingmail.shoppingcart.view.AddSubView) itemView.findViewById(R.id.AddSubView);
            //设置item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.OnItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * 点击item的监听者
     */
    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;
}
