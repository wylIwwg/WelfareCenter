package cn.wyl.welfarecenter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/18 20:03
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    protected SparseArray<View> mViews;
    protected Context mContext;
    protected View mConvertView;

    public CommonViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();

    }

    public static CommonViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        CommonViewHolder holder = new CommonViewHolder(context, view, parent);
        return holder;
    }


    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);

        }
        return (T) view;
    }


}
