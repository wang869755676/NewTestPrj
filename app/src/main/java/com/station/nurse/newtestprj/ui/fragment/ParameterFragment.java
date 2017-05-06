package com.station.nurse.newtestprj.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.station.nurse.newtestprj.R;
import com.station.nurse.newtestprj.view.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ParameterFragment extends Fragment {


    @Bind(R.id.param_num)
    NiceSpinner paramNum;
    @Bind(R.id.param_total)
    EditText paramTotal;
    @Bind(R.id.param_speed)
    EditText paramSpeed;

    private List<String> nums;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parameter, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        nums=new ArrayList<>();
        nums.add("1号泵");
        nums.add("2号泵");
        nums.add("3号泵");
        nums.add("4号泵");
        nums.add("5号泵");
        nums.add("6号泵");
        paramNum.attachDataSource(nums);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
