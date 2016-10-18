package cn.wyl.welfarecenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.GoodsDetailsBean;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.OkHttpUtils;
import cn.wyl.welfarecenter.views.FlowIndicator;
import cn.wyl.welfarecenter.views.SlideAutoLoopView;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/17 19:01
 */
public class GoodsDetailsActivity extends AppCompatActivity {
    Context mContext;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.img_collect)
    ImageView mImgCollect;
    @BindView(R.id.img_share)
    ImageView mImgShare;
    @BindView(R.id.tv_goodsEngName)
    TextView mTvGoodsEngName;
    @BindView(R.id.tv_goodsName)
    TextView mTvGoodsName;
    @BindView(R.id.tv_goodsPrice)
    TextView mTvGoodsPrice;
    @BindView(R.id.sal)
    SlideAutoLoopView mSal;
    @BindView(R.id.indicator)
    FlowIndicator mIndicator;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodsdetails);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int goodid = intent.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        initView(goodid);
        mContext = this;
        Log.d("main", "" + goodid);
    }

    private void initView(int goodid) {
        NetDao.downGoodsDetails(this, goodid, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {

                Log.d("main", "" + result.toString());
              mTvDesc.setText(result.getGoodsBrief());
                mTvGoodsEngName.setText(result.getGoodsEnglishName());
                mTvGoodsName.setText(result.getGoodsName());
                mTvGoodsPrice.setText(result.getCurrencyPrice());
                //ImageLoader.downloadImg(mContext, mi, result.getGoodsThumb());

            }

            @Override
            public void onError(String error) {

            }
        });

    }
}
