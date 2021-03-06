package com.station.nurse.newtestprj.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.station.nurse.newtestprj.R;
import com.station.nurse.newtestprj.adapter.InfoRecyclerAdapter;
import com.station.nurse.newtestprj.callBack.PumListCallBack;
import com.station.nurse.newtestprj.model.Pum;
import com.station.nurse.newtestprj.utils.Api;
import com.station.nurse.newtestprj.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {


    @Bind(R.id.infoRv)
    RecyclerView infoRv;
    @Bind(R.id.infoSipRefresh)
    SwipeRefreshLayout infoSipRefresh;

    private List<Pum> dataList;
    private InfoRecyclerAdapter adapter;
    private Timer timer;
/*    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                   infoSipRefresh.setRefreshing(true);
                    break;
            }
        }
    };*/
    public InfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);
        initView();
        getData();
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
                        Collections.sort(response);
                        infoSipRefresh.setRefreshing(false);
                        noZero(response);
                        if (response != null) {
                            if(adapter==null){
                                dataList = response;
                                adapter=new InfoRecyclerAdapter(dataList, getActivity());
                                infoRv.setAdapter(adapter);
                            }else{
                                dataList.clear();
                                dataList.addAll(response);
                                adapter.notifyDataSetChanged();
                            }



                        }
                    }
                });


          /*  if(adapter==null){
                dataList = new ArrayList<>();
                dataList.add(new Pum(0));
                dataList.add(new Pum(1));
                dataList.add(new Pum(2));
                dataList.add(new Pum(3));
                dataList.add(new Pum(4));
                adapter=new InfoRecyclerAdapter(dataList, getActivity());
                infoRv.setAdapter(adapter);
            }else{
                dataList.clear();

                dataList.add(new Pum(1));
                dataList.add(new Pum(0));
                dataList.add(new Pum(2));
                dataList.add(new Pum(4));
                dataList.add(new Pum(3));
                adapter.notifyDataSetChanged();
            }*/





    }

    private void initView() {
        infoSipRefresh.setEnabled(false);
        infoSipRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        infoRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               getData();
            }
        }, 0, 1000);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
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

}
