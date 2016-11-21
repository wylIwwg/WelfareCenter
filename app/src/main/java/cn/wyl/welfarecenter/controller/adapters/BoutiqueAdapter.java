package cn.wyl.welfarecenter.controller.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.BoutiqueBean;
import cn.wyl.welfarecenter.model.utils.ImageLoader;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/18 18:53
 */
public class BoutiqueAdapter extends RecyclerView.Adapter {
    ArrayList<BoutiqueBean> mList;
    Context mContext;

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view = View.inflate(mContext, R.layout.item_boutique, null);
        holder = new BoutiqueViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BoutiqueViewHolder vh= (BoutiqueViewHolder) holder;
        BoutiqueBean bouti= mList.get(position);
        vh.mTvBoutiTitle.setText(bouti.getTitle());
        vh.mTvBoutiName.setText(bouti.getName());
        vh.mTvBoutiDec.setText(bouti.getDescription());
        ImageLoader.downloadImg(mContext,vh.mImgBoutiGoods,bouti.getImageurl());

    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public void initData(ArrayList<BoutiqueBean> boutis) {
        mList.addAll(boutis);
        notifyDataSetChanged();
    }


    static class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_bouti_goods)
        ImageView mImgBoutiGoods;
        @BindView(R.id.tv_bouti_title)
        TextView mTvBoutiTitle;
        @BindView(R.id.tv_bouti_name)
        TextView mTvBoutiName;
        @BindView(R.id.tv_bouti_dec)
        TextView mTvBoutiDec;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
