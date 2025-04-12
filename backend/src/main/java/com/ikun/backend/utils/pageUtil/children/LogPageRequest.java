package com.ikun.backend.utils.pageUtil.children;

import com.ikun.backend.utils.pageUtil.PageRequest;

public class LogPageRequest extends PageRequest {
//    日志类型
    String logType;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
