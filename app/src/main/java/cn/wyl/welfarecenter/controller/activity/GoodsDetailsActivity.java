package cn.wyl.welfarecenter.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.bean.AlbumsBean;
import cn.wyl.welfarecenter.bean.GoodsDetailsBean;
import cn.wyl.welfarecenter.bean.MessageBean;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.model.net.NetDao;
import cn.wyl.welfarecenter.model.utils.MFGT;
import cn.wyl.welfarecenter.model.utils.OkHttpUtils;
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
    UserAvatar user;
    boolean iscollect;
    @BindView(R.id.img_add_cart)
    ImageView mImgAddCart;
    @BindView(R.id.relativeLayout)
    RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetails);
        ButterKnife.bind(this);
        user = WelfareCenterApplication.getUser();
        Intent intent = getIntent();
        goodid = intent.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if (goodid == 0) {
            MFGT.finish(this);
        }
        mContext = this;

        initView();
    }

    private void initView() {

        isCollect();

        downGoodsDetails();

    }

    private void downGoodsDetails() {
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


    @OnClick(R.id.img_share)
    public void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    @OnClick(R.id.img_collect)
    public void collectGoods() {
        if (user == null) {
            Toast.makeText(GoodsDetailsActivity.this, R.string.login_first, Toast.LENGTH_SHORT).show();
            return;
        }
        if (iscollect) {
            deleteCollects();
        } else {
            addCollects();
        }

    }

    private void isCollect() {
        NetDao.isCollected(mContext, user.getMuserName(), goodid, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                iscollect = result.isSuccess();
                if (iscollect) {
                    Log.e("main", "iscollect  t=" + iscollect);
                    mImgCollect.setImageResource(R.mipmap.bg_collect_out);
                } else {
                    Log.e("main", "iscollect  f=" + iscollect);
                    mImgCollect.setImageResource(R.mipmap.bg_collect_in);
                }

                Log.e("main", "是否收藏？" + iscollect);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void deleteCollects() {
        NetDao.deleteCollects(mContext, user.getMuserName(), goodid, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    Toast.makeText(GoodsDetailsActivity.this, R.string.collect_cancel, Toast.LENGTH_SHORT).show();
                    mImgCollect.setImageResource(R.mipmap.bg_collect_in);
                    iscollect = false;
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void addCollects() {
        NetDao.addCollects(mContext, user.getMuserName(), goodid, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    Log.e("main", "收藏成功！");
                    Toast.makeText(GoodsDetailsActivity.this, R.string.collect_ok, Toast.LENGTH_SHORT).show();
                    iscollect = true;
                    mImgCollect.setImageResource(R.mipmap.bg_collect_out);
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

   /* @OnClick(R.id.img_share)
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
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        dialogShare.show();

    }*/

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

    @OnClick(R.id.img_add_cart)
    public void addtoCart() {
        NetDao.addToCart(mContext, user.getMuserName(), goodid, 1, 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    Toast.makeText(GoodsDetailsActivity.this, R.string.add_cart_ok, Toast.LENGTH_SHORT).show();
                    Log.e("main", "添加购物车成功");
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
