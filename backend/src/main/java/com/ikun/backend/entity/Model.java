package com.ikun.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    int modelId;
    String modelType;
    String modelName;
    String modelDesc;
    String modelUrl;
    String modelDate;
    boolean customized;
    int currentEpoch;
    int totalEpoch;
    boolean modelStatus;

    //    不同模型被应用的数量
    @Transient
    int modelAppliedNumber;

//    ruleId，删除摄像头模型时会用到
    @Transient
    int ruleId;
}
