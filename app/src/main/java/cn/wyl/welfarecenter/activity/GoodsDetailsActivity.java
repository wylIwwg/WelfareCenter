package cn.wyl.welfarecenter.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.AlbumsBean;
import cn.wyl.welfarecenter.bean.GoodsDetailsBean;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.MFGT;
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
        if (goodid==0){
            MFGT.finish(this);
        }
        mContext = this;
        initView(goodid);
    }

    private void initView(int goodid) {
        NetDao.downGoodsDetails(this, goodid, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result.getProperties() == null) {
                    MFGT.finish((Activity) mContext);
                }
                mTvDesc.setText(result.getGoodsBrief());
                mTvGoodsEngName.setText(result.getGoodsEnglishName());
                mTvGoodsName.setText(result.getGoodsName());
                mTvGoodsPrice.setText(result.getCurrencyPrice());
                mSal.startPlayLoop(mIndicator, getAlbumS(result), getAlbumCount(result));
            }

            @Override
            public void onError(String error) {
                MFGT.finish((Activity) mContext);
            }
        });

    }
    @OnClick(R.id.img_back)
    public void onBackActivity(){
        MFGT.finish(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MFGT.finish(this);
    }

    private int getAlbumCount(GoodsDetailsBean result) {
        if (result.getProperties() != null && result.getProperties().length > 0) {
            return result.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumS(GoodsDetailsBean result) {
        String[] urls = new String[]{};
        if (result.getProperties() != null && result.getProperties().length > 0) {
            AlbumsBean[] albums = result.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
                urls[i] = albums[i].getImgUrl();
            }
        }
        return urls;
    }
}
