package cn.wyl.welfarecenter.controller.commonadapter;

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

    protected SparseArray<View> mViews;//存放布局文件中的所有View
    protected Context mContext;
    protected View mConvertView;//用于记录当前选中的itemView

    public CommonViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();

    }

    /**
     * 通过该方法在Adapter中构建ViewHolder
     *
     * @param context
     * @param parent
     * @param layoutId 需要解析的布局文件id
     * @return
     */
    public static CommonViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        CommonViewHolder holder = new CommonViewHolder(context, view, parent);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId 控件id
     * @param <T>    控件泛型
     * @return 返回该控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);

        }
        return (T) view;
    }


    /**
     * 设置控件的监听事件
     *
     * @param viewId   需要被监听的控件
     * @param listener 监听器
     * @return 返回holder对象
     */
    public CommonViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);//获取该控件
        view.setOnClickListener(listener);//设置监听
        return this;
    }


}
