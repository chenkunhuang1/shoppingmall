package com.example.chen.shoppingmail.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chen.shoppingmail.R;
import com.example.chen.shoppingmail.home.bean.ResultBeanData;
import com.example.chen.shoppingmail.utils.Constants;

import java.util.List;

/**
 * Created by chen on 2017/10/20.
 * 作用：
 */

public class HotGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.HotInfoBean> datas;

    public HotGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
        this.mContext = mContext;
        this.datas = hot_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_hot_grid_view,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_hot = (ImageView) convertView.findViewById(R.id.iv_hot);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据位置得到数据
        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = datas.get(position);
        Glide.with(mContext).load(Constants.IMAGE_URL+hotInfoBean.getFigure())
                .into(viewHolder.iv_hot);
        viewHolder.tv_name.setText(hotInfoBean.getName());
        viewHolder.tv_price.setText("￥"+hotInfoBean.getCover_price());
        return convertView;
    }
    static class ViewHolder{
        ImageView iv_hot;
        TextView tv_name;
        TextView tv_price;

    }

}

