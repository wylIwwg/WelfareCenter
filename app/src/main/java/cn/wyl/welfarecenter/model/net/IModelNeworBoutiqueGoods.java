package cn.wyl.welfarecenter.model.net;

import android.content.Context;

import cn.wyl.welfarecenter.bean.NewGoodsBean;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/11/21 17:44
 */
public interface IModelNeworBoutiqueGoods {
    void downNeworBoutiqueGoods(Context context, int catId, int pageId, onCompleteListener<NewGoodsBean[]> listener);
}
