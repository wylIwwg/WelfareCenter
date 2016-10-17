package cn.wyl.welfarecenter;

import android.app.Application;
import android.content.Context;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/13 21:16
 */
public class WelfareCenterApplication extends Application {
    static private WelfareCenterApplication instance = null;

    public WelfareCenterApplication() {
        instance = this;
    }

    public static Context getInstance() {
        if (instance == null) {
            instance = new WelfareCenterApplication();
        }
        return instance;
    }
}
