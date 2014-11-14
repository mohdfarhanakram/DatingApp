package com.digitalforce.datingapp.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.model.Insight;
import com.digitalforce.datingapp.widgets.ProgressWheel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;

import java.util.ArrayList;

/**
 * Created by FARHAN on 11/10/2014.
 */
public class InsightActivity extends BaseActivity{

    //private BarChart mBarChart;

    private ProgressWheel mProgressWheel18_19;
    private ProgressWheel mProgressWheel20_24;
    private ProgressWheel mProgressWheel25_29;

    private ProgressWheel mProgressWheelReply;
    private ProgressWheel mProgressWheelHeight;
    private ProgressWheel mProgressWheelWeight;
    private Typeface mTf;

    private Insight mInsight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_insight_layout);

        mInsight = getIntent().getParcelableExtra(AppConstants.USER_INSIGHT_INFO);

        String userName = getIntent().getStringExtra(AppConstants.INSIGHT_USER_NAME);
        ((TextView) findViewById(R.id.txt_screen_title)).setText(userName+" Insight");
        findViewById(R.id.img_action_menu).setVisibility(View.VISIBLE);

        ((TextView) findViewById(R.id.insight_age_des)).setText("Age distribution of users that "+userName+" is interested in.");


        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mProgressWheel18_19 = (ProgressWheel)findViewById(R.id.progressBar1819);
        mProgressWheel20_24 = (ProgressWheel)findViewById(R.id.progressBar2024);
        mProgressWheel25_29 = (ProgressWheel)findViewById(R.id.progressBar2529);
        mProgressWheelReply = (ProgressWheel)findViewById(R.id.progressBarReply);
        mProgressWheelHeight = (ProgressWheel)findViewById(R.id.progressBarHeight);
        mProgressWheelWeight = (ProgressWheel)findViewById(R.id.progressBarWeight);

        if(mInsight!=null){

            mProgressWheel18_19.setProgress(360);  // give value in angle
            mProgressWheel18_19.setText(mInsight.getAgebetween18and19());

            mProgressWheel20_24.setProgress(360);  // give value in angle
            mProgressWheel20_24.setText(mInsight.getAgebetween20and24());

            mProgressWheel25_29.setProgress(360);  // give value in angle
            mProgressWheel25_29.setText(mInsight.getAgebetween25and29());

            mProgressWheelReply.setProgress(360);  // give value in angle
            mProgressWheelReply.setText(mInsight.getReplyRate());

            mProgressWheelHeight.setProgress(0);  // give value in angle
            mProgressWheelHeight.setText(mInsight.getHeight());

            mProgressWheelWeight.setProgress(0);  // give value in angle
            mProgressWheelWeight.setText(mInsight.getWeight());

        }else{

            mProgressWheel18_19.setProgress(0);  // give value in angle
            mProgressWheel18_19.setText("0%");

            mProgressWheel20_24.setProgress(0);  // give value in angle
            mProgressWheel20_24.setText("0%");

            mProgressWheel25_29.setProgress(0);  // give value in angle
            mProgressWheel25_29.setText("0%");

            mProgressWheelReply.setProgress(0);  // give value in angle
            mProgressWheelReply.setText("0%");

            mProgressWheelHeight.setProgress(0);  // give value in angle
            mProgressWheelHeight.setText("0'00");

            mProgressWheelWeight.setProgress(0);  // give value in angle
            mProgressWheelWeight.setText("far");

        }




        ((TextView) findViewById(R.id.more_insight_age_des)).setText("Reply rate is based on "+userName+"'s use of Messages. Other metrics is based on Match Finder & Favorites.");


       /* mPieChartReply = (PieChart)findViewById(R.id.pie_reply_rate);
        //mPieChartHeight = (PieChart)findViewById(R.id.pie_avg_height);
        //mPieChartWeight = (PieChart)findViewById(R.id.pie_avg_weight);

        //drawPieChart();

        // change the color of the center-hole
        mPieChartReply.setHoleColor(0);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mPieChartReply.setValueTypeface(tf);
        mPieChartReply.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));

        mPieChartReply.setHoleRadius(60f);

        mPieChartReply.setDescription("");

        mPieChartReply.setDrawYValues(true);
        mPieChartReply.setDrawCenterText(true);

        mPieChartReply.setDrawHoleEnabled(true);

        mPieChartReply.setRotationAngle(0);

        // draws the corresponding description value into the slice
        mPieChartReply.setDrawXValues(true);

        // enable rotation of the chart by touch
        mPieChartReply.setRotationEnabled(true);

        // display percentage values
        mPieChartReply.setUsePercentValues(true);
        // mChart.setUnit(" â‚¬");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //mPieChartReply.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

        mPieChartReply.setCenterText("145lbs");

        setData(1, 100);

        mPieChartReply.animateXY(1500, 1500);
        // mChart.spin(2000, 0, 360);

        Legend l = mPieChartReply.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);*/



    }

    private int getPieChartDegree(String percentage){
        return getDetDegree(getPercentage(percentage));
    }


    private int getDetDegree(int percentage){
        return (percentage*18)/5;
    }

    private int getPercentage(String percentage){
        int val = 0;
        try{
            int index = percentage.indexOf("%");
            if(index>0){
                String s  = percentage.substring(0,index);
                return Integer.parseInt(s);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return val;

    }

    /*private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

       *//* for (int i = 0; i < count + 1; i++)
            xVals.add("farhan");*//*

        PieDataSet set1 = new PieDataSet(yVals1, "Election Results");
        set1.setSliceSpace(3f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        mPieChartReply.setData(data);

        // undo all highlights
        mPieChartReply.highlightValues(null);

        mPieChartReply.invalidate();
    }
*/



    /*private void drawBarChart(){
        // apply styling
        mBarChart.setValueTypeface(mTf);
        mBarChart.setDescription("");
        mBarChart.setDrawVerticalGrid(false);
        mBarChart.setDrawGridBackground(false);
        mBarChart.setValueTextColor(Color.WHITE);
        mBarChart.setPinchZoom(false);

        XLabels xl = mBarChart.getXLabels();
        xl.setCenterXLabelText(true);
        xl.setPosition(XLabels.XLabelPosition.BOTTOM);
        xl.setTextColor(Color.WHITE);
        xl.setTypeface(mTf);


        YLabels yl = mBarChart.getYLabels();
        yl.setTypeface(mTf);
        yl.setLabelCount(5);
        yl.setTextColor(Color.WHITE);

        BarData c = generateData();
        // set data
        mBarChart.setData(c);

        // do not forget to refresh the chart
//            holder.chart.invalidate();
        mBarChart.animateY(700);

    }*/

    /*private void drawPieChart(){
        mPieChartReply = setPieChartStyle(mPieChartReply);
        mPieChartHeight = setPieChartStyle(mPieChartHeight);
        mPieChartWeight = setPieChartStyle(mPieChartWeight);

        mPieChartReply.setCenterText("55");
        setData(mPieChartReply,2, 55);

        mPieChartHeight.setCenterText("5'11");
        setData(mPieChartHeight,2, 55);

        mPieChartWeight.setCenterText("154lbs");
        setData(mPieChartWeight,2, 55);

    }*/


    private void setData(PieChart pieChart,int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add("farhan");

        PieDataSet set1 = new PieDataSet(yVals1, "");
        set1.setSliceSpace(3f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        set1.setColors(colors);

        PieData data = new PieData(xVals);
        pieChart.setData(data);

        pieChart.animateXY(1500, 1500);
        // mChart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }



    private  PieChart setPieChartStyle(PieChart pieChart){
        pieChart.setHoleColor(0);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        pieChart.setValueTypeface(tf);
        pieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));

        //pieChart.setHoleRadius(60f);

        pieChart.setDescription("");

        pieChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(0);

        // draws the corresponding description value into the slice
        pieChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(false);

        // display percentage values
        pieChart.setUsePercentValues(true);
        // mChart.setUnit(" â‚¬");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //mChart.setOnChartValueSelectedListener(this);
        pieChart.setTouchEnabled(false);
        pieChart.setCenterText("MPAndroidChart\nLibrary");

        return pieChart;
    }


    /**
     * generates ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateData() {


        ArrayList<BarEntry> entries = getBarEntry();

        BarDataSet d = new BarDataSet(entries, "");
        d.setBarSpacePercent(20f);
        d.setColors(BAR_COLORS);
        d.setBarShadowColor(0);


        ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
        sets.add(d);

        BarData cd = new BarData(getAgesDiff(), sets);
        return cd;
    }

    private ArrayList<String> getAgesDiff() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("18-19");
        m.add("20-24");
        m.add("25-29");
        m.add("30-34");
        m.add("35-39");
        m.add("40-44");
        m.add("45>");

        return m;
    }

    private ArrayList<BarEntry> getBarEntry(){
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

         entries.add(new BarEntry(25, 0));
        entries.add(new BarEntry(50, 1));
        entries.add(new BarEntry(22, 2));
        entries.add(new BarEntry(75, 3));
        entries.add(new BarEntry(53, 4));
        entries.add(new BarEntry(90, 5));
        entries.add(new BarEntry(30, 6));
        return entries;
    }



     private final int[] BAR_COLORS = {
             Color.rgb(253, 172, 55), Color.rgb(0,186,255), Color.rgb(236, 131, 117),
             Color.rgb(31, 179, 107)
    };

    @Override
    public void onEvent(int eventId, Object eventData) {

    }
}
