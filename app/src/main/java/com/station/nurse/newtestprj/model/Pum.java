package com.station.nurse.newtestprj.model;

public class Pum {
/*
    Slot:泵号；
    Type：设备类型；
    Model：设备型号；
    LinkStatus：连接状态；
    Total:总量；
    Sum:累积量；
    Drug：药名；
    Speed：设定流速；
    RealSpeed：实时流速；
    Remain：剩余量；
    LastTime：剩余时间；
    Alarm：告警事件；
    AlarmTime：告警时间；
    Rate：输液进度。*/

    private int Slot;
    private String Type;
    private String Model;
    private String  Total;
    private String  LinkStatus;
    private String Sum;
    private String Drug;
    private String Speed;
    private String RealSpeed;
    private String Remain;
    private String LastTime;
    private String Alarm;
    private String  AlarmTime;
    private String Rate;
    private String Sn;

    public String getSn() {
        return Sn;
    }

    public void setSn(String sn) {
        Sn = sn;
    }

    public int getSlot() {
        return Slot;
    }

    public void setSlot(int slot) {
        Slot = slot;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getLinkStatus() {
        return LinkStatus;
    }

    public void setLinkStatus(String linkStatus) {
        LinkStatus = linkStatus;
    }

    public String getSum() {
        return Sum;
    }

    public void setSum(String sum) {
        Sum = sum;
    }

    public String getDrug() {
        return Drug;
    }

    public void setDrug(String drug) {
        Drug = drug;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getRealSpeed() {
        return RealSpeed;
    }

    public void setRealSpeed(String realSpeed) {
        RealSpeed = realSpeed;
    }

    public String getRemain() {
        return Remain;
    }

    public void setRemain(String remain) {
        Remain = remain;
    }

    public String getLastTime() {
        return LastTime;
    }

    public void setLastTime(String lastTime) {
        LastTime = lastTime;
    }

    public String getAlarm() {
        return Alarm;
    }

    public void setAlarm(String alarm) {
        Alarm = alarm;
    }

    public String getAlarmTime() {
        return AlarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        AlarmTime = alarmTime;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }
}