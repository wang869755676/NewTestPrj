package com.station.nurse.newtestprj.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.station.nurse.newtestprj.R;
import com.station.nurse.newtestprj.adapter.InfoRecyclerAdapter;
import com.station.nurse.newtestprj.callBack.PumListCallBack;
import com.station.nurse.newtestprj.model.ParamModel;
import com.station.nurse.newtestprj.model.Pum;
import com.station.nurse.newtestprj.utils.Api;
import com.station.nurse.newtestprj.utils.ToastUtils;
import com.station.nurse.newtestprj.view.NiceSpinner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class ParameterFragment extends Fragment {


    @Bind(R.id.param_num)
    NiceSpinner paramNum;
    @Bind(R.id.param_total)
    EditText paramTotal;
    @Bind(R.id.param_speed)
    EditText paramSpeed;
    @Bind(R.id.param_send)
    Button paramSend;

    private List<String> nums;
    private List<Pum> dataList;
    private ParamModel paramModel;
    private List<ParamModel> models;
    private int currentPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parameter, container, false);
        ButterKnife.bind(this, view);
        initListner();
        initData();
        return view;
    }

    private void initListner() {
        paramNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        paramSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList != null) {
                    if (models == null)
                        models = new ArrayList<ParamModel>();
                    paramModel = new ParamModel();
                    paramModel.Slot = dataList.get(currentPosition).getSlot() + "";
                    paramModel.Speed= paramSpeed.getText().toString();
                    paramModel.Total = paramTotal.getText().toString();
                    models.add(paramModel);
                    if (paramModel.Total == null || "".equals(paramModel.Total)) {
                        ToastUtils.showToast(getActivity(), "请输入总量");
                        return;
                    }

                    if (paramModel.Speed == null || "".equals(paramModel.Speed)) {
                        ToastUtils.showToast(getActivity(), "请输入流速");
                        return;
                    }
                    Log.e("===",new Gson().toJson(models)+"--------------------------------");
                    OkHttpUtils
                            .post()
                            .url(Api.paramApi)
                            .addParams("SendPara", new Gson().toJson(paramModel))
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    ToastUtils.showToast(getActivity(), "设置成功");
                                    paramSpeed.setText("");
                                    paramTotal.setText("");
                                    models.clear();
                                }
                            });

                }
            }
        });
    }

    private void initData() {
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
                        if (response != null) {
                            dataList = response;
                            nums = new ArrayList<String>();
                            for (Pum pum : dataList) {
                                nums.add(pum.getSlot() + "号泵");
                            }
                            paramNum.attachDataSource(nums);
                        }
                    }
                });
     /*   nums=new ArrayList<>();
        nums.add("1号泵");
        nums.add("2号泵");
        nums.add("3号泵");
        nums.add("4号泵");
        nums.add("5号泵");
        nums.add("6号泵");
        paramNum.attachDataSource(nums);*/

    }


    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(this);
    }
}
