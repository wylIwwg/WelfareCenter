package cn.wyl.welfarecenter.activity;

import android.support.v7.app.AppCompatActivity;

import cn.wyl.welfarecenter.utils.MFGT;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/19 17:00
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MFGT.finish(this);
    }
}
