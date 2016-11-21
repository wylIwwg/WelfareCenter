package cn.wyl.welfarecenter.model.net;

import android.content.Context;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/11/21 17:36
 */
public interface IModelLogin {
    void  login(Context context, String name, String password,onCompleteListener<String> listener);
}
