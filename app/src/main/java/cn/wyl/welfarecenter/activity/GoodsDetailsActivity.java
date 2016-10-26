package cn.wyl.welfarecenter.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.bean.AlbumsBean;
import cn.wyl.welfarecenter.bean.GoodsDetailsBean;
import cn.wyl.welfarecenter.bean.MessageBean;
import cn.wyl.welfarecenter.bean.UserAvatar;
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
public class GoodsDetailsActivity extends BaseActivity {
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
    int goodid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetails);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        goodid = intent.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if (goodid == 0) {
            MFGT.finish(this);
        }
        mContext = this;
        initView(goodid);
    }

    private void initView(int goodid) {
        NetDao.downGoodsDetails(mContext, goodid, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
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

    @OnClick(R.id.img_collect)
    public void collectGoods() {
        final UserAvatar user = WelfareCenterApplication.getUser();
        if (user != null) {

            NetDao.isCollected(mContext, user.getMuserName(), goodid, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && !result.isSuccess()) {
                        addCollects(user);
                    }else {
                        deleteCollects(user);
                    }

                }

                @Override
                public void onError(String error) {

                }
            });
        }

    }

    private void deleteCollects(UserAvatar user) {
        NetDao.deleteCollects(mContext, user.getMuserName(), goodid, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void addCollects(UserAvatar user) {
        NetDao.addCollects(mContext, user.getMuserName(), goodid, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    Log.e("main", "收藏成功！");
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @OnClick(R.id.img_back)
    public void onBackActivity() {
        MFGT.finish(this);
    }

    @OnClick(R.id.img_share)
    public void onShareGoods() {
        View share = getLayoutInflater().inflate(R.layout.activity_share, null);
        AlertDialog.Builder dialogShare = new AlertDialog.Builder(this).
                setView(share).setNegativeButton(null, null).setPositiveButton(null, null);


        WindowManager manager = getWindowManager();
        Display display = manager.getDefaultDisplay();
        Window window = dialogShare.create().getWindow();
       /* WindowManager.LayoutParams params = window.getAttributes();
        params.width = display.getWidth();
        params.alpha = 0.7f;
        window.setAttributes(params);*/
        window.setGravity(Gravity.BOTTOM);
        dialogShare.show();

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
