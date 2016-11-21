package cn.wyl.welfarecenter.controller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.model.dao.SharedPreferencesUtils;
import cn.wyl.welfarecenter.model.dao.UserDao;
import cn.wyl.welfarecenter.model.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private long sleepTime = 2000;
    SplashActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                UserAvatar user = WelfareCenterApplication.getUser();
                Log.e("main", "wel+" + user);
                String userName = SharedPreferencesUtils.getInstance(mContext).getUserName();
                Log.e("main","sahre+"+userName);
                if (user == null&&userName!=null) {
                    UserDao dao = new UserDao(mContext);
                    user = dao.getUser(userName);
                    WelfareCenterApplication.setUser(user);
                    Log.e("main", "data++" + user);
                }
                MFGT.startActivity(SplashActivity.this, MainActivity.class);
                finish();
            }
        }, sleepTime);

    }
}
