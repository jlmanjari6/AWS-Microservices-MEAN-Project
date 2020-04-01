package com.example.tourismapp.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.transition.Scene;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tourismapp.Helpers.GlobalStorage;
import com.example.tourismapp.Interface.RetrofitApiInterface;
import com.example.tourismapp.Models.BookingData;
import com.example.tourismapp.Models.NoOfBooking;
import com.example.tourismapp.Models.Topplaces;
import com.example.tourismapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnalyticsFragment extends Fragment {
    @Nullable
    ArrayList<String> lName = new ArrayList<String>();
    ArrayList<Integer> lCount = new ArrayList<Integer>();
    ArrayList<String> ldestination=new ArrayList<String>();
    ArrayList<Float> lCount1 = new ArrayList<Float>();
    PieChart pieChart;
    BarChart barchart;
    BarDataSet barDataSet1;
    ArrayList<Float> cnt=new ArrayList<>();
    ArrayList<String> str=new ArrayList<>();
    ArrayList<PieEntry> entries = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        pieChart = (PieChart)view.findViewById(R.id.pieChart);
        getTopPlaces();
        barchart=view.findViewById(R.id.barchart);
        getBookingDetails();
        return view;
    }

    public void getTopPlaces() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fv2z97pt9c.execute-api.us-east-1.amazonaws.com/dev/analytics/") // replace your local ip address here (but not localhost/127.0.0.1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiInterface service = retrofit.create(RetrofitApiInterface.class);
        Call<List<Topplaces>> call = service.topPlaces();
        call.enqueue(new Callback<List<Topplaces>>() {

            @Override
            public void onResponse(Call<List<Topplaces>> call, Response<List<Topplaces>> response) {
                try {
                    int i=0;
                    List<Topplaces> listtopplaces = response.body();
                    for (Topplaces c : listtopplaces) {
                        lName.add(c.getName());
                        lCount.add(Integer.parseInt(c.getCount()));
                        entries.add(new PieEntry(lCount.get(i),lName.get(i)));
                        i+=1;
                    }
                    PieDataSet pieDataSet = new PieDataSet(entries,"");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    PieData pieData = new PieData(pieDataSet);
                    pieChart.setData(pieData);
                    pieChart.getCenterCircleBox();
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setDrawSliceText(false);
                    pieChart.setDrawHoleEnabled(false);
                    pieChart.setCenterTextRadiusPercent(0);
                    Legend legend = pieChart.getLegend();
                    pieData.setValueTextSize(10);
                    pieData.setValueTextColor(707070);
                    legend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
                    pieChart.invalidate();
                }
                catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<List<Topplaces>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getBookingDetails() {
        BarData barData=new BarData();
        //String str=[];
        ArrayList<BarEntry> datavals=new ArrayList<BarEntry>();
        ArrayList<String> xAxisLabel = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fv2z97pt9c.execute-api.us-east-1.amazonaws.com/dev/booking/")// replace your local ip address here (but not localhost/127.0.0.1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApiInterface service = retrofit.create(RetrofitApiInterface.class);
        Call<List<NoOfBooking>> call = service.bookingDetails();
        call.enqueue(new Callback<List<NoOfBooking>>() {

            @Override
            public void onResponse(Call<List<NoOfBooking>> call, Response<List<NoOfBooking>> response) {
                try {
                    int i=0;
                    List<NoOfBooking> listBookings = response.body();
                    for (NoOfBooking c : listBookings) {
                        ldestination.add(c.getDestination());
                        lCount1.add(Float.parseFloat(c.getCount()));
                        xAxisLabel.add(ldestination.get(i));
                        datavals.add(new BarEntry(i,lCount1.get(i) ));
                        i+=1;
                    }
                    barDataSet1=new BarDataSet(datavals,"Tickets booked");
                    barData.addDataSet(barDataSet1);
                    barchart.setData(barData);
                    barchart.getDescription().setEnabled(false);
                    YAxis left = barchart.getAxisLeft();
                    left.setAxisMaxValue(5);//dataset.getYMax()+2);
                    left.setAxisMinValue(0);
                    left.setLabelCount(5);
                    barchart.getAxisRight().setEnabled(false);
                    XAxis bottomAxis = barchart.getXAxis();
                    barData.setDrawValues(false);
                    bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    bottomAxis.setAxisMinValue(0);

                    bottomAxis.setDrawGridLines(false);
                    barchart.setDrawValueAboveBar(false);
                    bottomAxis.setAxisMinimum(barData.getXMin()-.5f);
                    Legend legend = barchart.getLegend();
                    barchart.setBackgroundColor(0);
                    legend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
                    legend.setTextSize(10);
                    bottomAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return xAxisLabel.get((int) value);
                        }
                });
                    barchart.invalidate();
                }
                catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<NoOfBooking>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}