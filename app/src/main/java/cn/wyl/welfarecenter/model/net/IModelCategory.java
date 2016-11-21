package cn.wyl.welfarecenter.model.net;

import android.content.Context;

import cn.wyl.welfarecenter.bean.CategoryChildBean;
import cn.wyl.welfarecenter.bean.CategoryGroupBean;
import cn.wyl.welfarecenter.bean.NewGoodsBean;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/11/21 18:09
 */
public interface IModelCategory {
      void downCategoryGroup(Context context, onCompleteListener<CategoryGroupBean[]> listener) ;

      void downCategoryChild(Context context, int catId, int pageId, onCompleteListener<CategoryChildBean[]> listener) ;

      void downCategoryGoods(Context context, int catId, int pageId, onCompleteListener<NewGoodsBean[]> listener);

      }
