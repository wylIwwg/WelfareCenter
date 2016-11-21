package cn.wyl.welfarecenter.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.controller.activity.CategoryGoodsActivity;
import cn.wyl.welfarecenter.bean.CategoryChildBean;
import cn.wyl.welfarecenter.bean.CategoryGroupBean;
import cn.wyl.welfarecenter.model.utils.ImageLoader;
import cn.wyl.welfarecenter.model.utils.MFGT;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/19 17:11
 */
public class CategoryAdapter extends BaseExpandableListAdapter {
    static int index;

    Context mContext;
    ArrayList<CategoryGroupBean> mGroup;
    ArrayList<ArrayList<CategoryChildBean>> mChild;

    public CategoryAdapter(ArrayList<ArrayList<CategoryChildBean>>
                                   child, Context context, ArrayList<CategoryGroupBean> group) {
        mGroup = new ArrayList<>();
        mGroup.addAll(group);
        mChild = new ArrayList<>();
        mChild.addAll(child);
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return mGroup == null ? 0 : mGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChild != null && mChild.get(groupPosition) != null ? mChild.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroup != null ? mGroup.get(groupPosition) : null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return mChild != null && mChild.get(groupPosition) != null ? mChild.get(groupPosition).get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        CategoryGroupBean group = getGroup(groupPosition);
        if (group != null) {
            ImageLoader.downloadImg(mContext, holder.mImgIco, mGroup.get(groupPosition).getImageUrl());
            holder.mTvCateTitle.setText(mGroup.get(groupPosition).getName());
            holder.mImgArrow.setImageResource(isExpanded ? R.drawable.arrow_up : R.drawable.arrow_down);
        }
        return convertView;
    }


    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category_child, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CategoryChildBean child=    getChild(groupPosition, childPosition);
        if (child != null) {
            holder.mTvCateTitle.setText(child.getName());
            ImageLoader.downloadImg(mContext, holder.mImgIco,child.getImageUrl());

           holder.mLayoutChild.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(mContext, CategoryGoodsActivity.class);
                   intent.putExtra(I.CategoryChild.CAT_ID,child.getId());
                   intent.putExtra(I.CategoryGroup.NAME,mGroup.get(groupPosition).getName());
                   intent.putExtra("childList",mChild.get(groupPosition));
                   MFGT.startActivity((Activity) mContext,intent);
               }
           });

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    public void initGroupData(ArrayList<CategoryGroupBean> group) {
        mGroup.addAll(group);
        notifyDataSetChanged();
    }

    public void initChildData(ArrayList<ArrayList<CategoryChildBean>> child) {
        mChild.addAll(child);
        notifyDataSetChanged();
    }

    public void initData(ArrayList<CategoryGroupBean> group, ArrayList<ArrayList<CategoryChildBean>> child) {
        if (mGroup!=null){
            mGroup.clear();
        }
        mGroup.addAll(group);
        if (mChild!=null){
            mChild.clear();
        }
        mChild.addAll(child);
        notifyDataSetChanged();
    }


    static class GroupViewHolder {
        @BindView(R.id.img_ico)
        ImageView mImgIco;
        @BindView(R.id.tv_cate_title)
        TextView mTvCateTitle;
        @BindView(R.id.img_arrow)
        ImageView mImgArrow;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder {
        @BindView(R.id.img_ico)
        ImageView mImgIco;
        @BindView(R.id.tv_cate_title)
        TextView mTvCateTitle;
        @BindView(R.id.catelayout_child)
        RelativeLayout mLayoutChild;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
