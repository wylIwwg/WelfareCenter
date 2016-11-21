package cn.wyl.welfarecenter.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.wyl.welfarecenter.bean.UserAvatar;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/24 10:15
 */
public class DBManager {
    private static DBManager mManager = new DBManager();
    private DBOpenHelper helper;


    void initHelper(Context context) {

        helper=DBOpenHelper.initDB(context);
    }

    public static synchronized DBManager getInstance() {

        return mManager;
    }

    public synchronized void closeDB() {
        if (helper != null) {
            helper.closeDB();
        }

    }

    public synchronized boolean saveUser(UserAvatar user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDao.USER_COLUMN_NAME, user.getMuserName());
        values.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
        values.put(UserDao.USER_COLUMN_AVATAR_ID, user.getMavatarId());
        values.put(UserDao.USER_COLUMN_AVATAR_PATH, user.getMavatarPath());
        values.put(UserDao.USER_COLUMN_AVATAR_SUFFIX, user.getMavatarSuffix());
        values.put(UserDao.USER_COLUMN_AVATAR_TYPE, user.getMavatarType());
        values.put(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME, user.getMavatarLastUpdateTime());
        if (db.isOpen()) {

            return db.replace(UserDao.USER_TABLE_NAME, null, values) != -1;
        }
        return false;

    }

    public synchronized UserAvatar getUser(String userName) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from " + UserDao.USER_TABLE_NAME + " where "
                + UserDao.USER_COLUMN_NAME + " =?";
        UserAvatar user = null;
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        if (cursor.moveToNext()){
            user=new UserAvatar();
            user.setMuserName(userName);
            user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_NICK)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_ID)));
            user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH)));
            user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE)));
            user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX)));
            user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME)));
            return user;
        }
        return user;
    }

    public synchronized boolean updataUser(UserAvatar user) {
        int result=-1;
        SQLiteDatabase db=helper.getWritableDatabase();
        String sql =UserDao.USER_COLUMN_NAME+" =?";
        ContentValues values=new ContentValues();
        values.put(UserDao.USER_COLUMN_NICK,user.getMuserNick());
        if (db.isOpen()){
            result= db.update(UserDao.USER_TABLE_NAME,values,sql,new String[]{user.getMuserName()});
        }
        return result>0;
    }
}
