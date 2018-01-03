package com.sifanghao.weatherforecast;

import android.content.Intent;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sifanghao.weatherforecast.beans.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectCityActivity extends AppCompatActivity {

    private ImageView back;
    private TextView title_name;
    private ListView city_list;

    public static final int TYPE_PROVINCE=1;
    public static final int TYPE_City=2;

    private int currentType;
    private String currentCity;
    private List<String> allCityList;
    private List<String> allCityNumberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        Intent intent=getIntent();

        currentType=intent.getIntExtra("currentType", 1);
        currentCity=intent.getStringExtra("currentCity");


        title_name=(TextView)findViewById(R.id.select_city_title_name);
        back=(ImageView)findViewById(R.id.select_city_back);
        city_list=(ListView)findViewById(R.id.city_list);

        title_name.setText("当前城市：" +currentCity );
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentType == TYPE_PROVINCE)
                    finish();
                else if (currentType == TYPE_City) {
                    currentType=TYPE_PROVINCE;
                    initViewProvince();
                    updateView();
                }
            }
        });

        allCityList=new ArrayList<String>();
        if(currentType==TYPE_PROVINCE)
            initViewProvince();
        else if(currentType==TYPE_City)
            initViewCity();

        updateView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_city, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViewProvince(){
        allCityList.clear();
        Map<String ,Map<String,String>> allCity=((MyApplication)MyApplication.getInstance()).getAllCity();
                for(Map.Entry<String,Map<String,String>> e : allCity.entrySet()){
                    allCityList.add(e.getKey());
                }

                city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent=new Intent(SelectCityActivity.this,SelectCityActivity.class);
//                        intent.putExtra("currentType",SelectCityActivity.TYPE_City);
//                        intent.putExtra("currentCity",allCityList.get(position));
//                        startActivity(intent);
                        currentType=TYPE_City;
                        currentCity=allCityList.get(position);
                        initViewCity();
                        updateView();
            }
        });
    }

    private void initViewCity(){
        allCityList.clear();
        allCityNumberList=new ArrayList<String>();
        Map<String ,Map<String,String>> allCity= ((MyApplication)MyApplication.getInstance()).getAllCity();
        for(Map.Entry<String,String> e : allCity.get(currentCity).entrySet()){
            allCityList.add(e.getKey());
            allCityNumberList.add(e.getValue());
        }

        city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SelectCityActivity.this,MainActivity.class);
                intent.putExtra("currentCityNumber",allCityNumberList.get(position));
                intent.putExtra("currentCity",allCityList.get(position));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void updateView(){
        String[] citys=new String[allCityList.size()];
        allCityList.toArray(citys);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,citys);
        city_list.setAdapter(adapter);
    }
}
