package com.iel.swsapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 *
 * 是为了实现观察统计手卫生的本地化存储；
 * Created by yxnne on 2018/4/24.
 */

public class MyWatchStatisticDatabaseHelper extends SQLiteOpenHelper{

    // 建表
    public static final String SQL_CREATE_RECORD = "create table Record(" +
            "id integer primary key autoincrement," +
            "depart text," +
            "role text," +
            "name text," +
            "nbr text," +
            "occasion text," +
            "obey text," +
            "way text," +
            "right text," +
            "date integer" +
            ")";


    private Context mContext;

    public MyWatchStatisticDatabaseHelper(Context context,
                                          String name,
                                          SQLiteDatabase.CursorFactory factory,
                                          int version) {
        super(context, name, factory, version);

        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RECORD);
        //Toast.makeText(mContext, "create db ok",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //
}
