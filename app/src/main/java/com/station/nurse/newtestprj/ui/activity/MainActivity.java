package com.station.nurse.newtestprj.ui.activity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.station.nurse.newtestprj.R;
import com.station.nurse.newtestprj.ui.fragment.InfoFragment;
import com.station.nurse.newtestprj.ui.fragment.ParameterFragment;
import com.station.nurse.newtestprj.ui.fragment.SpeedFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Timer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_vp)
    ViewPager mainVp;
    @Bind(R.id.main_tab)
    TabLayout mainTab;

    private String title[]={"输液信息","参数设置","流速曲线"};
    private ArrayList<Fragment> fragmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mainTab.setupWithViewPager(mainVp);
        fragmentList= new ArrayList<>();
        fragmentList.add(new InfoFragment());
        fragmentList.add(new ParameterFragment());
        fragmentList.add(new SpeedFragment());
        mainVp.setAdapter(new MainFragmentAdapter(getSupportFragmentManager()));
       // mainVp.setOffscreenPageLimit(3);

    }

    private class MainFragmentAdapter extends FragmentStatePagerAdapter{

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList!=null? fragmentList.size():0;
        }
    }




}

