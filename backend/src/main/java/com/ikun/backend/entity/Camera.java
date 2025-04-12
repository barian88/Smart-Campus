package com.ikun.backend.entity;

import lombok.*;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Camera {

    int cameraId;
    String cameraUrl;
    String cameraArea;
    String cameraName;
    boolean cameraStatus;
    String cameraLocation;
    boolean hasPerson;
    boolean edgeProtection;
    String edgeProtectionName;
    boolean fenceProtection;
    int delayParam;
    String arrayParam;
//    更新摄像头时，需要更新的字段
    @Transient
    List<String> newProperties;

}
