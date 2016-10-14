package cn.wyl.welfarecenter.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private long sleepTime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {

                MFGT.startActivity(SplashActivity.this,MainActivity.class);
                finish();
            }
        },sleepTime);

    }
}
