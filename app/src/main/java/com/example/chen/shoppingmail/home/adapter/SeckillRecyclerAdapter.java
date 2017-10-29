package com.example.chen.shoppingmail.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chen.shoppingmail.R;
import com.example.chen.shoppingmail.home.bean.ResultBeanData;
import com.example.chen.shoppingmail.utils.Constants;

import java.util.List;

/**
 * Created by chen on 2017/10/19.
 * 作用： 秒杀的设配器
 */

public class SeckillRecyclerAdapter extends RecyclerView.Adapter<SeckillRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillRecyclerAdapter(Context context, List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = View.inflate(context, R.layout.item_seckill, null);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //根据位置得到对应的数据
        ResultBeanData.ResultBean.SeckillInfoBean.ListBean listbean = list.get(position);
        //绑定数据
        Glide.with(context).load(Constants.IMAGE_URL + listbean.getFigure())
                .into(holder.iv_figure);
        holder.tv_cover_price.setText(listbean.getCover_price());
        holder.tv_origin_price.setText(listbean.getOrigin_price());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_figure;
        private TextView tv_cover_price;
        private TextView tv_origin_price;

        public ViewHolder(final View itemView) {
            super(itemView);
            iv_figure = (ImageView) itemView.findViewById(R.id.iv_figure);
            tv_cover_price = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price = (TextView) itemView.findViewById(R.id.tv_origin_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, "秒杀="+getLayoutPosition(), Toast.LENGTH_SHORT).show();
                    if (onSeckillRecyclerView != null) {
                        onSeckillRecyclerView.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * 监听器
     */
    public interface OnSeckillRecyclerView {
        /**
         * 当某条被点击的时候回调
         *
         * @param position
         */
        public void onItemClick(int position);
    }

    private OnSeckillRecyclerView onSeckillRecyclerView;

    /**
     * 设置item的监听
     *
     * @param onSeckillRecyclerView
     */
    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }
}
