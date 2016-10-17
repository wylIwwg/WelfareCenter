package cn.wyl.welfarecenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.adapters.NewGoodsAdapter;
import cn.wyl.welfarecenter.bean.NewGoodsBean;

/**
 * 添加闪屏
 */
public class MainActivity extends AppCompatActivity {
    NewGoodsAdapter mNewGoodsAdapter;
    List<NewGoodsBean> mList;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    @BindView(R.id.recyclerLayout)
    RecyclerView mRecyclerLayout;
    @BindView(R.id.swiperLayout)
    SwipeRefreshLayout mSwiperLayout;
    @BindView(R.id.rb_newgoods)
    RadioButton mRbNewgoods;
    @BindView(R.id.rb_boutique)
    RadioButton mRbBoutique;
    @BindView(R.id.rb_category)
    RadioButton mRbCategory;
    @BindView(R.id.rb_cart)
    RadioButton mRbCart;
    @BindView(R.id.tv_cart_hint)
    TextView mTvCartHint;
    @BindView(R.id.rb_center)
    RadioButton mRbCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        groupButton[0] = mRbNewgoods;
        groupButton[1] = mRbBoutique;
        groupButton[2] = mRbCategory;
        groupButton[3] = mRbCart;
        groupButton[4] = mRbCenter;

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerLayout);
        mList = new ArrayList<>();
        mNewGoodsAdapter = new NewGoodsAdapter(this, mList);
        mRecyclerView.setAdapter(mNewGoodsAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }


    RadioButton[] groupButton = new RadioButton[5];

    int index;

    public void onMenuButtonChanged(View view) {

        switch (view.getId()) {
            case R.id.rb_newgoods:
                index = 0;
                Log.d("main", "newgoods");
                break;
            case R.id.rb_boutique:
                index = 1;
                Log.d("main", "boutique");
                break;
            case R.id.rb_category:
                index = 2;
                Log.d("main", "category");
                break;

            case R.id.rb_center:
                index = 4;
                Log.d("main", "center");
                break;
            case R.id.rb_cart:
                index = 3;
                Log.d("main", "cart");
                break;
        }
        setRadioButtonStatus();
    }

    private void setRadioButtonStatus() {
        for (int i = 0; i < groupButton.length; i++) {
            if (i == index) {
                groupButton[i].setChecked(true);
            } else groupButton[i].setChecked(false);
        }
    }


}
