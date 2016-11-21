package cn.wyl.welfarecenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.CategoryChildBean;
import cn.wyl.welfarecenter.bean.NewGoodsBean;
import cn.wyl.welfarecenter.controller.adapters.NewGoodsAdapter;
import cn.wyl.welfarecenter.model.net.ModelCategory;
import cn.wyl.welfarecenter.model.net.onCompleteListener;
import cn.wyl.welfarecenter.model.utils.CommonUtils;
import cn.wyl.welfarecenter.model.utils.ConvertUtils;
import cn.wyl.welfarecenter.model.utils.MFGT;
import cn.wyl.welfarecenter.views.CatChildFilterButton;
import cn.wyl.welfarecenter.views.SpaceItemDecoration;

public class CategoryGoodsActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.recycleLayout)
    RecyclerView mRecycleLayout;
    @BindView(R.id.swiper)
    SwipeRefreshLayout mSwiper;

    GridLayoutManager mGridLayoutManager;

    ArrayList<NewGoodsBean> mList;
    NewGoodsAdapter mNewGoodsAdapter;

    ModelCategory mCategory;

    int pageId = 1;
    int catId;
    @BindView(R.id.btn_price)
    Button mBtnPrice;
    @BindView(R.id.btn_time)
    Button mBtnTime;
    @BindView(R.id.btnCatChildFilter)
    CatChildFilterButton mBtnCatChildFilter;

    String cateName;
    ArrayList<CategoryChildBean> listChild = new ArrayList<>();
    @BindView(R.id.R_title)
    LinearLayout mRTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_goods);
        ButterKnife.bind(this);
        //获取传过来的值
        Intent intent = getIntent();
        catId = intent.getIntExtra(I.CategoryChild.CAT_ID, 0);
        cateName = intent.getStringExtra(I.CategoryGroup.NAME);
        listChild = (ArrayList<CategoryChildBean>) intent.getSerializableExtra("childList");
        if (catId == 0) {
            finish();
        }

        mCategory = new ModelCategory();

        mList = new ArrayList<>();
        initData(false);
        mNewGoodsAdapter = new NewGoodsAdapter(this, mList);
        initView();
    }

    private void initView() {
        //使用自定义布局文件设置内容
        mRTitle.setVisibility(View.GONE);

        mBtnCatChildFilter.setText(cateName);
        mBtnCatChildFilter.setOnCatFilterClickListener(cateName, listChild);

        mSwiper.setColorSchemeColors(getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecycleLayout.setAdapter(mNewGoodsAdapter);
        mRecycleLayout.setHasFixedSize(true);
        mRecycleLayout.setLayoutManager(mGridLayoutManager);
        mRecycleLayout.addItemDecoration(new SpaceItemDecoration(12));

        setListeners();
    }

    private void initData(final boolean isAll) {
        mCategory.downCategoryGoods(this, catId, pageId, new onCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mSwiper.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                mNewGoodsAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    mList = list;
                    if (isAll) {
                        mNewGoodsAdapter.initAllData(list);
                    } else {
                        mNewGoodsAdapter.initData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mNewGoodsAdapter.setMore(false);
                    }
                } else {
                    mNewGoodsAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                mSwiper.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                CommonUtils.showLongToast(error);
                mNewGoodsAdapter.setMore(false);
            }
        });

    }

    private void setListeners() {

        mSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageId = 1;
                mSwiper.setRefreshing(true);
                mSwiper.setEnabled(true);
                mTvRefresh.setVisibility(View.VISIBLE);

                initData(false);
            }
        });

        mRecycleLayout.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int position;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                position = mGridLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                position = mGridLayoutManager.findLastVisibleItemPosition();
                if (position >= mNewGoodsAdapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && mNewGoodsAdapter.isMore()) {
                    pageId++;
                    initData(true);
                }
            }
        });
    }

    int sortBy = I.SORT_BY_ADDTIME_ASC;
    boolean isTime, isPrice;

    /**
     * 根据布尔值判断箭头的走向，并设置排序flag
     *
     * @param view
     */
    @OnClick({R.id.btn_price, R.id.btn_time, R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_price:
                if (isPrice)
                    sortBy = I.SORT_BY_PRICE_ASC;
                else sortBy = I.SORT_BY_PRICE_DESC;
                mBtnPrice.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(isPrice ? R.drawable.arrow_order_up : R.drawable.arrow_order_down), null);
                isPrice = !isPrice;
                break;
            case R.id.btn_time:
                if (isTime)
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                else sortBy = I.SORT_BY_ADDTIME_DESC;
                mBtnTime.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(isPrice ? R.drawable.arrow_order_up : R.drawable.arrow_order_up), null);
                isTime = !isTime;

                break;
            case R.id.img_back:
                MFGT.finish(this);
                break;
        }
        //通知适配器更新排序
        mNewGoodsAdapter.setSortBy(sortBy);
    }

}
