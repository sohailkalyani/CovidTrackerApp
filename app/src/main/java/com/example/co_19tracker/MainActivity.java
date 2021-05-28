package com.example.co_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.co_19tracker.api.ApiUtilities;
import com.example.co_19tracker.api.CounterData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView totalConfirm,todayConfirm,totalRecovered,todayRecovered,totalDeath,todayDeath,totalTest,totalActive,date;
    private List<CounterData> list;
    PieChart pieChart;
    String country="India";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=new ArrayList<>();
        if(getIntent().getStringExtra("country")!=null)
            country=getIntent().getStringExtra("country");
        init();
        TextView cname=findViewById(R.id.cname);

        cname.setText(country);
        cname.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this,CountryActivity.class)));
        ApiUtilities.getApiInterface().getCounterData().enqueue(new Callback<List<CounterData>>() {
            @Override
            public void onResponse(Call<List<CounterData>> call, Response<List<CounterData>> response) {
              list.addAll(response.body());

              for(int i=0;i<list.size();i++){
                  if(list.get(i).getCountry().equals(country)){

                      int confirm=Integer.parseInt(list.get(i).getCases());
                      int deaths=Integer.parseInt(list.get(i).getDeaths());
                      int recovered=Integer.parseInt(list.get(i).getRecovered());
                      int active=Integer.parseInt(list.get(i).getActive());
                      int tested=Integer.parseInt(list.get(i).getTests());

                      totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                      totalActive.setText(NumberFormat.getInstance().format(active));
                      totalDeath.setText(NumberFormat.getInstance().format(deaths));
                      totalRecovered.setText(NumberFormat.getInstance().format(recovered));

                      todayDeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                      todayConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                      todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));
                      totalTest.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));

                      setText(list.get(i).getUpdated());

                      pieChart.addPieSlice(new PieModel("CONFIRM",confirm,getResources().getColor(R.color.yellow)));
                      pieChart.addPieSlice(new PieModel("ACTIVE",active,getResources().getColor(R.color.blue_pie)));
                      pieChart.addPieSlice(new PieModel("RECOVERED",recovered,getResources().getColor(R.color.green_pie)));
                      pieChart.addPieSlice(new PieModel("DEATH",deaths,getResources().getColor(R.color.red_pie)));
                      pieChart.startAnimation();
                  }
              }
            }

            @Override
            public void onFailure(Call<List<CounterData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setText(String last_updated) {
        DateFormat format=new SimpleDateFormat("MMM dd, yyyy");
        long millisecond=Long.parseLong(last_updated);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);

        date.setText("Updated at "+format.format(calendar.getTime()));
    }

    private void init() {
        totalActive=findViewById(R.id.totalActive);
        totalConfirm=findViewById(R.id.totalConfirm);
        todayConfirm=findViewById(R.id.todayConfirm);
        totalRecovered=findViewById(R.id.totalRecovered);
        todayRecovered=findViewById(R.id.todayRecovered);
        totalTest=findViewById(R.id.totalTest);
        totalDeath=findViewById(R.id.totalDeath);
        todayDeath=findViewById(R.id.todayDeath);
        pieChart=findViewById(R.id.piechart);
        date=findViewById(R.id.dateAndTime);
    }

}