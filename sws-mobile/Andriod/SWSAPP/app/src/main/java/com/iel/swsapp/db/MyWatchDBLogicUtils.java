package com.iel.swsapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iel.swsapp.utils.StasticUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * db的逻辑
 * Created by yxnne on 2018/4/26.
 */

public class MyWatchDBLogicUtils {


    public static String RESULT_RATE_ALL_OBEY = "RESULT_RATE_ALL_OBEY";
    public static String RESULT_RATE_ALL_CORRECT = "RESULT_RATE_ALL_CORRECT";
    public static String RESULT_RATE_OCCASION_BEFORE_CONTACT = "RESULT_RATE_OCCASION_BEFORE_CONTACT";
    public static String RESULT_RATE_OCCASION_AFTER_CONTACT = "RESULT_RATE_OCCASION_AFTER_CONTACT";
    public static String RESULT_RATE_OCCASION_BEFORE_ASPETIC = "RESULT_RATE_OCCASION_BEFORE_ASPETIC";
    public static String RESULT_RATE_OCCASION_AFTER_LIQUID = "RESULT_RATE_OCCASION_AFTER_LIQUID";
    public static String RESULT_RATE_OCCASION_AFTER_ENV = "RESULT_RATE_OCCASION_AFTER_ENV";



    /**
     * find All records, 得到全部数据
     * @param context context
     * @return List<OneRecord>
     */
    public static List<OneRecord> getAllRecords(Context context){
        MyWatchStatisticDatabaseHelper mDBHelper = new MyWatchStatisticDatabaseHelper(
                context, "sws.db", null, 1);
        List<OneRecord> mRecordData = new ArrayList<>();
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Record", null);

        if (cursor.moveToFirst()){
            do{

                OneRecord one = new OneRecord();
                one.id = cursor.getInt(cursor.getColumnIndex("id"));
                one.obey = cursor.getString(cursor.getColumnIndex("obey"));
                one.name = cursor.getString(cursor.getColumnIndex("name"));
                one.depart = cursor.getString(cursor.getColumnIndex("depart"));
                one.role = cursor.getString(cursor.getColumnIndex("role"));
                one.nbr = cursor.getString(cursor.getColumnIndex("nbr"));
                one.occasion = cursor.getString(cursor.getColumnIndex("occasion"));
                one.way = cursor.getString(cursor.getColumnIndex("way"));
                one.correct = cursor.getString(cursor.getColumnIndex("right"));
                one.time = cursor.getLong(cursor.getColumnIndex("date")) ;
                //cursor.get
                Logger.i("one.time" +one.time);
                // add to data list
                mRecordData.add(one);

            }while(cursor.moveToNext());
        }
        cursor.close();

        return mRecordData;
    }

    /**
     * 根据id 删除Record中的数据
     * @param context 上下文
     * @param id id
     * @return 结果
     */
    public static boolean deleteRecordById(Context context, int id){

        MyWatchStatisticDatabaseHelper mDBHelper = new MyWatchStatisticDatabaseHelper(
                context, "sws.db", null, 1);

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        try{
            db.delete("Record","id=?", new String[]{"" + id });
        }catch (Exception e){
            return false;
        }

        return true;
    }

    /**
     * 查询所有集合，计算数据集合
     * @param context context
     * @return 数据集合，键值对
     */
    public static Map<String, Double> getRatesFromAll(Context context){
        return getRates(getAllRecords(context));
    }

    /**
     * 根据查询结果算时机
     * @param datas 查询结果
     * @return 数据集合
     */
    private static Map<String, Double> getRates(List<OneRecord> datas){

        int all = datas.size();
        // 依从率分子次数统计
        int obey = 0;
        int correct = 0;
        // 接触患者前
        int bContactAll = 0;
        int bcontactObey = 0;
        // 接触患者后
        int aContactAll = 0;
        int acontactObey = 0;

        // 无菌操作前
        int bAspeticAll = 0;
        int bAspeticObey = 0;

        // 无菌操作前
        int aLiquidAll = 0;
        int aLiquidObey = 0;

        // 接触患者环境后
        int aEnvAll = 0;
        int aEnvObey = 0;

        for (OneRecord one : datas) {

            if(one.obey.equals( "依从" )){
                obey ++;

                // 统计正确率
                if (one.correct.equals("完全正确")){
                    correct ++ ;
                }
            }

            if(one.occasion.equals("接触患者前")){
                bContactAll ++;
                if(one.obey.equals( "依从" )){
                    bcontactObey ++;
                }
            }
            if(one.occasion.equals("接触患者后")){
                aContactAll ++;
                if(one.obey.equals( "依从" )){
                    acontactObey ++;
                }
            }
            if(one.occasion.equals("无菌操作前")){
                bAspeticAll ++;
                if(one.obey.equals( "依从" )){
                    bAspeticObey ++;
                }
            }
            if(one.occasion.equals("接触患者体液后")){
                aLiquidAll ++;
                if(one.obey.equals( "依从" )){
                    aLiquidObey ++;
                }
            }
            if(one.occasion.equals("接触环境前")){
                aEnvAll ++;
                if(one.obey.equals( "依从" )){
                    aEnvObey ++;
                }
            }

        }

        // 计算 各种率
        // 总依从率：
        double rateAll = StasticUtil.calcRate(obey, all - obey);
        double rateCorrect = StasticUtil.calcRate(obey, obey - correct);

        // 时机
        // 接触患者前
        double rateBeforeContact = StasticUtil.calcRate(
                bcontactObey, bContactAll - bcontactObey);

        // 接触患者后
        double rateAfterContact = StasticUtil.calcRate(
                acontactObey, aContactAll - acontactObey);

        // 无菌操作前
        double rateBeforeAspetic = StasticUtil.calcRate(
                bAspeticObey, bAspeticAll - bAspeticObey);

        // 接触体液后
        double rateAfterLiquid = StasticUtil.calcRate(
                aLiquidObey, aLiquidAll - aLiquidObey);

        // 接触患者环境后
        double rateAfterEnv = StasticUtil.calcRate(
                aEnvObey, aEnvAll - aEnvObey);

        // 放置结果
        Map<String, Double> resultMap = new HashMap<>();

        resultMap.put(RESULT_RATE_ALL_OBEY, rateAll);
        resultMap.put(RESULT_RATE_ALL_CORRECT, rateCorrect);
        resultMap.put(RESULT_RATE_OCCASION_BEFORE_CONTACT, rateBeforeContact);
        resultMap.put(RESULT_RATE_OCCASION_AFTER_CONTACT, rateAfterContact);
        resultMap.put(RESULT_RATE_OCCASION_BEFORE_ASPETIC, rateBeforeAspetic);
        resultMap.put(RESULT_RATE_OCCASION_AFTER_LIQUID, rateAfterLiquid);
        resultMap.put(RESULT_RATE_OCCASION_AFTER_ENV, rateAfterEnv);

        return resultMap;
    }


}



