package com.station.nurse.newtestprj.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.station.nurse.newtestprj.R;
import com.station.nurse.newtestprj.model.Pum;

import java.util.List;


public class InfoRecyclerAdapter extends RecyclerView.Adapter<InfoRecyclerAdapter.InfoViewHolder> {

    private List<Pum> dataList;
    private Context context;
    private Animation animation;

    public InfoRecyclerAdapter(List<Pum> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        animation= AnimationUtils.loadAnimation(context,R.anim.twinkle);
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_info, parent,false);

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

        private TextView itemType;
        private TextView itemModel;
        private TextView itemState;
        private TextView itemRate;
        private TextView itemAlarm;
        private TextView itemAlarmTime;

        private View itemTwinkle;
        private TextView itemStat;

        // （运行状态的泵用绿色闪动，停止的状态用绿色不闪动，低级告警橘黄闪动，高级告警红色闪动）
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

            itemType = (TextView) itemView.findViewById(R.id.item_info_type);
            itemModel = (TextView) itemView.findViewById(R.id.item_info_model);
            itemState = (TextView) itemView.findViewById(R.id.item_info_statue);
            itemRate = (TextView) itemView.findViewById(R.id.item_info_rate);
            itemAlarm = (TextView) itemView.findViewById(R.id.item_info_alarm);
            itemAlarmTime = (TextView) itemView.findViewById(R.id.item_info_alarmTime);
            itemTwinkle = itemView.findViewById(R.id.view_Twinkle);
            itemStat = (TextView) itemView.findViewById(R.id.item_info_state);
        }

        public void bindData(Pum pum) {
            itemNum.setText(pum.getSlot() + "号泵");
            itemSerNum.setText("设备序列号:" + pum.getSn());
            itemRealSpeed.setText("实时流速: " + pum.getRealSpeed());
            itemSpeed.setText("流速: " + pum.getSpeed());
            itemName.setText("药名: " + pum.getDrug());
            itemSurplus.setText("剩余量: " + pum.getRemain());
            itemTotal.setText("总量: " + pum.getSum());
            itemTime.setText("剩余时间: " + pum.getLastTime());
            itemModel.setText("型号: " + pum.getModel());
            itemState.setText("连接状态: " + pum.getLinkStatus());
            itemRate.setText("输液进度: " + pum.getRate());
            itemAlarm.setText("告警事件: " + pum.getAlarm());
            itemAlarmTime.setText("告警时间: " + pum.getAlarmTime());
            itemType.setText("设备类型:" + pum.getType() + "  ");
            switch (pum.getStatus()){
                case 0:
                    itemTwinkle.clearAnimation();
                    itemTwinkle.setBackgroundColor(Color.GRAY);
                    itemStat.setText("断开连接");
                    break;
                case 1:
                    itemTwinkle.clearAnimation();
                    itemTwinkle.setBackgroundColor(Color.GREEN);
                    itemStat.setText("停止");
                    break;
                case 2:
                    itemTwinkle.clearAnimation();
                    itemTwinkle.setAnimation(animation);
                    itemTwinkle.setBackgroundColor(Color.GREEN);
                    itemStat.setText("运行");
                    break;

                case 3:
                    itemTwinkle.clearAnimation();
                    itemTwinkle.setAnimation(animation);
                    itemTwinkle.setBackgroundColor(Color.YELLOW);
                    itemStat.setText("中级警报");
                    break;
                case 4:
                    itemTwinkle.clearAnimation();
                    itemTwinkle.setAnimation(animation);
                    itemTwinkle.setBackgroundColor(Color.RED);
                    itemStat.setText("高级警报");
                    break;
            }
        }
    }
}
