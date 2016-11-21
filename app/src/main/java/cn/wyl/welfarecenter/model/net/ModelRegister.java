package cn.wyl.welfarecenter.model.net;

import android.content.Context;

import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.bean.Result;
import cn.wyl.welfarecenter.model.utils.MD5;
import cn.wyl.welfarecenter.model.utils.OkHttpUtils;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/11/21 17:39
 */
public class ModelRegister implements IModelRegister {
    @Override
    public void register(Context context, String name, String nick, String password, onCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME, name)
                .addParam(I.User.NICK, nick)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(Result.class)
                .post()
                .execute(listener);
    }
}
