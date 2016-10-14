package cn.wyl.welfarecenter.activity;

import android.os.Bundle;
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
        new Thread() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                long costTime = System.currentTimeMillis() - startTime;
                if (sleepTime - costTime > 0) {
                    try {
                        Thread.sleep(sleepTime - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                MFGT.startActivity(SplashActivity.this,LoginActivity.class);
                MFGT.finish(SplashActivity.this);
            }
        }.start();
    }
}
