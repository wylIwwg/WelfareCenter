package cn.wyl.welfarecenter.controller.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.NewGoodsBean;
import cn.wyl.welfarecenter.controller.adapters.NewGoodsAdapter;
import cn.wyl.welfarecenter.model.net.ModelNeworBoutiqueGoods;
import cn.wyl.welfarecenter.model.net.onCompleteListener;
import cn.wyl.welfarecenter.model.utils.CommonUtils;
import cn.wyl.welfarecenter.model.utils.ConvertUtils;
import cn.wyl.welfarecenter.model.utils.MFGT;
import cn.wyl.welfarecenter.views.SpaceItemDecoration;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    NewGoodsAdapter mNewGoodsAdapter;
    ArrayList<NewGoodsBean> mList;
    GridLayoutManager mGridLayoutManager;

    int pageId = 1;
    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.recycleLayout)
    RecyclerView mRecycleLayout;
    @BindView(R.id.swiper)
    SwipeRefreshLayout mSwiper;

    ModelNeworBoutiqueGoods mNeworBoutiqueGoods;

    public NewGoodsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, view);
        mNeworBoutiqueGoods=new ModelNeworBoutiqueGoods();

        mSwiper.setColorSchemeColors(getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mList = new ArrayList<>();
        initData(false);
        mNewGoodsAdapter = new NewGoodsAdapter(getActivity(), mList);
        mRecycleLayout.setAdapter(mNewGoodsAdapter);
        mRecycleLayout.setHasFixedSize(true);
        mRecycleLayout.setLayoutManager(mGridLayoutManager);
        mRecycleLayout.addItemDecoration(new SpaceItemDecoration(12));

        setListeners();
        return view;
    }

    private void setListeners() {

        mNewGoodsAdapter.setOnItemCKListener(new NewGoodsAdapter.OnItemCKListener() {
            @Override
            public void onItemClicK(View view, int position) {

                int id = (int) view.getTag();
                MFGT.gotoGoodsDetailsAC(getActivity(),id);

            }
        });


        mSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageId = 1;
                mSwiper.setRefreshing(true);
                mSwiper.setEnabled(true);
                mTvRefresh.setVisibility(View.VISIBLE);

                initData(false);
            }
        });


        mRecycleLayout.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int position;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                position = mGridLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                position = mGridLayoutManager.findLastVisibleItemPosition();
                if (position >= mNewGoodsAdapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && mNewGoodsAdapter.isMore()) {
                    pageId++;
                    initData(true);
                }
            }
        });
    }

    private void initData(final boolean isAll) {

        mNeworBoutiqueGoods.downNeworBoutiqueGoods(getActivity(), 0,pageId, new onCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mSwiper.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                mNewGoodsAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    mList = list;
                    if (isAll) {
                        mNewGoodsAdapter.initAllData(list);
                    } else {
                        mNewGoodsAdapter.initData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mNewGoodsAdapter.setMore(false);
                    }
                } else {
                    mNewGoodsAdapter.setMore(false);
                }

            }

            @Override
            public void onError(String error) {
                mSwiper.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                CommonUtils.showLongToast(error);
                mNewGoodsAdapter.setMore(false);
            }
        });

    }


}
