package com.ikun.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraToModel {

    int ruleId;
    int cameraId;
    int modelId;


}
