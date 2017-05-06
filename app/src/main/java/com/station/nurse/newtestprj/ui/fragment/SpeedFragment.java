package com.station.nurse.newtestprj.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.station.nurse.newtestprj.R;
import com.station.nurse.newtestprj.formatter.DayAxisValueFormatter;
import com.station.nurse.newtestprj.utils.FormateDate;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tech.linjiang.suitlines.SuitLines;
import tech.linjiang.suitlines.Unit;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpeedFragment extends Fragment {


    /* @Bind(R.id.speed_one)
     LinearLayout speedOne;*/
    @Bind(R.id.speed_line)
    LineChart speedLine;
    @Bind(R.id.suitlines)
    SuitLines suitlines;
    SuitLines.LineBuilder builder;

    private int[] color = {Color.RED, 0xFF5E2612, 0xFFE3CF57, 0xFFED9121, 0xFFD2691E, 0xFF734A12};
    private ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
    private LineData data;
    private List<List<Entry>> values;
    private List<Entry> value;
    private List<String> days = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    LineData data = new LineData(dataSets);
                    speedLine.setData(data);
                    speedLine.invalidate();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_speed, container, false);
        ButterKnife.bind(this, view);
        initChart();
        initData();
        return view;
    }

    /**
     * 初始化折线图的一些属性
     */
    private void initChart() {

    /*    speedLine.getDescription().setEnabled(false);
        speedLine.setDrawGridBackground(true);

        XAxis xAxis = speedLine.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setLabelCount(5, true);
        xAxis.setValueFormatter(new DayAxisValueFormatter(days));

        YAxis leftAxis = speedLine.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = speedLine.getAxisRight();
        rightAxis.setEnabled(false);


        speedLine.setScrollContainer(true);
        speedLine.setTouchEnabled(true);
        speedLine.setPinchZoom(false);
        // speedLine.zoom(1.2f, 0f, 15f, 0f);*/

        builder = new SuitLines.LineBuilder();
    }

    private void initData() {
      /*  Log.e("===", System.currentTimeMillis() + "");
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        for (int z = 0; z < 1; z++) {

            ArrayList<Entry> values = new ArrayList<Entry>();

            for (int i = 0; i < 20; i++) {
                days.add(FormateDate.formateDate("MM-dd HH:mm", System.currentTimeMillis() + 20));
                double val = (Math.random() * 20) + 3;
                values.add(new Entry(i, (float) val));
            }

            LineDataSet d = new LineDataSet(values, "DataSet " + (z + 1));
            d.setLineWidth(2.5f);
            d.setDrawCircles(false);

            int color = Colors[z];
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }


        LineData data = new LineData(dataSets);
        speedLine.setData(data);
        speedLine.invalidate();*/

       /*  values = new ArrayList<>();
        values.add(new ArrayList<Entry>());
        values.add(new ArrayList<Entry>());
        values.add(new ArrayList<Entry>());
        values.add(new ArrayList<Entry>());
        values.add(new ArrayList<Entry>());
        values.add(new ArrayList<Entry>());

        dataSets = new ArrayList<ILineDataSet>();
        for (int z = 0; z < 6; z++) {
            value=values.get(z);
            value.add(new Entry(System.currentTimeMillis(), 200));
            LineDataSet d = new LineDataSet(value, " " + (z + 1));
            d.setLineWidth(2.5f);
            d.setDrawCircles(false);
            d.setColor(Colors[z]);
            d.setCircleColor(Colors[z]);
            dataSets.add(d);
        }

       Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dataSets = new ArrayList<ILineDataSet>();
                for (int z = 0; z < 6; z++) {
                    value=values.get(z);
                    value.add(new Entry(System.currentTimeMillis(), 200));
                    LineDataSet d = new LineDataSet(value, " " + (z + 1));
                    d.setLineWidth(2.5f);
                    d.setDrawCircles(false);
                    d.setColor(Colors[z]);
                    d.setCircleColor(Colors[z]);
                    dataSets.add(d);
                }
                handler.sendEmptyMessage(0);
            }
        }, 0, 5000);*/




      /*  // make the first DataSet dashed
        ((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
        ((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
        ((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);*/

        init(6);
    }

    public void init(int count) {
        if (count <= 0) {
            count = 0;
        }
        if (count == 1) {
            List<Unit> lines = new ArrayList<>();
            for (int i = 0; i < 14; i++) {
                lines.add(new Unit(new SecureRandom().nextInt(48), i + "d"));
            }
            suitlines.feedWithAnim(lines);
            return;
        }

        SuitLines.LineBuilder builder = new SuitLines.LineBuilder();
        for (int j = 0; j < count; j++) {
            List<Unit> lines = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                lines.add(new Unit(new SecureRandom().nextInt(128), FormateDate.formateDate("MM-dd HH:ss",System.currentTimeMillis())));
            }
            builder.add(lines,color[j]);
        }
        builder.build(suitlines, true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
