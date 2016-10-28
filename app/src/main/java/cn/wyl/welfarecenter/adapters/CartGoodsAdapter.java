package cn.wyl.welfarecenter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.CartBean;
import cn.wyl.welfarecenter.bean.GoodsDetailsBean;
import cn.wyl.welfarecenter.utils.ImageLoader;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/17 11:16
 */
public class CartGoodsAdapter extends RecyclerView.Adapter {
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

    public CartGoodsAdapter(Context context, List<CartBean> list) {
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
        vh.mTvCartGoodsCount.setText("×" + goods.getCount() );
        vh.mCbCart.setVisibility(View.GONE);
        vh.mCbCart.setEnabled(false);
        vh.mImgCartDel.setVisibility(View.GONE);
        vh.mImgCartDel.setEnabled(false);
        vh.mImgCartAdd.setVisibility(View.GONE);
        vh.mImgCartAdd.setEnabled(false);

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


    }

}
