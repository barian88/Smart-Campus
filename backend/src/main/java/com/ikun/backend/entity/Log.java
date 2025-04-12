package com.ikun.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    int logId;
    String operator;
    String ipAddress;
    String logType;
    String logOperation;
    String logContent;
    String logTime;
}
