package com.sifanghao.weatherforecast.DBM;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sifanghao.weatherforecast.beans.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SiFanghao on 2015-10-19.
 */
public class CityDB {
    private SQLiteDatabase db=null;
    public static final String CITY_DB_NAME="city.db";
    private static final String CITY_TABLE_NAME="city";

    public CityDB(Context context,String path){
        db=context.openOrCreateDatabase(CITY_DB_NAME,Context.MODE_PRIVATE,null);
    }

    public List<City> getAllCity(){
        List<City> list=new ArrayList<City>();
        Cursor cursor=db.rawQuery("select * from "+CITY_TABLE_NAME,null);
        while(cursor.moveToNext()){
            list.add(new City(cursor.getString(cursor.getColumnIndex("province")),
                    cursor.getString(cursor.getColumnIndex("city")),
                    cursor.getString(cursor.getColumnIndex("number")),
            cursor.getString(cursor.getColumnIndex("firstpy")),
            cursor.getString(cursor.getColumnIndex("allpy")),
            cursor.getString(cursor.getColumnIndex("allfirstpy"))));
        }

        return list;
    }
}
