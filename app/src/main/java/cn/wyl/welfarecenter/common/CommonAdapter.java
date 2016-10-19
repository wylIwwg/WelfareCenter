package cn.wyl.welfarecenter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/18 19:57
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    protected List<T> mDatas;
    protected Context mContext;
    protected int mLayoutId;
    protected LayoutInflater mInflater;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        mContext = context;
        mDatas=new ArrayList<T>();
        mDatas.addAll(datas);
        mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder holder = CommonViewHolder.get(mContext, parent, mLayoutId);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(CommonViewHolder holder, T t);


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void initData(ArrayList<T> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }
}
