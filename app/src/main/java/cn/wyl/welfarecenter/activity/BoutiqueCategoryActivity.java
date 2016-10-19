package cn.wyl.welfarecenter.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.NewGoodsBean;
import cn.wyl.welfarecenter.common.CommonAdapter;
import cn.wyl.welfarecenter.common.CommonViewHolder;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.ConvertUtils;
import cn.wyl.welfarecenter.utils.ImageLoader;
import cn.wyl.welfarecenter.utils.MFGT;
import cn.wyl.welfarecenter.utils.OkHttpUtils;
import cn.wyl.welfarecenter.views.SpaceItemDecoration;

public class BoutiqueCategoryActivity extends AppCompatActivity {


    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.tv_bouti_cate_name)
    TextView mTvBoutiCateName;

    @BindView(R.id.recy_boutique_cate)
    RecyclerView mRecyBoutiqueCate;

    CommonAdapter<NewGoodsBean> mCommonAdapter;
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
        mTvBoutiCateName.setText(cateName);
        catId = getIntent().getIntExtra(I.Boutique.CAT_ID, -1);
        if (catId < 0) {
            finish();
        }
        mContext = this;
        mList = new ArrayList<>();
        initData();
        initView();
    }

    private void initData() {

        NetDao.downNeworBoutiqueGoods(this, catId, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> goodsBeen = ConvertUtils.array2List(result);
                    mList = goodsBeen;
                    mCommonAdapter.initData(goodsBeen);
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private void initView() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MFGT.finish(this);
    }
}
