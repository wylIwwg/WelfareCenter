package cn.wyl.welfarecenter.model.net;

import android.content.Context;

import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.model.utils.MD5;
import cn.wyl.welfarecenter.model.utils.OkHttpUtils;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/11/21 17:39
 */
public class ModelLogin implements IModelLogin {
    @Override
    public void login(Context context, String name, String password, onCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, name)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);
    }
}
