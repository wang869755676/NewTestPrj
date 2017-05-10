package com.station.nurse.newtestprj.ui.activity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
    private String result;
    //private HashMap<String,Pum> pumList;
    int count = 0;
    private Timer timer = new Timer();
    private TextView textView;
    public static final int SHOW_RESPONSE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textView = (TextView) findViewById(R.id.textView);
        initView();
    }

    private void initView() {
        mainTab.setupWithViewPager(mainVp);
        fragmentList= new ArrayList<>();
        fragmentList.add(new InfoFragment());
        fragmentList.add(new ParameterFragment());
        fragmentList.add(new SpeedFragment());
        mainVp.setAdapter(new MainFragmentAdapter(getSupportFragmentManager()));
        new Thread(getThread).start();
        //getSystemService(Conte)
    }

    private class MainFragmentAdapter extends FragmentPagerAdapter{

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
    private Thread getThread = new Thread() {
        public void run() {
            while (true) {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://192.168.15.100/output.php");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
                    connection.setRequestProperty("Cookie", "AppName=" + URLEncoder.encode("", "UTF-8"));
                    connection.setRequestProperty("MyProperty", "this is me!");
                    if (connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        try {
                            JSONArray jsonArray = new JSONArray(convertStreamToString(is));
                            int num = jsonArray.length();
                            if (num > 0) {

                                result = "";
                            }
                            for (int i = 0; i < num; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                result = result + "泵号：" + String.valueOf(jsonObject.getInt("Slot")) + "\r\n序列号：" + jsonObject.getString("Sn") + "\r\n实时流速：" + jsonObject.getString("RealSpeed") + "\r\n流速：" + jsonObject.getString("Speed") + "\r\n药名：" + jsonObject.getString("Drug") + "\r\n剩余量：" + jsonObject.getString("Remain") + "\r\n剩余时间：" + jsonObject.getString("LastTime") + "\r\n总量：" + jsonObject.getString("Total") + "\r\n累积量：" + jsonObject.getString("Sum") + "\r\n";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Message msg = Message.obtain();
                        msg.what = 0;
                        getHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }

        ;
    };
    private Handler getHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0 && result != null) {
                textView.setText(result);
            }
        }

        ;
    };

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }



}

