package cn.wyl.welfarecenter.net;

import android.content.Context;

import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.bean.GoodsDetailsBean;
import cn.wyl.welfarecenter.bean.NewGoodsBean;
import cn.wyl.welfarecenter.utils.OkHttpUtils;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/17 15:18
 */
public class NetDao {
    public static void downLoadNewGoods(Context context, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, I.CAT_ID + "")
                .addParam(I.PAGE_ID, pageId + "")
                .addParam(I.PAGE_SIZE, I.PAGE_SIZE_DEFAULT + "")
                .targetClass(NewGoodsBean[].class)
                .execute(listener);

    }

    public static void downGoodsDetails(Context context, int goodId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener){
        OkHttpUtils utils=new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,goodId+"")
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

}
