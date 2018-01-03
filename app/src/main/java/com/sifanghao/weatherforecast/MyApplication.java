package com.sifanghao.weatherforecast;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.sifanghao.weatherforecast.DBM.CityDB;
import com.sifanghao.weatherforecast.beans.City;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SiFanghao on 2015-10-19.
 */
public class MyApplication extends Application{

    private List<City> allCityList=null;
    private Map<String,Map<String,String>> allCity=null;

    private static Application myApplication=null;

    @Override
    public void onCreate(){
        super.onCreate();
        myApplication=this;

        initCityData();

    }

    public static Application getInstance(){
        return myApplication;
    }

    private CityDB openCityDB() {
        String path = "/data" +
                Environment.getDataDirectory().getAbsolutePath() +
                File.separator + getPackageName() +
                File.separator + "databases" +
                File.separator + CityDB.CITY_DB_NAME;
        File db = new File(path);
        InputStream is = null;
        FileOutputStream fo = null;
        if(db.exists())
            db.delete();
        if (!db.exists()) {
            try {
                is = getAssets().open("city.db");
                db.createNewFile();
                fo = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fo.write(buffer);
                    fo.flush();
                }
                is.close();
                fo.close();
            } catch (IOException e) {
                Log.e("DBFileIOError", e.toString());
            }
        }
        return new CityDB(this,path);
    }

    private void initCityData(){
        String province,city,number;
        CityDB cityDB=openCityDB();
        allCityList=cityDB.getAllCity();
        allCity=new HashMap<String,Map<String,String>>();
        Map<String,String> list=null;
        for(City c : allCityList){
            province=c.getProvince();
            city=c.getCity();
            number=c.getNumber();
            if(allCity.containsKey(province)){
                list=allCity.get(province);
                list.put(city,number);
            }
            else{
                list=new HashMap<String, String>();
                list.put(city,number);
                allCity.put(province,list);
            }
        }
    }

    public List<City> getAllCityList() {
        return allCityList;
    }

    public Map<String, Map<String, String>> getAllCity() {
        return allCity;
    }
}
