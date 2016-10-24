package cn.wyl.welfarecenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.dao.SharedPreferencesUtils;
import cn.wyl.welfarecenter.utils.MFGT;

public class PersonalInfoActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.img_avatar)
    ImageView mImgAvatar;
    @BindView(R.id.tv_mynick)
    TextView mTvMynick;
    @BindView(R.id.btn_relogin)
    Button mBtnRelogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.img_back, R.id.tv_mynick, R.id.btn_relogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                MFGT.finish(this);
                break;
            case R.id.tv_mynick:
                break;
            case R.id.btn_relogin:
                WelfareCenterApplication.setUser(null);
                SharedPreferencesUtils.getInstance(this).removeUser();

                MFGT.finish(this);
                break;
        }
    }
}
