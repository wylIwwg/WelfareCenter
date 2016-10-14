package cn.wyl.welfarecenter.utils;

import android.widget.Toast;

import cn.wyl.welfarecenter.WelfareCenterApplication;

public class CommonUtils {
    public static void showLongToast(String msg){
        Toast.makeText(WelfareCenterApplication.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(String msg){
        Toast.makeText(WelfareCenterApplication.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(int rId){
        showLongToast(WelfareCenterApplication.getInstance().getString(rId));
    }
    public static void showShortToast(int rId){
        showShortToast(WelfareCenterApplication.getInstance().getString(rId));
    }
}
