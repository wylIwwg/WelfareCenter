package cn.wyl.welfarecenter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.adapters.CartAdapter;
import cn.wyl.welfarecenter.bean.CartBean;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.CommonUtils;
import cn.wyl.welfarecenter.utils.ConvertUtils;
import cn.wyl.welfarecenter.utils.OkHttpUtils;
import cn.wyl.welfarecenter.views.SpaceItemDecoration;


public class CartFragment extends Fragment {

    @BindView(R.id.recycle_cart)
    RecyclerView mRecycleCart;
    @BindView(R.id.tv_cart_total)
    TextView mTvCartTotal;
    @BindView(R.id.tv_cart_save)
    TextView mTvCartSave;
    @BindView(R.id.btn_cart_buy)
    Button mBtnCartBuy;
    @BindView(R.id.layout_cart_by)
    RelativeLayout mLayoutCartBy;

    public CartFragment() {
        // Required empty public constructor
    }

    CartAdapter mAdapter;
    ArrayList<CartBean> mList;
    LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        initView();
        mList = new ArrayList<>();
        initData(false);
        mLayoutManager=new LinearLayoutManager(getActivity());
        mAdapter = new CartAdapter(getActivity(), mList);
        mRecycleCart.setAdapter(mAdapter);
        mRecycleCart.setHasFixedSize(true);
        mRecycleCart.setLayoutManager(mLayoutManager);

        mRecycleCart.addItemDecoration(new SpaceItemDecoration(12));
        return view;
    }

    UserAvatar user;

    private void initView() {
        user = WelfareCenterApplication.getUser();
        if (user != null) {
            NetDao.findCarts(getActivity(), user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    if (result != null && result.length > 0) {
                        ArrayList<CartBean> cartBeen = ConvertUtils.array2List(result);
                        Log.e("main", "购物车商品数量=" + cartBeen.size());
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    private void initData(final boolean isAll) {

        NetDao.findCarts(getActivity(), user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {


                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<CartBean> list = ConvertUtils.array2List(result);
                    mList = list;
                    if (isAll) {
                        mAdapter.initAllData(list);
                    } else {
                        mAdapter.initData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                } else {
                    mAdapter.setMore(false);
                }

            }

            @Override
            public void onError(String error) {

                CommonUtils.showLongToast(error);
                mAdapter.setMore(false);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @OnClick(R.id.btn_cart_buy)
    public void onClick() {
    }
}
