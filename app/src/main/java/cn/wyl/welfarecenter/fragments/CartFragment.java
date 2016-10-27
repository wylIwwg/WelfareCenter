package cn.wyl.welfarecenter.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    @BindView(R.id.lay_nothing)
    RelativeLayout mLayNothing;

    public CartFragment() {
        // Required empty public constructor
    }

    CartAdapter mAdapter;
    ArrayList<CartBean> mList;
    LinearLayoutManager mLayoutManager;

    updatePriceReceiver mReceiver;
    Context mContext;
    UserAvatar user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        setLayout(false);
        user = WelfareCenterApplication.getUser();
        mContext = getActivity();
        mList = new ArrayList<>();
        initData();
        initView();

        return view;

    }


    private void setLayout(boolean hasCart) {
        Log.e("main",hasCart?"HAVESONMTHONG":"NOTHING");
        mLayoutCartBy.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        mLayNothing.setVisibility(hasCart ? View.GONE : View.VISIBLE);
    }


    public void updatePrice() {
        if (mList != null && mList.size() > 0) {
            int sumPrice = 0;
            int rankPrice = 0;
            for (CartBean cb : mList) {
                if (cb.isChecked()) {
                    sumPrice += getPrice(cb.getGoods().getCurrencyPrice()) * cb.getCount();
                    rankPrice += getPrice(cb.getGoods().getRankPrice()) * cb.getCount();
                }
            }
            mTvCartSave.setText(sumPrice - rankPrice + "");
            mTvCartTotal.setText(sumPrice + "");
        } else {
            setLayout(false);
        }
    }

    //修改价格的广播
    class updatePriceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("main", "updatePriceReceiver");
            updatePrice();

        }
    }

    //获取数值型的价格
    protected int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.parseInt(price);
    }



    private void initView() {


        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATE_PRICE);
        mReceiver = new updatePriceReceiver();
        mContext.registerReceiver(mReceiver, filter);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CartAdapter(getActivity(), mList);
        mRecycleCart.setAdapter(mAdapter);
        mRecycleCart.setHasFixedSize(true);
        mRecycleCart.setLayoutManager(mLayoutManager);
        mRecycleCart.addItemDecoration(new SpaceItemDecoration(12));

    }

    private void initData() {

        NetDao.findCarts(getActivity(), user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    setLayout(true);
                    ArrayList<CartBean> list = ConvertUtils.array2List(result);
                    if (mList!=null){
                        mList.clear();
                    }
                    mList.addAll(list);
                    mContext.sendBroadcast(new Intent(I.CART_STATUS));
                    mAdapter.initData(mList);
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
                setLayout(false);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("main", "Cartfragmrt.resume...");
        initData();
        initView();
    }

    @OnClick(R.id.btn_cart_buy)
    public void onClick() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }
}
