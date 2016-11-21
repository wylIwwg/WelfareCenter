package cn.wyl.welfarecenter.model.net;

import android.content.Context;

import cn.wyl.welfarecenter.bean.GoodsDetailsBean;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/11/21 18:40
 */
public interface IModelGoodsDetails {
    void downGoodsDetails(Context context, int goodId, onCompleteListener<GoodsDetailsBean> listener);


}
