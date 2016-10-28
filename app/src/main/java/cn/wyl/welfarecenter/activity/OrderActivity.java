package cn.wyl.welfarecenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.adapters.CartGoodsAdapter;
import cn.wyl.welfarecenter.bean.CartBean;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.et_order_name)
    EditText mEtOrderName;
    @BindView(R.id.et_order_phone)
    EditText mEtOrderPhone;
    @BindView(R.id.sp_order_province)
    Spinner mSpOrderProvince;
    @BindView(R.id.et_order_street)
    EditText mEtOrderStreet;
    @BindView(R.id.tv_order_pay)
    TextView mTvOrderPay;
    @BindView(R.id.btn_order_pay)
    Button mBtnOrderPay;
    @BindView(R.id.rcyle_order)
    RecyclerView mRcyleOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        initData();
    }

    CartGoodsAdapter mAdapter;

    private void initData() {

        ArrayList<CartBean> cartBeen = (ArrayList<CartBean>) getIntent().getSerializableExtra(I.Cart.ID);
        int price = getIntent().getIntExtra("price", 0);


        Log.e("main", "order:" + price);
        Log.e("main", "order:" + cartBeen.toString());
        mTvOrderPay.setText("￥" + price);


        mAdapter = new CartGoodsAdapter(this, cartBeen);

        mRcyleOrder.setAdapter(mAdapter);
        mRcyleOrder.setLayoutManager(new LinearLayoutManager(this));

    }

    @OnClick(R.id.btn_order_pay)
    public void onClick() {

    }
}
