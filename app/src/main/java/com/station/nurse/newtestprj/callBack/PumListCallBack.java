package com.station.nurse.newtestprj.callBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.station.nurse.newtestprj.model.Pum;
import com.zhy.http.okhttp.callback.Callback;

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
        if(response.isSuccessful()){
            Gson gson=new Gson();
            return  gson.fromJson(response.body().toString(),new TypeToken<Pum>(){}.getType());
        }

        return null;
    }


}
