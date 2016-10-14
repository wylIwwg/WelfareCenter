package cn.wyl.welfarecenter;

import android.app.Application;
import android.content.Context;

import cn.wyl.welfarecenter.activity.MainActivity;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/13 21:16
 */
public class WelfareCenterApplication extends Application {

    public static Context getInstance(){
        return  new MainActivity();
    }
}
