package cn.wyl.welfarecenter.model.utils;

import android.app.Activity;
import android.content.Intent;

import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.controller.activity.GoodsDetailsActivity;
import cn.wyl.welfarecenter.controller.activity.LoginActivity;
import cn.wyl.welfarecenter.controller.activity.MainActivity;
import cn.wyl.welfarecenter.controller.activity.PersonalInfoActivity;
import cn.wyl.welfarecenter.controller.activity.RegisterActivity;


public class MFGT {

    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void gotoMainActivity(Activity context) {
        startActivity(context, MainActivity.class);
    }

    public static void startActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void startActivity(Activity context, Intent intent) {

        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoLogin(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);

        context.startActivityForResult(intent, I.TO_LOGIN_AC);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void goPersonInfoActivity(Activity context){

        Intent intent = new Intent(context, PersonalInfoActivity.class);
        context.startActivityForResult(intent, I.TO_PERSONAINFO_AC);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoRegisterActivity(Activity context){

        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivityForResult(intent, I.TO_REGISTER_AC);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoGoodsDetailsAC(Activity context, int goodsId) {
        Intent intent=new Intent(context, GoodsDetailsActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoOrderAC(Activity context, Intent intent) {
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }
}
