package com.ikun.backend.utils.pageUtil.children;


import com.ikun.backend.utils.pageUtil.PageRequest;

import java.util.List;

public class CameraPageRequest extends PageRequest {
//    摄像头区域
    private List<String> cameraAreaList;

    public List<String> getCameraAreaList() {
        return cameraAreaList;
    }

    public void setCameraAreaList(List<String> cameraAreaList) {
        this.cameraAreaList = cameraAreaList;
    }

}
