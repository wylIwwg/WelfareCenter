package cn.wyl.welfarecenter.model.net;

import android.content.Context;

import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.bean.CategoryChildBean;
import cn.wyl.welfarecenter.bean.CategoryGroupBean;
import cn.wyl.welfarecenter.bean.NewGoodsBean;
import cn.wyl.welfarecenter.model.utils.OkHttpUtils;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/11/21 18:12
 */
public class ModelCategory implements IModelCategory {
    @Override
    public void downCategoryGroup(Context context, onCompleteListener<CategoryGroupBean[]> listener) {
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    @Override
    public void downCategoryChild(Context context, int catId, int pageId, onCompleteListener<CategoryChildBean[]> listener) {
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID, catId + "")
                .addParam(I.PAGE_ID, pageId + "")
                .addParam(I.PAGE_SIZE, I.PAGE_SIZE_DEFAULT + "")
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

    @Override
    public void downCategoryGoods(Context context, int catId, int pageId, onCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam("cat_id", catId + "")
                .addParam(I.PAGE_ID, pageId + "")
                .addParam(I.PAGE_SIZE, I.PAGE_SIZE_DEFAULT + "")
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
}
