package cn.wyl.welfarecenter.model.net;

import android.content.Context;

import cn.wyl.welfarecenter.bean.BoutiqueBean;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/11/21 18:05
 */
public interface IModelBoutique {
    void downBoutique(Context context, onCompleteListener<BoutiqueBean[]> listener);
}
