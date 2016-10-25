package cn.wyl.welfarecenter.dao;

import android.content.Context;

import cn.wyl.welfarecenter.bean.UserAvatar;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/24 10:46
 */
public class UserDao {
    public static final String USER_TABLE_NAME = "t_superwechat_user";
    public static final String USER_COLUMN_NAME = "m_user_name";
    public static final String USER_COLUMN_NICK = "m_user_nick";
    public static final String USER_COLUMN_AVATAR_ID = "m_user_avatar_id";
    public static final String USER_COLUMN_AVATAR_PATH = "m_user_avatar_path";
    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_user_avatar_suffix";
    public static final String USER_COLUMN_AVATAR_TYPE = "m_user_avatar_type";
    public static final String USER_COLUMN_AVATAR_LASTUPDATE_TIME = "m_user_avatar_lastupdatatime";

    public UserDao(Context context) {
        DBManager.getInstance().initHelper(context);
    }

    public boolean saveUser(UserAvatar user) {
        return DBManager.getInstance().saveUser(user);
    }

    public boolean updateUser(UserAvatar user) {
        return DBManager.getInstance().updataUser(user);
    }

    public void closeDB(){
        DBManager.getInstance().closeDB();
    }
    public UserAvatar getUser(String userName) {
        return DBManager.getInstance().getUser(userName);

    }
}
