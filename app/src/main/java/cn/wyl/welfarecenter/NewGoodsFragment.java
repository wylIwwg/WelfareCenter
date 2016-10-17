package cn.wyl.welfarecenter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.wyl.welfarecenter.adapters.NewGoodsAdapter;
import cn.wyl.welfarecenter.bean.NewGoodsBean;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    SwipeRefreshLayout mRefreshLayout;
    NewGoodsAdapter mNewGoodsAdapter;
    List<NewGoodsBean> mList;
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    Context mContext;

    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiper);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );


        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleLayout);
        mList = new ArrayList<>();
        mList = downLoadData();
        mNewGoodsAdapter = new NewGoodsAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mNewGoodsAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        return view;
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
