package cn.wyl.welfarecenter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.adapters.NewGoodsAdapter;
import cn.wyl.welfarecenter.bean.NewGoodsBean;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.ConvertUtils;
import cn.wyl.welfarecenter.utils.OkHttpUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    NewGoodsAdapter mNewGoodsAdapter;
    ArrayList<NewGoodsBean> mList;
    GridLayoutManager mGridLayoutManager;
    Context mContext;

    int pageId = 1;
    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.recycleLayout)
    RecyclerView mRecycleLayout;
    @BindView(R.id.swiper)
    SwipeRefreshLayout mSwiper;

    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, view);
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

        setListeners();
        return view;
    }

    private void setListeners() {
        mSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwiper.setRefreshing(true);
                mSwiper.setEnabled(true);
                mTvRefresh.setVisibility(View.VISIBLE);

                initData(false);
                mTvRefresh.setVisibility(View.GONE);
                mSwiper.setRefreshing(false);
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
                if (position >= mNewGoodsAdapter.getItemCount() - 1) {
                    pageId++;
                    initData(true);
                }
                else {
                    mNewGoodsAdapter.setFooter("没有数据了");
                }
            }
        });
    }

    private void initData(final boolean isAll) {
        NetDao.downLoadNewGoods(getActivity(), pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                Log.d("main", "" + list.size());
                if (isAll) {
                    mNewGoodsAdapter.initAllData(list);
                } else
                    mNewGoodsAdapter.initData(list);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private ArrayList<NewGoodsBean> downLoadData() {
        ArrayList<NewGoodsBean> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            NewGoodsBean goods = new NewGoodsBean();
            goods.setCurrencyPrice("12333");
            goods.setGoodsName("大衣" + i);
            list.add(goods);
        }
        return list;
    }

}
