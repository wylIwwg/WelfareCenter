package cn.wyl.welfarecenter.model.net;

import android.content.Context;

import cn.wyl.welfarecenter.bean.Result;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/11/21 17:36
 */
public interface IModelRegister {
    void  register(Context context, String name, String nick, String password, onCompleteListener<Result> listener);
}
