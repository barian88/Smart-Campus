package com.ikun.backend.utils.pageUtil.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ikun.backend.utils.pageUtil.PageRequest;

public class EventPageRequest extends PageRequest {
    //  地域
    private String eventArea;
    //  时间
    private String beginDate;
    private String endDate;
    //  摄像头id
    private int cameraId;
    //  事件类型
    private String eventType;
    //  按时间降序排列
    private boolean orderByTimeDesc = true;
    //  事件负责人id
    @JsonProperty("superintendentId")
    int userId;
    //  事件处理人姓名
    private String eventHandlerName;
    //  事件处理人工号
    private String eventHandlerJobId;
    //  事件状态
    @JsonProperty("eventStatus")
    private String eventStatus;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEventHandlerJobId() {
        return eventHandlerJobId;
    }

    public void setEventHandlerJobId(String eventHandlerJobId) {
        this.eventHandlerJobId = eventHandlerJobId;
    }

    public String getEventHandlerName() {
        return eventHandlerName;
    }

    public void setEventHandlerName(String eventHandlerName) {
        this.eventHandlerName = eventHandlerName;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }


    public boolean isOrderByTimeDesc() {
        return orderByTimeDesc;
    }

    public void setOrderByTimeDesc(boolean orderByTimeDesc) {
        this.orderByTimeDesc = orderByTimeDesc;
    }

    public String getEventArea() {
        return eventArea;
    }

    public void setEventArea(String eventArea) {
        this.eventArea = eventArea;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
