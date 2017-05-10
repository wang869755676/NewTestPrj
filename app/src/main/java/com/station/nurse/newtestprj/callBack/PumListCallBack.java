package com.station.nurse.newtestprj.callBack;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.station.nurse.newtestprj.model.Pum;
import com.station.nurse.newtestprj.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;

import java.util.List;
import okhttp3.Response;

/**
 * 创建人：
 * 创建时间： 2017/5/8
 * 内容描叙：
 * 修改人：
 * 修改时间：
 * 修改描叙：
 */

public abstract class PumListCallBack extends Callback<List<Pum>>
{
    @Override
    public List<Pum> parseNetworkResponse(Response response, int id) throws Exception {
        Log.e("====","========"+response.body()+"====="+response.code());
        if(response.isSuccessful()){
            Gson gson=new Gson();

            if(response.body().toString()!=null &&!"".equals(response.body().toString()) ){
                JSONArray jsonArray=new JSONArray(response.body().string());
                return  gson.fromJson(jsonArray.toString(),new TypeToken<List<Pum>>(){}.getType());
            }
            return null;

        }

        return null;
    }


}
