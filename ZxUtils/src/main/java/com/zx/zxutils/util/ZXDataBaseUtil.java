package com.zx.zxutils.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zx.zxutils.ZXApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiangb on 2017/3/31.
 * 功能：数据库工具
 */
public class ZXDataBaseUtil extends SQLiteOpenHelper {

    private static Map<String, ZXDataBaseUtil> dbMaps = new HashMap<>();
    private List<String> createTableList;//建表语句列表
    private String nowDbName;

    private ZXDataBaseUtil(String dbName, int dbVersion, List<String> tableSqls) {
        super(ZXApp.getContext(), dbName, null, dbVersion);
        nowDbName = dbName;
        createTableList = new ArrayList<>();
        createTableList.addAll(tableSqls);
    }

    /**
     * 获取数据库实例
     *
     * @param name
     * @param version
     * @param creatSqls create table if not exists table name (pName text, pAge text, pDate text)
     * @return
     */
    public static ZXDataBaseUtil getInstance(String name, int version, List<String> creatSqls) {
        ZXDataBaseUtil ZXDataBaseUtil = dbMaps.get(name);
        if (ZXDataBaseUtil == null) {
            ZXDataBaseUtil = new ZXDataBaseUtil(name, version, creatSqls);
        }
        dbMaps.put(name, ZXDataBaseUtil);
        return ZXDataBaseUtil;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String sqlString : createTableList) {
            db.execSQL(sqlString);
        }
    }


    ;


    /**
     * @param @param sql
     * @param @param bindArgs
     * @return void
     * @Title: execSQL
     * @Description: Sql写入
     * @author lihy
     */
    public void execSQL(String sql, Object[] bindArgs) {
        ZXDataBaseUtil ZXDataBaseUtil = dbMaps.get(nowDbName);
        synchronized (ZXDataBaseUtil) {
            SQLiteDatabase database = ZXDataBaseUtil.getWritableDatabase();
            database.execSQL(sql, bindArgs);
        }
    }

    /**
     * @param @param  sql查询
     * @param @param  bindArgs
     * @param @return
     * @return Cursor
     * @Title: rawQuery
     * @Description:
     * @author lihy
     */
    public Cursor rawQuery(String sql, String[] bindArgs) {
        ZXDataBaseUtil ZXDataBaseUtil = dbMaps.get(nowDbName);
        synchronized (ZXDataBaseUtil) {
            SQLiteDatabase database = ZXDataBaseUtil.getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, bindArgs);
            return cursor;
        }
    }

    /**
     * @param @param table
     * @param @param contentValues 设定文件
     * @return void 返回类型
     * @throws
     * @Title: insert
     * @Description: 插入数据
     * @author lihy
     */
    public void insert(String table, ContentValues contentValues) {
        ZXDataBaseUtil ZXDataBaseUtil = dbMaps.get(nowDbName);
        synchronized (ZXDataBaseUtil) {
            SQLiteDatabase database = ZXDataBaseUtil.getWritableDatabase();
            database.insert(table, null, contentValues);
        }
    }

    /**
     * @param @param table
     * @param @param values
     * @param @param whereClause
     * @param @param whereArgs 设定文件
     * @return void 返回类型
     * @throws
     * @Title: update
     * @Description: 更新
     */
    public void update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        ZXDataBaseUtil ZXDataBaseUtil = dbMaps.get(nowDbName);
        synchronized (ZXDataBaseUtil) {
            SQLiteDatabase database = ZXDataBaseUtil.getWritableDatabase();
            database.update(table, values, whereClause, whereArgs);
        }
    }

    /**
     * @param @param table
     * @param @param whereClause
     * @param @param whereArgs
     * @return void
     * @Title: delete
     * @Description:删除
     * @author lihy
     */
    public void delete(String table, String whereClause, String[] whereArgs) {
        ZXDataBaseUtil ZXDataBaseUtil = dbMaps.get(nowDbName);
        synchronized (ZXDataBaseUtil) {
            SQLiteDatabase database = ZXDataBaseUtil.getWritableDatabase();
            database.delete(table, whereClause, whereArgs);
        }
    }

    /**
     * @param @param table
     * @param @param columns
     * @param @param selection
     * @param @param selectionArgs
     * @param @param groupBy
     * @param @param having
     * @param @param orderBy
     * @return void
     * @Title: query
     * @Description: 查
     * @author lihy
     */
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        ZXDataBaseUtil ZXDataBaseUtil = dbMaps.get(nowDbName);
        synchronized (ZXDataBaseUtil) {
            SQLiteDatabase database = ZXDataBaseUtil.getReadableDatabase();
            // Cursor cursor = database.rawQuery("select * from "
            // + TableName.TABLE_NAME_USER + " where userId =" + userId, null);
            Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            return cursor;
        }
    }

    /**
     * @param table
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return Cursor
     * @Description:查
     * @exception:
     * @author: lihy
     * @time:2015-4-3 上午9:37:29
     */
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                        String orderBy, String limit) {
        ZXDataBaseUtil ZXDataBaseUtil = dbMaps.get(nowDbName);
        synchronized (ZXDataBaseUtil) {
            SQLiteDatabase database = ZXDataBaseUtil.getReadableDatabase();
            // Cursor cursor = database.rawQuery("select * from "
            // + TableName.TABLE_NAME_USER + " where userId =" + userId, null);
            Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            return cursor;
        }
    }

    /**
     * @param @return
     * @return Cursor
     * @Description 查询，方法重载,table表名，sqlString条件
     * @author lihy
     */
    public Cursor query(String tableName, String sqlString) {
        ZXDataBaseUtil ZXDataBaseUtil = dbMaps.get(nowDbName);
        synchronized (ZXDataBaseUtil) {
            SQLiteDatabase database = ZXDataBaseUtil.getReadableDatabase();
            Cursor cursor = database.rawQuery("select * from " + tableName + " " + sqlString, null);

            return cursor;
        }
    }

    /**
     * @see android.database.sqlite.SQLiteOpenHelper#close()
     */
    public void clear() {
        ZXDataBaseUtil ZXDataBaseUtil = dbMaps.get(nowDbName);
        ZXDataBaseUtil.close();
        dbMaps.remove(ZXDataBaseUtil);
    }

    /**
     * onUpgrade()方法在数据库版本每次发生变化时都会把用户手机上的数据库表删除，然后再重新创建。<br/>
     * 一般在实际项目中是不能这样做的，正确的做法是在更新数据库表结构时，还要考虑用户存放于数据库中的数据不会丢失,从版本几更新到版本几。(非
     * Javadoc)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
    }
}
