package cn.wyl.welfarecenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.R;

public class OrderActivity extends BaseActivity implements PaymentHandler {

    @BindView(R.id.tv_order_total)
    TextView tvOrderTotal;
    @BindView(R.id.btn_order_buy)
    Button btnOrderBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        price=getIntent().getIntExtra("price",0);
        if (price!=0){
            tvOrderTotal.setText(price+"");
        }

        initData();
    }

    private void initData() {

        //设置要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "cnp", "bfb"});
        //设置是否支持外卡支付， true：支持， false：不支持， 默认不支持外卡
        //PingppOne.SUPPORT_FOREIGN_CARD = true;
        //提交数据的格式，默认格式为json
        //PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";
        //设置APP_ID和PUBLISHABLE_KEY(应用快捷支付需要)
        //PingppOne.APP_ID = "Ping++ App ID";
        //PingppOne.PUBLISHABLE_KEY = "Ping++ Publishable Key";
        //是否开启日志
        PingppLog.DEBUG = true;
    }

    int price;
    private static String URL = "http://218.244.151.190/demo/charge";

    @OnClick(R.id.btn_order_buy)
    public void onClick() {



// 产生个订单号
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());

        // 计算总金额（以分为单位）
        int amount = 0;

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
            bill.put("amount", price*100);
            bill.put("extras", extras);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //壹收款: 创建支付通道的对话框
        PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), URL, this);
    }

    @Override
    public void handlePaymentResult(Intent intent) {

    }
}
