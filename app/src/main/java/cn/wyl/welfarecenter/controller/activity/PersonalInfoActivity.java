package cn.wyl.welfarecenter.controller.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.bean.Result;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.model.dao.SharedPreferencesUtils;
import cn.wyl.welfarecenter.model.net.NetDao;
import cn.wyl.welfarecenter.model.utils.ImageLoader;
import cn.wyl.welfarecenter.model.utils.MFGT;
import cn.wyl.welfarecenter.model.utils.OkHttpUtils;
import cn.wyl.welfarecenter.model.utils.OnSetAvatarListener;
import cn.wyl.welfarecenter.model.utils.ResultUtils;

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
    @BindView(R.id.btn_exchange)
    Button mBtnExchange;
    @BindView(R.id.lay_load_info)
    LinearLayout mLayLoadInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        mc = this;
        initData();


    }

    private void initData() {
        user = WelfareCenterApplication.getUser();
        if (user != null) {
            mTvMynick.setText(user.getMuserNick());
            mTvPersonName.setText(user.getMuserName());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, mImgAvatar);
            mBtnRelogin.setText("退出当前账号（" + user.getMuserName() + "）");
        }
    }

    @OnClick({R.id.img_back, R.id.btn_exchange, R.id.tv_mynick, R.id.btn_relogin, R.id.img_avatar_update, R.id.img_pr_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                MFGT.finish(this);
                break;
            case R.id.tv_mynick:
                // MFGT.startActivity(this,UpdateNickActivity.class);
                Intent intent = new Intent(this, UpdateNickActivity.class);
                startActivityForResult(intent, I.TO_UPDATE_NICK);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.btn_relogin:

                loginOut();
                break;
            case R.id.img_avatar_update:
                mAvatarListener = new OnSetAvatarListener(this, R.id.lay_load_info, user.getMuserName(), I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.img_pr_update:
                break;
            case R.id.btn_exchange:
                MFGT.startActivity(this,LoginActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == I.TO_UPDATE_NICK && (resultCode == RESULT_OK)) {
            Toast.makeText(PersonalInfoActivity.this, "更新昵称成功！", Toast.LENGTH_SHORT).show();
        }
        Log.e("main", "resultcode=" + resultCode);
        if (resultCode != RESULT_OK) {
            return;
        }
        mAvatarListener.setAvatar(requestCode, data, mImgAvatar);
        if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            updateAvatar();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    OnSetAvatarListener mAvatarListener;

    private void updateAvatar() {
        File file = new File(OnSetAvatarListener.getAvatarPath(this, user.getMavatarPath() + "/" + user.getMuserName() + I.AVATAR_SUFFIX_JPG));
        Log.e("main", file.exists() + "file?");
        Log.e("main", file.getAbsolutePath());
        NetDao.updateUserAvatar(this, user.getMuserName(), file, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, UserAvatar.class);
                Log.e("main", "re==" + result);
                if (result.getRetCode() == 0 && result.isRetMsg()) {
                    UserAvatar user = (UserAvatar) result.getRetData();
                    if (user != null) {
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mc, mImgAvatar);
                        WelfareCenterApplication.setUser(user);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });

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
