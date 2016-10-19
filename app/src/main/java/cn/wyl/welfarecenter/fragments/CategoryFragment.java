package cn.wyl.welfarecenter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.CategoryGroupBean;
import cn.wyl.welfarecenter.common.CommonAdapter;
import cn.wyl.welfarecenter.common.CommonViewHolder;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.ConvertUtils;
import cn.wyl.welfarecenter.utils.ImageLoader;
import cn.wyl.welfarecenter.utils.OkHttpUtils;


public class CategoryFragment extends Fragment {

    @BindView(R.id.category_recycler)
    RecyclerView mCategoryRecycler;
    LinearLayoutManager mManager;

    ArrayList<CategoryGroupBean> mList;
    CommonAdapter<CategoryGroupBean> mCommonAdapter;
    ArrayList<String> test;


    CommonAdapter<String> testAdapter;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();

        return view;
    }

    private void initView() {
        mCommonAdapter = new CommonAdapter<CategoryGroupBean>(getActivity(), mList, R.layout.item_category) {

            @Override
            public void convert(CommonViewHolder holder, CategoryGroupBean groupBean) {
                TextView tv_name = holder.getView(R.id.tv_cate_title);
                tv_name.setText(groupBean.getName());
                ImageView img_ico = holder.getView(R.id.img_ico);
                ImageLoader.downloadImg(getActivity(), img_ico, groupBean.getImageUrl());
            }

        };
        mManager = new LinearLayoutManager(getActivity());
        mCategoryRecycler.setLayoutManager(mManager);
        mCategoryRecycler.setAdapter(mCommonAdapter);
    }

    private void initData() {
        test = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            char c = (char) (67 + index);
            test.add(String.valueOf(c));
        }
        mList = new ArrayList<>();
        NetDao.downCategoryGroup(getActivity(), new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result.length > 0 && result != null) {
                    mList = ConvertUtils.array2List(result);
                    mCommonAdapter.initData(mList);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }


}
