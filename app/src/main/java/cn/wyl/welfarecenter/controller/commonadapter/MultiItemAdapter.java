package cn.wyl.welfarecenter.controller.commonadapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/19 15:36
 */
public abstract class MultiItemAdapter<T> extends CommonAdapter<T> {
    protected MultiItemTypeSupport<T> mItemTypeSupport;

    public MultiItemAdapter(Context context, List<T> datas, MultiItemTypeSupport<T> itemTypeSupport) {
        super(context, datas, -1);
        mItemTypeSupport=itemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mItemTypeSupport.getItemViewType(position,mDatas.get(position));
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId=mItemTypeSupport.getLayoutId(viewType);
        CommonViewHolder holder=CommonViewHolder.get(mContext,parent,layoutId);
        return holder;

    }
}
