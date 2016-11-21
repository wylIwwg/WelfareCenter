package cn.wyl.welfarecenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.controller.adapters.CartGoodsAdapter;
import cn.wyl.welfarecenter.bean.CartBean;

public class OrderActivity extends BaseActivity implements PaymentHandler {

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
    int price;

    private void initData() {

        //设置需要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "bfb", "jdpay_wap"});

        // 提交数据的格式，默认格式为json
        // PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";

        PingppLog.DEBUG = true;


        ArrayList<CartBean> cartBeen = (ArrayList<CartBean>) getIntent().getSerializableExtra(I.Cart.ID);
        price = getIntent().getIntExtra("price", 0);


        Log.e("main", "order:" + price);
        Log.e("main", "order:" + cartBeen.toString());
        mTvOrderPay.setText("￥" + price);


        mAdapter = new CartGoodsAdapter(this, cartBeen);

        mRcyleOrder.setAdapter(mAdapter);
        mRcyleOrder.setLayoutManager(new LinearLayoutManager(this));

    }

    private static String URL = "http://218.244.151.190/demo/charge";

    @OnClick(R.id.btn_order_pay)
    public void onClick() {
        // 产生个订单号
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());

        // 计算总金额（以分为单位）

        // 构建账单json对象
        JSONObject bill = new JSONObject();

        // 自定义的额外信息 选填
        JSONObject extras = new JSONObject();
        try {
            extras.put("extra1", "extra1");
            extras.put("extra2", "extra2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            bill.put("order_no", orderNo);
            bill.put("amount", price * 100);
            bill.put("extras", extras);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("main", "开始购买");
        //壹收款: 创建支付通道的对话框
        PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), URL, this);
    }

    @Override
    public void handlePaymentResult(Intent data) {
        if (data != null) {
            /**
             * code：支付结果码  -2:服务端错误、 -1：失败、 0：取消、1：成功
             * error_msg：支付结果信息
             */
            int code = data.getExtras().getInt("code");
            String errorMsg = data.getExtras().getString("error_msg");
        }
    }
}
