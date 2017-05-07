package com.station.nurse.newtestprj.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;

import com.github.mikephil.charting.charts.LineChart;
import com.station.nurse.newtestprj.R;
import com.station.nurse.newtestprj.model.SpeedModel;
import com.station.nurse.newtestprj.utils.FormateDate;
import com.station.nurse.newtestprj.utils.ViewHolderUtil;

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


    private int[] color = {Color.RED, 0xFF5E2612, 0xFFE3CF57, 0xFFED9121, 0xFFD2691E, 0xFF734A12};
    @Bind(R.id.speed_line)
    LineChart speedLine;
    @Bind(R.id.suitlines)
    SuitLines suitlines;
    SuitLines.LineBuilder builder;
    List<Unit> lines;
    @Bind(R.id.speed_gv)
    GridView speedGv;

    private List<SpeedModel> speedModels;
    private List<SpeedModel> datas;
    private int currentPosition=-1;
    private boolean isRemove = false;

   /* private ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
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
    };*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_speed, container, false);
        ButterKnife.bind(this, view);
        initView();

        return view;
    }

    private void initView() {
        builder = new SuitLines.LineBuilder();
        lines = new ArrayList<>();

        speedModels = new ArrayList<>();
        speedModels.add(new SpeedModel());
       // speedModels.add(new SpeedModel());
        datas = speedModels;
        speedGv.setAdapter(new NumAdapter());
        init();
    }


    public void init() {
        if (builder != null)
            builder = null;
        builder = new SuitLines.LineBuilder();

        for (int j = 0; j < speedModels.size(); j++) {
            if (currentPosition == j && isRemove) {
                continue;
            }
            lines=new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                lines.add(new Unit(new SecureRandom().nextInt(128), FormateDate.formateDate("MM-dd HH:ss", System.currentTimeMillis())));
            }
            builder.add(lines, color[j]);
        }

        builder.build(suitlines, true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class NumAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return speedModels != null ? speedModels.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return speedModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_num, null);
            CheckBox box = ViewHolderUtil.ViewHolder.get(view, R.id.item_check);
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    hideView(position, isChecked);
                }
            });

            return view;
        }
    }

    private void hideView(int position, boolean isChecked) {
        currentPosition = position;
        if (!isChecked) {
            isRemove = true;
        } else {
            isRemove = false;
        }
        init();
    }
}
