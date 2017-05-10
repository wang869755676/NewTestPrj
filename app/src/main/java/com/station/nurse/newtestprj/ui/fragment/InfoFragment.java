package com.station.nurse.newtestprj.ui.fragment;


import android.os.Bundle;
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

import java.util.List;

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
                .execute(new PumListCallBack(){

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("====","========"+e.getMessage());
                        ToastUtils.showToast(getActivity(),"网络请求的错误信息"+e.getMessage());

                    }

                    @Override
                    public void onResponse(List<Pum> response, int id) {
                        ToastUtils.showToast(getActivity(),"数据集合"+response);
                        Log.e("====","========"+response);
                        if(response!=null){
                            dataList=response;
                            infoRv.setAdapter(new InfoRecyclerAdapter(dataList, getActivity()));
                        }
                    }
                });
    /*    dataList = new ArrayList<>();
        dataList.add(new Pum());
        dataList.add(new Pum());
        dataList.add(new Pum());
        dataList.add(new Pum());
        dataList.add(new Pum());
        dataList.add(new Pum());*/


    }

    private void initView() {
        infoSipRefresh.setRefreshing(false);
        infoSipRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        infoRv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
