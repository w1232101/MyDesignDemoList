/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by maning on 16/3/5.
 */
public class GankMMHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "GankMM";
    public static final String TABLE_NAME_COLLECT = "collect";
    public static final String TABLE_NAME_PUBLIC = "public";
    private static final int version = 1;

    /**
     * _id : 56d6481e6776592a03e624a4
     * _ns : ganhuo
     * createdAt : 2016-03-02T09:55:42.63Z
     * desc : 3.2
     * publishedAt : 2016-03-02T12:06:37.242Z
     * source : chrome
     * type : 福利
     * url : http://ww3.sinaimg.cn/large/7a8aed7bjw1f1ia8qj5qbj20nd0zkmzp.jpg
     * used : true
     * who : 张涵宇
     */
    private static final String ID = "id";
    public static final String GankID = "gankID";
    public static final String NS = "_ns";
    public static final String createdAt = "createdAt";
    public static final String desc = "desc";
    public static final String publishedAt = "publishedAt";
    public static final String source = "source";
    public static final String type = "type";
    public static final String url = "url";
    public static final String used = "used";
    public static final String who = "who";


    //收藏表
    private static final String sql_collect = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_COLLECT + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GankID + " TEXT, "
            + NS + " TEXT, "
            + createdAt + " TEXT, "
            + desc + " TEXT, "
            + publishedAt + " TEXT, "
            + source + " TEXT, "
            + type + " TEXT, "
            + url + " TEXT, "
            + used + " TEXT, "
            + who + " TEXT)";

    //公共阅读表
    private static final String sql_public = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_PUBLIC + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GankID + " TEXT, "
            + NS + " TEXT, "
            + createdAt + " TEXT, "
            + desc + " TEXT, "
            + publishedAt + " TEXT, "
            + source + " TEXT, "
            + type + " TEXT, "
            + url + " TEXT, "
            + used + " TEXT, "
            + who + " TEXT)";

    public GankMMHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql_collect);
        db.execSQL(sql_public);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
