package com.aiju.zyb.data.dbhelp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AIJU on 2017-05-21.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "history_data";
    private static final String TABLE_NAME="history_table";
    private static final int DATABASE_VERSION = 8;

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public String getTableName() {
        return TABLE_NAME;
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "  (pid integer primary key autoincrement,name varchar(600))");
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_NAME + "");
        onCreate(db);
    }
}