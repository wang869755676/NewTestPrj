package com.station.nurse.newtestprj.formatter;


import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.station.nurse.newtestprj.utils.FormateDate;

import java.util.ArrayList;
import java.util.List;

public class DayAxisValueFormatter implements IAxisValueFormatter {
    private List<String> days;

    public DayAxisValueFormatter(List<String> days) {
        if(days!=null){
            this.days = days;
        }else {
            this.days=new ArrayList<>();
        }

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        return  days.get((int) value);
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
