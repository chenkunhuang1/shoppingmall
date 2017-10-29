package com.example.chen.shoppingmail.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chen.shoppingmail.R;
import com.example.chen.shoppingmail.app.GoodsInfoActivity;
import com.example.chen.shoppingmail.home.bean.GoodsBean;
import com.example.chen.shoppingmail.home.bean.ResultBeanData;
import com.example.chen.shoppingmail.utils.Constants;
import com.example.chen.shoppingmail.utils.ToastHelper;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen on 2017/10/16.
 * 作用：
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {
    /**
     * 广告条幅
     */
    public static final int BANNER = 0;
    /**
     *  频道
     */
    public static final int CHANNEL = 1;
    /**
     *  活动
     */
    public static final int ACT = 2;
    /**
     *  秒杀
     */
    public static final int SECKILL = 3;
    /**
     *  推荐
     */
    public static final int RECOMMEND = 4;
    /**
     *  热卖
     */
    public static final int HOT = 5;
    private static final String GOODS_BEAN = "goodsBean";
    private Context mContext;
    private ResultBeanData.ResultBean result;
    /**
     * 当前类型
     */
    private int currentType = BANNER;
    private final LayoutInflater inflater;

    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean result) {
        this.mContext = mContext;
        this.result = result;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 相当于getview
     * 创建ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER){
            return new BannerViewHolder(mContext,inflater.inflate(R.layout.banner_viewpager,null));
        }else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mContext, inflater.inflate(R.layout.channel_item, null));
        }else if (viewType == ACT) {
            return new ActViewHolder(mContext, inflater.inflate(R.layout.act_item, null));
        }else if (viewType == SECKILL) {
            return new SeckillViewHolder(mContext, inflater.inflate(R.layout.seckill_item, null));
        }else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mContext, inflater.inflate(R.layout.recommend_item, null));
        }else if (viewType == HOT) {
            return new HotViewHolder(mContext, inflater.inflate(R.layout.hot_item, null));
        }
        return null;

    }
    class HotViewHolder extends RecyclerView.ViewHolder{

        private final Context mContext;
        private TextView tv_more_hot;
        private GridView gv_hot;
        private HotGridViewAdapter adapter;

        public HotViewHolder(final Context mContext, View itemview) {
            super(itemview);
            this.mContext = mContext;
            tv_more_hot = (TextView) itemview.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView) itemview.findViewById(R.id.gv_hot);

        }

        public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
            adapter = new HotGridViewAdapter(mContext,hot_info);
            gv_hot.setAdapter(adapter);

            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    ToastHelper.showToast(mContext,"position"+position,Toast.LENGTH_SHORT);
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }
    class RecommendViewHolder extends RecyclerView.ViewHolder{

        private final Context mContext;
        private TextView tv_more_recommend;
        private GridView gv_recommend;
        private RecommendGridViewAdapter adapter;

        public RecommendViewHolder(final Context mContext, View itemview) {
            super(itemview);
            this.mContext = mContext;
            tv_more_recommend  = (TextView) itemview.findViewById(R.id.tv_more_recommend);
            gv_recommend = (GridView) itemview.findViewById(R.id.gv_recommend);

        }

        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
            //设置设配器
            adapter = new RecommendGridViewAdapter(mContext,recommend_info);
            gv_recommend.setAdapter(adapter);

            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ToastHelper.showToast(mContext,"position"+position,Toast.LENGTH_SHORT);
                    ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    startGoodsInfoActivity(goodsBean);
                    ToastHelper.showToast(mContext,"position"+position,Toast.LENGTH_SHORT);
                }
            });
        }
    }


    class SeckillViewHolder extends RecyclerView.ViewHolder{
        private final Context mContext;
        private TextView tv_time_seckill;
        private TextView tv_more_seckill;
        private RecyclerView rv_seckill;
        private SeckillRecyclerAdapter adapter;
        private long dt = 0;
        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dt -= 1000;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                String s = simpleDateFormat.format(new Date(dt));
                tv_time_seckill.setText(s);
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0,1000);
                if(dt <= 0){
                    //把消息移除
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };

        public SeckillViewHolder(Context mContext, View itemview) {
            super(itemview);
            tv_more_seckill = (TextView) itemview.findViewById(R.id.tv_more_seckill);
            tv_time_seckill = (TextView) itemview.findViewById(R.id.tv_time_seckill);
            rv_seckill = (RecyclerView) itemview.findViewById(R.id.rv_seckill);
            this.mContext = mContext;
        }

        public void setData(final ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
            adapter = new SeckillRecyclerAdapter(mContext,seckill_info.getList());
            rv_seckill.setAdapter(adapter);
            //设置布局管理器
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
            //设置item的点击事件
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerAdapter.OnSeckillRecyclerView() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext, "秒杀"+position, Toast.LENGTH_SHORT).show();
                    ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info.getList().get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(listBean.getCover_price());
                    goodsBean.setName(listBean.getName());
                    goodsBean.setProduct_id(listBean.getProduct_id());
                    goodsBean.setFigure(listBean.getFigure());
                    startGoodsInfoActivity(goodsBean);

                }
            });
            //秒杀倒计时
            dt = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());
            handler.sendEmptyMessageDelayed(0,1000);

        }
    }
    class ActViewHolder extends RecyclerView.ViewHolder{
        private Context mContext;
        private ViewPager viewpager;

        public ActViewHolder(Context mContext, View itemview) {
            super(itemview);
            this.mContext = mContext;
            viewpager = (ViewPager) itemview.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            viewpager.setPageMargin(20);
            viewpager.setOffscreenPageLimit(3);//>=3
            //setPageTransformer 决定动画效果
            viewpager.setPageTransformer(true, new
                    ScaleInTransformer());
            viewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return act_info.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                /**
                 *
                 * @param container  viewpager
                 * @param position  对应页面的位置
                 * @return
                 */

                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    ImageView iv = new ImageView(mContext);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(mContext).load(Constants.IMAGE_URL+act_info.get(position).getIcon_url()).into(iv);
                    //添加到容器中
                    container.addView(iv);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastHelper.showToast(mContext,"position="+position,Toast.LENGTH_SHORT);
                        }
                    });
                    return iv;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });
        }
    }
    class ChannelViewHolder extends RecyclerView.ViewHolder{
        private Context mContext;
        private GridView gv_channel;
        private ChannelAdapter adapter;

        public ChannelViewHolder(final Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            gv_channel = (GridView) itemView.findViewById(R.id.gv_channel);
            //设置item点击事件
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ToastHelper.showToast(mContext,"position="+position,Toast.LENGTH_SHORT);
                    //startGoodsInfoActivity(goodsBean);
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            //得到数据了
            //设置GridView的适配器
            adapter = new ChannelAdapter(mContext, channel_info);
            gv_channel.setAdapter(adapter);
        }



    }
    class BannerViewHolder extends RecyclerView.ViewHolder{
        private Context mContext;
        private Banner banner;


        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.banner = (Banner) itemView.findViewById(R.id.banner);
        }


        public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
            List<String> imagesurl = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                String imageurl = banner_info.get(i).getImage();
                imagesurl.add(imageurl);

            }
            //设置循环
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            //设置手风琴效果
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setImages(imagesurl, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    Glide.with(mContext)
                            .load(Constants.IMAGE_URL+url)
                            .into(view);
                }
            });
            //设置item的点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    //ToastHelper.showToast(mContext,"position"+position,Toast.LENGTH_SHORT);
                    //startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

    private void startGoodsInfoActivity(GoodsBean goodsBean) {
        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
        intent.putExtra(GOODS_BEAN,goodsBean);
        mContext.startActivity(intent);
    }

    /**
     * 相当于getview中的绑定数据
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(result.getBanner_info());
        }else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(result.getChannel_info());
        }else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(result.getAct_info());
        }else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(result.getSeckill_info());
        }else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendviewholder = (RecommendViewHolder) holder;
            recommendviewholder.setData(result.getRecommend_info());
        }else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(result.getHot_info());
        }
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
