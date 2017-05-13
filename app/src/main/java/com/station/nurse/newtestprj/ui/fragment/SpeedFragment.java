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
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;

import com.station.nurse.newtestprj.R;
import com.station.nurse.newtestprj.callBack.PumListCallBack;
import com.station.nurse.newtestprj.model.Pum;
import com.station.nurse.newtestprj.utils.Api;
import com.station.nurse.newtestprj.utils.FormateDate;
import com.station.nurse.newtestprj.utils.ViewHolderUtil;
import com.station.nurse.newtestprj.view.NoTouchView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import tech.linjiang.suitlines.SuitLines;
import tech.linjiang.suitlines.Unit;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpeedFragment extends Fragment {


    @Bind(R.id.suitlines)
    SuitLines suitlines;
    SuitLines.LineBuilder builder;
    List<Unit> lines;
    @Bind(R.id.speed_gv)
    GridView speedGv;
    @Bind(R.id.speed_notouch)
    NoTouchView noTouchView;
    private int[] color = {Color.RED, 0xFF5E2612, 0xFFE3CF57, 0xFFED9121, 0xFFD2691E, 0xFF734A12};
    private List<Pum> dataList;
    private boolean[] removeNum;
    private NumAdapter adapter;

    private List<List<Unit>> datas;
    private Timer timer;
    private List<Unit> temps = new ArrayList<>();
    private int position;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    getData();
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
        initView();
        return view;
    }

    private void getData() {
        OkHttpUtils
                .get()
                .url(Api.homeApi)
                .build()
                .execute(new PumListCallBack() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(List<Pum> response, int id) {
                        Log.e("===", response.size() + "------------");
                        if (response != null) {
                            noZero(response);
                            if (dataList == null) {
                                dataList = response;
                                initDatas();
                                removeNum = new boolean[dataList.size()];
                                adapter = new NumAdapter();
                                speedGv.setAdapter(new NumAdapter());
                            } else {
                                dataList.clear();
                                dataList.addAll(response);
                                adapter.notifyDataSetChanged();
                            }
                            init(false);

                        }
                    }
                });
       /* if (dataList == null) {
            dataList = new ArrayList<>();
            dataList.add(new Pum(0, "12.0ml/h"));
            dataList.add(new Pum(1, "8.0ml/h"));
            dataList.add(new Pum(3, "3.0ml/h"));
            noZero(dataList);
            initDatas();
            removeNum = new boolean[dataList.size()];
            adapter = new NumAdapter();
            speedGv.setAdapter(new NumAdapter());
        } else {
            dataList.clear();
            dataList.add(new Pum(0, "10.0ml/h"));
            dataList.add(new Pum(1, "10.0ml/h"));
            dataList.add(new Pum(3, "3.0ml/h"));
            noZero(dataList);
            adapter.notifyDataSetChanged();
        }
        init(false);*/
    }


    private void initView() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0, 10000);

    }

    /**
     * 初始化datas集合
     */
    private void initDatas() {
        datas = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            datas.add(new ArrayList<Unit>());
        }
    }


    public void init(boolean f) {
        if (dataList != null) {
            if (builder != null)
                builder = null;
            builder = new SuitLines.LineBuilder();

            for (int j = 0; j < dataList.size(); j++) {
                if (datas.get(j).size() > 120) {
                    temps.clear();
                    temps.addAll(datas.get(j));
                    datas.get(j).clear();
                    datas.get(j).addAll(temps.subList(60, temps.size()));
                }
                if (!f) {
                    position = datas.get(j).size();
                    if (position % 6 == 0) {
                        datas.get(j).add(new Unit(getSpeedNum(dataList.get(j).getRealSpeed()), FormateDate.formatDate("MM-dd HH:mm", new Date())));
                    } else {
                        datas.get(j).add(new Unit(getSpeedNum(dataList.get(j).getRealSpeed()), ""));
                    }

                }
                if(datas.get(j).size()>2){
                    noTouchView.setVisibility(View.GONE);
                }
                if (removeNum[j]) {
                    continue;
                }
                lines = new ArrayList<>();
                for (int i = 0; i < datas.get(j).size(); i++) {
                    lines.add(datas.get(j).get(i));
                }

                builder.add(lines, color[j]);
            }

            builder.build(suitlines, false);
        }


    }

    public void noZero(List<Pum> pumList) {
        Iterator iterator = pumList.iterator();
        while (iterator.hasNext()) {
            Pum pum = (Pum) iterator.next();
            if (pum.getSlot() == 0) {
                iterator.remove();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void hideView(int position, boolean isChecked) {
        if (!isChecked) {
            removeNum[position] = true;
        } else {
            removeNum[position] = false;
        }
        init(true);
    }

    private float getSpeedNum(String speed) {
        float speedNum = 0;
        speed = speed.replace("ml/h", "");
        try {
            speedNum = (int) Float.parseFloat(speed);
        } catch (Exception e) {
            Log.e("===", e.getMessage());
            speedNum = 0;
        }
        return speedNum;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(this);
    }

    private class NumAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList != null ? dataList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_num, null);
            CheckBox box = ViewHolderUtil.ViewHolder.get(view, R.id.item_check);
            box.setText(dataList.get(position).getSlot() + "号泵");
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    hideView(position, isChecked);
                }
            });

            return view;
        }
    }
}
