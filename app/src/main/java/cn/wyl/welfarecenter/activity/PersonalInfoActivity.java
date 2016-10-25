package cn.wyl.welfarecenter.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.dao.SharedPreferencesUtils;
import cn.wyl.welfarecenter.utils.ImageLoader;
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

    UserAvatar user;
    @BindView(R.id.tv_person_name)
    TextView mTvPersonName;
    @BindView(R.id.img_avatar_update)
    ImageView mImgAvatarUpdate;
    @BindView(R.id.img_pr_update)
    ImageView mImgPrUpdate;
    PersonalInfoActivity mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        mc = this;
        user = WelfareCenterApplication.getUser();
        if (user != null) {
            mTvMynick.setText(user.getMuserNick());
            mTvPersonName.setText(user.getMuserName());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, mImgAvatar);
            mBtnRelogin.setText("退出当前账号（" + user.getMuserName() + "）");
        }


    }

    @OnClick({R.id.img_back, R.id.tv_mynick, R.id.btn_relogin, R.id.img_avatar_update, R.id.img_pr_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                MFGT.finish(this);
                break;
            case R.id.tv_mynick:
                break;
            case R.id.btn_relogin:

                loginOut();
                break;
            case R.id.img_avatar_update:
                updateAvatar();
                break;
            case R.id.img_pr_update:
                break;
        }
    }

    private void updateAvatar() {

    }

    private void loginOut() {
        new AlertDialog.Builder(this).setTitle("退出登录")
                .setIcon(getResources().getDrawable(R.drawable.loginout))
                .setMessage("确认退出当前账号：" + user.getMuserName() + "?").setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WelfareCenterApplication.setUser(null);
                SharedPreferencesUtils.getInstance(mc).removeUser();
                MFGT.finish(mc);
            }
        }).setNegativeButton("取消", null).create().show();

    }

}
