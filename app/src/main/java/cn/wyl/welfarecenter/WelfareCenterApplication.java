package cn.wyl.welfarecenter;

import android.app.Application;
import android.content.Context;

import cn.wyl.welfarecenter.bean.UserAvatar;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/13 21:16
 */
public class WelfareCenterApplication extends Application {
    static private WelfareCenterApplication instance = null;
    static private UserAvatar user;

    public static UserAvatar getUser() {
        return user;
    }

    public static void setUser(UserAvatar user) {
        WelfareCenterApplication.user = user;
    }

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
