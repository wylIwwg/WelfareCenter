package cn.wyl.welfarecenter.model.dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/24 14:59
 */
public class SharedPreferencesUtils {
    private SharedPreferences.Editor mEditor;
    private static SharedPreferencesUtils instance;
    private  SharedPreferences mSharedPreferences;
    private static final String SHARE_NAME = "userinfo";
    private static final String SHARE_KEY_USERNAME = "share_key_username";

    public SharedPreferencesUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);

        mEditor = mSharedPreferences.edit();
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesUtils(context);
        }
        return instance;
    }

    public void saveUserInfo(String userName) {
        mEditor.putString(SHARE_KEY_USERNAME, userName);
        mEditor.commit();
    }

    public String getUserName() {
       return mSharedPreferences.getString(SHARE_KEY_USERNAME, null);
    }

    public void removeUser(){
        mEditor.remove(SHARE_KEY_USERNAME);
        mEditor.commit();
    }
}
