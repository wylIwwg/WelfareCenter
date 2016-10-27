package cn.wyl.welfarecenter.net;

import android.content.Context;

import java.io.File;

import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.bean.BoutiqueBean;
import cn.wyl.welfarecenter.bean.CartBean;
import cn.wyl.welfarecenter.bean.CategoryChildBean;
import cn.wyl.welfarecenter.bean.CategoryGroupBean;
import cn.wyl.welfarecenter.bean.CollectBean;
import cn.wyl.welfarecenter.bean.GoodsDetailsBean;
import cn.wyl.welfarecenter.bean.MessageBean;
import cn.wyl.welfarecenter.bean.NewGoodsBean;
import cn.wyl.welfarecenter.bean.Result;
import cn.wyl.welfarecenter.utils.MD5;
import cn.wyl.welfarecenter.utils.OkHttpUtils;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/17 15:18
 */
public class NetDao {
    public static void downNeworBoutiqueGoods(Context context, int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, catId + "")
                .addParam(I.PAGE_ID, pageId + "")
                .addParam(I.PAGE_SIZE, I.PAGE_SIZE_DEFAULT + "")
                .targetClass(NewGoodsBean[].class)
                .execute(listener);

    }

    public static void downGoodsDetails(Context context, int goodId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<GoodsDetailsBean>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID, goodId + "")
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    public static void downBoutique(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<BoutiqueBean[]>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }

    public static void downCategoryGroup(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener) {
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    public static void downCategoryChild(Context context, int catId, int pageId, OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener) {

        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID, catId + "")
                .addParam(I.PAGE_ID, pageId + "")
                .addParam(I.PAGE_SIZE, I.PAGE_SIZE_DEFAULT + "")
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

    public static void downCategoryGoods(Context context, int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam("cat_id", catId + "")
                .addParam(I.PAGE_ID, pageId + "")
                .addParam(I.PAGE_SIZE, I.PAGE_SIZE_DEFAULT + "")
                .targetClass(NewGoodsBean[].class)
                .execute(listener);


    }

    public static void doRegisterUser(Context context, String name, String nick, String password, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME, name)
                .addParam(I.User.NICK, nick)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(Result.class)
                .post()
                .execute(listener);
    }

    public static void doLogin(Context context, String name, String password, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, name)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);
    }

    public static void doFindUserByName(Context context, String name, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME, name)
                .targetClass(Result.class)
                .execute(listener);
    }

    public static void updateUserAvatar(Context context, String name, File file, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID, name)
                .addParam(I.AVATAR_TYPE, I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(String.class)
                .post()
                .execute(listener);
    }

    public static void updateUserNick(Context context, String name, String nick, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME, name)
                .addParam(I.User.NICK, nick)
                .targetClass(String.class)
                .execute(listener);
    }

    public static void findCollectCount(Context context, String name, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME, name)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void downCollects(Context context, String name, int pageId, OkHttpUtils.OnCompleteListener<CollectBean[]> listener) {
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME, name)
                .addParam(I.PAGE_ID, pageId + "")
                .addParam(I.PAGE_SIZE, I.PAGE_SIZE_DEFAULT + "")
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

    public static void deleteCollects(Context context, String name, int goodsId, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                .addParam(I.Collect.USER_NAME, name)
                .addParam(I.Collect.GOODS_ID, goodsId + "")
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void isCollected(Context context, String name, int goodsId, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Collect.GOODS_ID, goodsId + "")
                .addParam(I.Collect.USER_NAME, name)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void addCollects(Context context, String name, int goodsId, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_COLLECT)
                .addParam(I.Collect.GOODS_ID, goodsId + "")
                .addParam(I.Collect.USER_NAME, name)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void findCarts(Context context, String muserName, OkHttpUtils.OnCompleteListener<CartBean[]> listener) {
        OkHttpUtils<CartBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME, muserName)
                .targetClass(CartBean[].class)
                .execute(listener);
    }


    public static void addToCart(Context context, String muserName, int goodid,
                                 int count, int ischecked, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.GOODS_ID, goodid + "")
                .addParam(I.Cart.USER_NAME, muserName)
                .addParam(I.Cart.COUNT, count + "")
                .addParam(I.Cart.IS_CHECKED, ischecked + "")
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void updateCartCount(Context context, int cartId, int count,
                                       int ischecked, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID, cartId + "")
                .addParam(I.Cart.COUNT, count + "")
                .addParam(I.Cart.IS_CHECKED, ischecked + "")
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void deleteCart(Context context, int cartId,
                                   OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID, cartId + "")
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
