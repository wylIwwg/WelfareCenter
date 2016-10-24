package cn.wyl.welfarecenter.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.bean.UserAvatar;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/24 10:13
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static String DB_NAME = I.User.TABLE_NAME + ".db";
    private static int VERSION = 1;
    private static DBOpenHelper mHelper;

    static final String USER_TABLE_CREATE = "create table " +
            UserDao.USER_TABLE_NAME + " (" +
            UserDao.USER_COLUMN_NAME + " text primary key," +
            UserDao.USER_COLUMN_NICK + " text," +
            UserDao.USER_COLUMN_AVATAR_ID + " integer," +
            UserDao.USER_COLUMN_AVATAR_TYPE + " integer," +
            UserDao.USER_COLUMN_AVATAR_PATH + " text," +
            UserDao.USER_COLUMN_AVATAR_SUFFIX + " text," +
            UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME + " text);";

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static DBOpenHelper initDB(Context context) {
        if (mHelper == null) {
            mHelper = new DBOpenHelper(context.getApplicationContext());
        }
        return mHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void closeDB() {
        if (mHelper != null) {
            SQLiteDatabase db=mHelper.getReadableDatabase();
            db.close();
            mHelper = null;
        }
    }

    public boolean saveUser(UserAvatar user) {
        return false;
    }
}
