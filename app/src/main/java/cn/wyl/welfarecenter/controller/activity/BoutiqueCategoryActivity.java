package cn.wyl.welfarecenter.controller.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.NewGoodsBean;
import cn.wyl.welfarecenter.controller.commonadapter.CommonAdapter;
import cn.wyl.welfarecenter.controller.commonadapter.CommonViewHolder;
import cn.wyl.welfarecenter.controller.commonadapter.MultiItemAdapter;
import cn.wyl.welfarecenter.model.net.NetDao;
import cn.wyl.welfarecenter.model.utils.ConvertUtils;
import cn.wyl.welfarecenter.model.utils.ImageLoader;
import cn.wyl.welfarecenter.model.utils.MFGT;
import cn.wyl.welfarecenter.model.utils.OkHttpUtils;
import cn.wyl.welfarecenter.views.SpaceItemDecoration;

public class BoutiqueCategoryActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.tv_bouti_cate_name)
    TextView mTvBoutiCateName;

    @BindView(R.id.recy_boutique_cate)
    RecyclerView mRecyBoutiqueCate;

    CommonAdapter<NewGoodsBean> mCommonAdapter;

    MultiItemAdapter<NewGoodsBean> mMultiItemAdapter;
    GridLayoutManager mManager;

    ArrayList<NewGoodsBean> mList;
    String cateName;
    int catId;
    int pageId = 1;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_category);
        ButterKnife.bind(this);
        cateName = getIntent().getStringExtra(I.Boutique.NAME);
        catId = getIntent().getIntExtra(I.Boutique.CAT_ID, -1);
        if (catId < 0) {
            finish();
        }
        initData(I.ACTION_DOWNLOAD);
        initView();
        setListener();
    }

    private void setListener() {
        mRecyBoutiqueCate.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastPo;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastPo = mManager.findLastVisibleItemPosition();
                Log.e("main", lastPo + "___" + mCommonAdapter.getItemCount());
                if (lastPo >= mCommonAdapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    pageId++;
                    initData(I.ACTION_PULL_UP);
                }
            }
        });
    }

    private void initData(final int action) {
        mList = new ArrayList<>();
        NetDao.downNeworBoutiqueGoods(this, catId, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> goodsBeen = ConvertUtils.array2List(result);
                    mList = goodsBeen;
                    if (action == I.ACTION_DOWNLOAD) {
                        mCommonAdapter.initData(goodsBeen);
                    } else {
                        mCommonAdapter.initAllData(goodsBeen);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private void initView() {
        mTvBoutiCateName.setText(cateName);
        mContext = this;
        mManager = new GridLayoutManager(this, 2);
       mCommonAdapter = new CommonAdapter<NewGoodsBean>(this, mList, R.layout.item_newgoods) {
            @Override
            public void convert(CommonViewHolder holder, NewGoodsBean goodsBean) {
                ImageView img_goods = holder.getView(R.id.img_goods);
                ImageLoader.downloadImg(mContext, img_goods, goodsBean.getGoodsThumb());
                TextView tv_name = holder.getView(R.id.tv_goodsName);
                tv_name.setText(goodsBean.getGoodsName());
                TextView tv_price = holder.getView(R.id.tv_goodsPrice);
                tv_price.setText(goodsBean.getCurrencyPrice());
            }
        };


        mRecyBoutiqueCate.setLayoutManager(mManager);
        mRecyBoutiqueCate.setAdapter(mCommonAdapter);
        mRecyBoutiqueCate.addItemDecoration(new SpaceItemDecoration(10));

    }

    @OnClick(R.id.img_back)
    public void finishActivity() {
        MFGT.finish(this);
    }


}
