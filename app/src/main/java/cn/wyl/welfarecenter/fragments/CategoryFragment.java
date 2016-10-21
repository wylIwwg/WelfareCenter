package cn.wyl.welfarecenter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.adapters.CategoryAdapter;
import cn.wyl.welfarecenter.bean.CategoryChildBean;
import cn.wyl.welfarecenter.bean.CategoryGroupBean;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.ConvertUtils;
import cn.wyl.welfarecenter.utils.OkHttpUtils;


public class CategoryFragment extends Fragment {
    CategoryAdapter mAdapter;
    ArrayList<CategoryGroupBean> mGroup;
    ArrayList<ArrayList<CategoryChildBean>> mChild;
    int count = 0;

    @BindView(R.id.category_expand)
    ExpandableListView mCategoryExpand;

    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        mGroup = new ArrayList<>();
        mChild = new ArrayList<>();
        mAdapter = new CategoryAdapter(mChild, getActivity(), mGroup);

        initGroupData();

        initView();
        return view;
    }

    private void initChildData(final int index) {
        NetDao.downCategoryChild(getActivity(), mGroup.get(index).getId(), 1, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                count++;
                if (result != null && result.length > 0) {
                    ArrayList<CategoryChildBean> childBeen = ConvertUtils.array2List(result);
                    mChild.set(index, childBeen);

                    if (count == mGroup.size()) {
                        mAdapter.initData(mGroup, mChild);
                    }
                }
            }

            @Override
            public void onError(String error) {
                Log.e("main", error);
            }
        });
    }


    private void initGroupData() {


        NetDao.downCategoryGroup(getActivity(), new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                mGroup.addAll(ConvertUtils.array2List(result));
                for (int i = 0; i < result.length; i++) {
                    mChild.add(new ArrayList<CategoryChildBean>());
                    initChildData(i);
                }
            }

            @Override
            public void onError(String error) {

            }
        });


    }

    private void initView() {

        mCategoryExpand.setGroupIndicator(null);

        mCategoryExpand.setAdapter(mAdapter);
    }


}
