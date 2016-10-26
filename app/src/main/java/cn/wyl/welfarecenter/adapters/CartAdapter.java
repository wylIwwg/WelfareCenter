package cn.wyl.welfarecenter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.CartBean;
import cn.wyl.welfarecenter.bean.GoodsDetailsBean;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.ImageLoader;
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
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public interface OnItemCKListener {
        void onItemClicK(View view, int position);
    }

    private OnItemCKListener mOnItemCKListener;

    public void setOnItemCKListener(OnItemCKListener mOnItemCKListener) {
        this.mOnItemCKListener = mOnItemCKListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        holder = new CartViewHolder(View.inflate(mContext, R.layout.item_cart, null));

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final CartBean goods = mList.get(position);
        final CartViewHolder vh = (CartViewHolder) holder;
        NetDao.downGoodsDetails(mContext, goods.getGoodsId(), new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {

                vh.mTvCartGoodsName.setText(result.getGoodsName());
                vh.mTvCartGoodsPrice.setText(result.getCurrencyPrice()+"*"+goods.getCount());
                ImageLoader.downloadImg(mContext, vh.mImgCartGoods, result.getGoodsThumb());
            }

            @Override
            public void onError(String error) {

            }
        });
        vh.mTvCartGoodsCount.setText(goods.getCount()+"");

        if (mOnItemCKListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemCKListener.onItemClicK(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size() ;
    }



    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }

        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void initAllData(ArrayList<CartBean> list) {
        mList.addAll(list);
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

         CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
