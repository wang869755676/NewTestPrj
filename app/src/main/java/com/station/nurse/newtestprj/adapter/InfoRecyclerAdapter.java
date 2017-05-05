package com.station.nurse.newtestprj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.station.nurse.newtestprj.R;
import com.station.nurse.newtestprj.model.Pum;

import java.util.List;


public class InfoRecyclerAdapter extends RecyclerView.Adapter<InfoRecyclerAdapter.InfoViewHolder> {

    private List<Pum> dataList;
    private Context context;

    public InfoRecyclerAdapter(List<Pum> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_info,null);

        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        holder.bindData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

   public class InfoViewHolder extends RecyclerView.ViewHolder {

        private TextView itemNum;
        private TextView itemSerNum;
        private TextView itemRealSpeed;
        private TextView itemSpeed;
        private TextView itemSurplus;
        private TextView itemTotal;
        private TextView itemTime;
        private TextView itemName;

        public InfoViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.item_info_name);
            itemTime = (TextView) itemView.findViewById(R.id.item_info_time);
            itemTotal = (TextView) itemView.findViewById(R.id.item_info_total);
            itemSurplus = (TextView) itemView.findViewById(R.id.item_info_surplus);
            itemSpeed = (TextView) itemView.findViewById(R.id.item_info_speed);
            itemRealSpeed = (TextView) itemView.findViewById(R.id.item_info_realSpeed);
            itemSerNum = (TextView) itemView.findViewById(R.id.item_info_serNum);
            itemNum = (TextView) itemView.findViewById(R.id.item_info_sn);
        }

        public void bindData(Pum pum) {
            itemNum.setText(pum.getSn() + "号泵");
            itemSerNum.setText("设备序列号:                               " + pum.getSlot());
            itemRealSpeed.setText("实时流速: " + pum.getRealSpeed());
            itemSpeed.setText("流速: " + pum.getSpeed());
            itemName.setText("药名: " + pum.getDrug());
            itemSurplus.setText("剩余量: " + pum.getRemain());
            itemTotal.setText("总量: " + pum.getSum());
            itemTime.setText("剩余时间: " + pum.getLastTime());
        }
    }
}
