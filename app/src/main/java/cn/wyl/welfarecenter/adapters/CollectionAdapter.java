package cn.wyl.welfarecenter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.bean.CollectBean;
import cn.wyl.welfarecenter.bean.MessageBean;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.ImageLoader;
import cn.wyl.welfarecenter.utils.OkHttpUtils;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/17 11:16
 */
public class CollectionAdapter extends RecyclerView.Adapter {
    static List<CollectBean> mList;
    Context mContext;
    boolean isMore;


    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public CollectionAdapter(Context context, List<CollectBean> list) {
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
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterItem(View.inflate(mContext, R.layout.item_footer, null));
        } else
            holder = new CollectsHolder(View.inflate(mContext, R.layout.item_collects, null));

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position == getItemCount() - 1) {
            FooterItem vh = (FooterItem) holder;
            vh.mtv_footer.setText(getFooter());
            return;
        }
        CollectBean goods = mList.get(position);
        CollectsHolder vh = (CollectsHolder) holder;
        vh.mTvGoodsName.setText(goods.getGoodsName());
        vh.mLayoutGoods.setTag(goods);

        ImageLoader.downloadImg(mContext, vh.mImgGoods, goods.getGoodsThumb());

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

    private int getFooter() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;

        } else return I.TYPE_ITEM;
    }

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }

        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void initAllData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


   /* class GoodsItem extends RecyclerView.ViewHolder {
        ImageView mImgGoods;
        TextView mTvGoodsName;
        LinearLayout mLayoutGoods;

        public GoodsItem(View itemView) {
            super(itemView);
            mImgGoods = (ImageView) itemView.findViewById(R.id.img_goods);
            mTvGoodsName = (TextView) itemView.findViewById(R.id.tv_goodsName);
            mLayoutGoods = (LinearLayout) itemView.findViewById(R.id.layout_goods);

        }
    }*/

    class FooterItem extends RecyclerView.ViewHolder {
        TextView mtv_footer;

        public FooterItem(View view) {
            super(view);
            mtv_footer = (TextView) view.findViewById(R.id.tv_loaddata);
        }
    }


    class CollectsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_goods)
        ImageView mImgGoods;
        @BindView(R.id.tv_goodsName)
        TextView mTvGoodsName;
        @BindView(R.id.img_delete)
        ImageView mImgDelete;
        @BindView(R.id.layout_goods)
        RelativeLayout mLayoutGoods;

        CollectsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.img_delete)
        public void deleteCollect() {
            String name = WelfareCenterApplication.getUser().getMuserName();
            final CollectBean goods = (CollectBean) mLayoutGoods.getTag();
            NetDao.deleteCollects(mContext, name, goods.getGoodsId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        Log.e("main","删除收藏成功");
                        mList.remove(goods);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(String error) {
                    Log.e("main", "error==="+error);
                }
            });
        }
    }
}
