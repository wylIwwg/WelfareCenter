package cn.wyl.welfarecenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.adapters.CollectionAdapter;
import cn.wyl.welfarecenter.bean.CollectBean;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.CommonUtils;
import cn.wyl.welfarecenter.utils.ConvertUtils;
import cn.wyl.welfarecenter.utils.MFGT;
import cn.wyl.welfarecenter.utils.OkHttpUtils;
import cn.wyl.welfarecenter.views.SpaceItemDecoration;

public class CollectsActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.tv_bouti_cate_name)
    TextView mTvBoutiCateName;
    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.recycleLayout)
    RecyclerView mRecycleLayout;
    @BindView(R.id.swiper)
    SwipeRefreshLayout mSwiper;

    CollectionAdapter mAdapter;
    Context mContext;

    int pageId = 1;
    UserAvatar user;
    String username;
    ArrayList<CollectBean> mList;
    GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collects);
        mContext = this;
        user= WelfareCenterApplication.getUser();
        if (user!=null){
            username=user.getMuserName();
        }else {
            finish();
        }
        ButterKnife.bind(this);
        mTvBoutiCateName.setText("收藏的宝贝");
        mSwiper.setColorSchemeColors(getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        mGridLayoutManager = new GridLayoutManager(mContext, 2);
        mList = new ArrayList<>();
        initData(false);
        mAdapter = new CollectionAdapter(mContext, mList);
        mRecycleLayout.setAdapter(mAdapter);
        mRecycleLayout.setHasFixedSize(true);
        mRecycleLayout.setLayoutManager(mGridLayoutManager);
        mRecycleLayout.addItemDecoration(new SpaceItemDecoration(12));

        setListeners();
    }

    private void setListeners() {

        mAdapter.setOnItemCKListener(new CollectionAdapter.OnItemCKListener() {
            @Override
            public void onItemClicK(View view, int position) {

                CollectBean goods = (CollectBean) view.getTag();
                Intent intent = new Intent(mContext, GoodsDetailsActivity.class);
                intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goods.getGoodsId());
                startActivity(intent);
            }
        });


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
                if (position >= mAdapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && mAdapter.isMore()) {
                    pageId++;
                    initData(true);
                }
            }
        });
    }

    private void initData(final boolean isAll) {

        NetDao.downCollects(mContext, username, pageId, new OkHttpUtils.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                mSwiper.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    mList = list;
                    if (isAll) {
                        mAdapter.initAllData(list);
                    } else {
                        mAdapter.initData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                } else {
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                mSwiper.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                CommonUtils.showLongToast(error);
                mAdapter.setMore(false);
            }
        });

    }


    @OnClick(R.id.img_back)
    public void finishAc(View view){
        MFGT.finish(this);
    }
}
