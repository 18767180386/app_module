package com.aiju.zyb.data.dbhelp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aiju.zyb.bean.HistoryRerordBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AIJU on 2017-05-21.
 */

public class DBManager  {
        private DBHelper helper;
        private SQLiteDatabase db;

        public DBManager(Context context) {
            helper = new DBHelper(context);
            db = helper.getWritableDatabase();
        }

        public void save(List<HistoryRerordBean> shopList) {
            db.beginTransaction();  //开始事务
            try {

                for (HistoryRerordBean shop : shopList) {
                    db.execSQL("delete from " + helper.getTableName() + " where name=? ", new Object[]{shop.getKeyword()});
                    db.execSQL("insert into " + helper.getTableName() + " (name) values  (?)",
                            new Object[]{shop.getKeyword()});
                }

                db.setTransactionSuccessful();  //设置事务成功完成
            } finally {
                db.endTransaction();    //结束事务
            }

        }

        public void update(HistoryRerordBean shop) {
            db.execSQL("update " + helper.getTableName() + " set name=? where pid=? ", new Object[]{shop.getKeyword()});
        }

        public HistoryRerordBean find(Integer id) {
            Cursor cursor = db.rawQuery(
                    "select * from "+helper.getTableName()+" where tagindex=?",
                    new String[]{String.valueOf(id)});
            if (cursor.moveToNext()) {
                // return new Person(cursor.getInt(0), cursor.getString(1), cursor.getShort(2));
            }
            return null;
        }

        public void delete(Integer... ids) {
            if (ids.length > 0) {
                StringBuffer sb = new StringBuffer();
                for (Integer id : ids) {
                    sb.append('?').append(',');
                }
                sb.deleteCharAt(sb.length() - 1);
                db.execSQL(
                        "delete from " + helper.getTableName() + " where tagindex in(" + sb.toString()
                                + ")", ids);
            }
        }

        public void delAll() {
            db.execSQL("delete from " + helper.getTableName() + " ");
        }


        public List<HistoryRerordBean> getScrollData(String sql) {
            List<HistoryRerordBean> shops = new ArrayList<HistoryRerordBean>();
            Cursor cursor = null;
            long old = 0;
            try {
                old = System.currentTimeMillis();
                cursor = db.rawQuery("select name from " + helper.getTableName(), null);
                while (cursor.moveToNext()) {
                    HistoryRerordBean shop = new HistoryRerordBean();
                    shop.setKeyword(cursor.getString(cursor.getColumnIndex("name")));
                    shops.add(shop);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
            long newTime = System.currentTimeMillis();
            long l = newTime - old;
            Log.d("DBManager", l / 1000 + "");
            return shops;
        }

        private Date getDate(long old, long newTime) {
            return new Date(newTime - old);
        }

        // 获取分页数据，提供给SimpleCursorAdapter使用。
        public Cursor getRawScrollData(int startResult, int maxResult) {
            List<HistoryRerordBean> persons = new ArrayList<HistoryRerordBean>();
            return db.rawQuery(
                    "select personid as _id ,name,age from person limit ?,?",
                    new String[]{String.valueOf(startResult),
                            String.valueOf(maxResult)});

        }

        public int getCount() {
            Cursor cur = db.rawQuery("SELECT count(*) as num FROM " + helper.getTableName() + "", null);
            int ret = 0;
            if (null != cur) {
                cur.moveToFirst();
                if (!cur.isAfterLast()) {
                    ret = cur.getInt(cur.getColumnIndex("num"));
                }
                cur.close();
            }
            // closeDB();
            return ret;

        }


        public void closeDB() {
            db.close();
        }


}