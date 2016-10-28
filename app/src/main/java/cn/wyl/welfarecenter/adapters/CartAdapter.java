package cn.wyl.welfarecenter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.CartBean;
import cn.wyl.welfarecenter.bean.GoodsDetailsBean;
import cn.wyl.welfarecenter.bean.MessageBean;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.ImageLoader;
import cn.wyl.welfarecenter.utils.MFGT;
import cn.wyl.welfarecenter.utils.OkHttpUtils;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/17 11:16
 */
public class CartAdapter extends RecyclerView.Adapter {
    static List<CartBean> mList;
    Context mContext;
    boolean isMore;


    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public CartAdapter(Context context, List<CartBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        holder = new CartViewHolder(View.inflate(mContext, R.layout.item_cart, null));

        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final CartViewHolder vh = (CartViewHolder) holder;
        final CartBean goods = mList.get(position);
        GoodsDetailsBean goodsDetails = goods.getGoods();
        if (goodsDetails != null) {
            vh.mTvCartGoodsName.setText(goodsDetails.getGoodsName());
            vh.mTvCartGoodsPrice.setText(goodsDetails.getCurrencyPrice());
            ImageLoader.downloadImg(mContext, vh.mImgCartGoods, goodsDetails.getGoodsThumb());
        }
        vh.mTvCartGoodsCount.setText("(" + goods.getCount() + ")");
        vh.mCbCart.setChecked(goods.isChecked());

        vh.mCbCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                goods.setChecked(isChecked);
                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_PRICE));
            }
        });


        vh.mImgCartDel.setTag(position);
        vh.mImgCartAdd.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public void initData(ArrayList<CartBean> list) {

        mList = list;
        notifyDataSetChanged();
    }




    class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_cart_goods)
        ImageView mImgCartGoods;
        @BindView(R.id.tv_cart_goodsName)
        TextView mTvCartGoodsName;
        @BindView(R.id.tv_cart_goodsCount)
        TextView mTvCartGoodsCount;
        @BindView(R.id.tv_cart_goodsPrice)
        TextView mTvCartGoodsPrice;
        @BindView(R.id.img_cart_del)
        ImageView mImgCartDel;
        @BindView(R.id.img_cart_add)
        ImageView mImgCartAdd;
        @BindView(R.id.cb_cart)
        CheckBox mCbCart;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.tv_cart_goodsPrice, R.id.tv_cart_goodsName, R.id.img_cart_goods})
        public void gotoGoodsDetails(View view) {
            Log.e("main", "gotogoodsdetails");
            final int posotion = (int) mImgCartAdd.getTag();
            CartBean cart = mList.get(posotion);
            MFGT.gotoGoodsDetailsAC((Activity) mContext, cart.getGoodsId());
        }

        @OnClick({R.id.img_cart_del, R.id.img_cart_add})
        public void updateCount(View view) {
            final int posotion = (int) view.getTag();
            CartBean cart = mList.get(posotion);
            switch (view.getId()) {
                case R.id.img_cart_del://减少数目
                    if (cart.getCount() > 1) {
                        NetDao.updateCartCount(mContext, cart.getId(), cart.getCount() - 1, 0, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean result) {
                                if (result.isSuccess() && result != null) {
                                    Log.e("main", result.getMsg());
                                    mList.get(posotion).setCount(mList.get(posotion).getCount() - 1);
                                    mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_PRICE));
                                    mTvCartGoodsCount.setText("(" + mList.get(posotion).getCount() + ")");
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    } else {
                        NetDao.deleteCart(mContext, cart.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean result) {
                                if (result.isSuccess() && result != null) {
                                    mList.remove(posotion);
                                    mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_PRICE));
                                    mContext.sendBroadcast(new Intent(I.CART_STATUS));
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }

                    break;
                case R.id.img_cart_add:
                    NetDao.updateCartCount(mContext, cart.getId(), cart.getCount() + 1, 0, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result.isSuccess() && result != null) {
                                Log.e("main", result.getMsg());
                                mList.get(posotion).setCount(mList.get(posotion).getCount() + 1);
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_PRICE));
                                mTvCartGoodsCount.setText("(" + mList.get(posotion).getCount() + ")");
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                    break;
            }
        }

    }

}
