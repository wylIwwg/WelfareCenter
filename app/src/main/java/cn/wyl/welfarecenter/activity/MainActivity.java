package cn.wyl.welfarecenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.adapters.NewGoodsAdapter;
import cn.wyl.welfarecenter.bean.NewGoodsBean;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.dao.SharedPreferencesUtils;
import cn.wyl.welfarecenter.fragments.BoutiqueFragment;
import cn.wyl.welfarecenter.fragments.CartFragment;
import cn.wyl.welfarecenter.fragments.CategoryFragment;
import cn.wyl.welfarecenter.fragments.NewGoodsFragment;
import cn.wyl.welfarecenter.fragments.PersonFragment;

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
    RadioButton perbtn;

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
                perbtn = (RadioButton) view;

                index = 4;
                if (mFragments[4] == null) {
                    mFragments[4] = new PersonFragment();
                    transaction.add(R.id.frameLayout, mFragments[4]);
                } else
                    transaction.show(mFragments[4]);


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

    @Override
    protected void onResume() {
        super.onResume();
        if (index==4&& WelfareCenterApplication.getUser()!=null) {
            PersonFragment fragment = new PersonFragment();

            getSupportFragmentManager().beginTransaction().hide(mFragments[4]).add(R.id.frameLayout, fragment).show(fragment).commit();
            mFragments[4] = fragment;
            setRadioButtonStatus();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case I.TO_LOGIN_AC:
                UserAvatar user = WelfareCenterApplication.getUser();
               if (user!=null) {
                   Log.e("main", "登录成功：" + user + "");
                   PersonFragment fragment = new PersonFragment();
                   getSupportFragmentManager().beginTransaction().hide(mFragments[4]).add(R.id.frameLayout, fragment).show(fragment).commit();
                   mFragments[4] = fragment;
                   setRadioButtonStatus();
               }
                break;
            case I.TO_PERSONAINFO_AC:
                if (WelfareCenterApplication.getUser() == null) {
                    PersonFragment fragment1 = new PersonFragment();
                    getSupportFragmentManager().beginTransaction().hide(mFragments[4]).add(R.id.frameLayout, fragment1).show(fragment1).commit();
                    mFragments[4] = fragment1;
                    setRadioButtonStatus();

                }
                break;
        }
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
        SharedPreferencesUtils.getInstance(this).removeUser();
        finish();
    }
}
