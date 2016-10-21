package cn.wyl.welfarecenter.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.adapters.NewGoodsAdapter;
import cn.wyl.welfarecenter.bean.NewGoodsBean;
import cn.wyl.welfarecenter.fragments.BoutiqueFragment;
import cn.wyl.welfarecenter.fragments.CartFragment;
import cn.wyl.welfarecenter.fragments.CategoryFragment;
import cn.wyl.welfarecenter.fragments.NewGoodsFragment;
import cn.wyl.welfarecenter.fragments.PersonFragment;
import cn.wyl.welfarecenter.utils.MFGT;

/**
 * 添加闪屏
 */
public class MainActivity extends BaseActivity {
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
    RadioButton[] groupButton = new RadioButton[5];
    Fragment[] mFragments = new Fragment[5];

    NewGoodsAdapter mNewGoodsAdapter;
    ArrayList<NewGoodsBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
        mList = new ArrayList<>();
        mNewGoodsAdapter = new NewGoodsAdapter(this, mList);
        groupButton[0] = mRbNewgoods;
        groupButton[1] = mRbBoutique;
        groupButton[2] = mRbCategory;
        groupButton[3] = mRbCart;
        groupButton[4] = mRbCenter;
        groupButton[0].setChecked(true);
        //首次加载选中新品页面的Fragment
        mFragments[0] = new NewGoodsFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, mFragments[0])

                .show(mFragments[0]).commit();
    }


    int index;

    public void onMenuButtonChanged(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (view.getId()) {
            case R.id.rb_newgoods:
                index = 0;
                if (mFragments[0] == null) {
                    mFragments[0] = new NewGoodsFragment();
                    transaction.add(R.id.frameLayout, mFragments[0]);
                } else
                    transaction.show(mFragments[0]);
                break;
            case R.id.rb_boutique:
                index = 1;
                if (mFragments[1] == null) {
                    mFragments[1] = new BoutiqueFragment();
                    transaction.add(R.id.frameLayout, mFragments[1]);
                } else
                    transaction.show(mFragments[1]);
                break;
            case R.id.rb_category:
                index = 2;
                if (mFragments[2] == null) {
                    mFragments[2] = new CategoryFragment();
                    transaction.add(R.id.frameLayout, mFragments[2]);
                } else transaction.show(mFragments[2]);
                break;

            case R.id.rb_center:
                index = 4;
                if (mFragments[4] == null) {
                    mFragments[4] = new PersonFragment();
                    transaction.add(R.id.frameLayout, mFragments[4]);
                } else
                    transaction.show(mFragments[4]);
                new AlertDialog.Builder(this).setTitle("未登录").setMessage("请先登录...").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MFGT.startActivity(MainActivity.this, LoginActivity.class);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setIcon(R.drawable.quest).create().show();


                break;
            case R.id.rb_cart:
                index = 3;
                if (mFragments[3] == null) {
                    mFragments[3] = new CartFragment();
                    transaction.add(R.id.frameLayout, mFragments[3]);
                } else
                    transaction.show(mFragments[3]);
                break;
        }
        setRadioButtonStatus();

        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        for (int i = 0; i < mFragments.length; i++) {
            if (mFragments[i] != null) {
                transaction.hide(mFragments[i]);
            }
        }

    }

    private void setRadioButtonStatus() {
        for (int i = 0; i < groupButton.length; i++) {
            if (i == index) {
                groupButton[i].setChecked(true);
            } else groupButton[i].setChecked(false);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
