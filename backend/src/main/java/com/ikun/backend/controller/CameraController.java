package com.ikun.backend.controller;

import com.ikun.backend.entity.Camera;
import com.ikun.backend.service.CameraService;
import com.ikun.backend.utils.AjaxResult;
import com.ikun.backend.utils.pageUtil.children.CameraPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/camera")
public class CameraController {

    @Autowired
    CameraService cameraService;

    @PostMapping("getCameraByPageByCondition")
    AjaxResult getCameraByPageByCondition(@RequestBody CameraPageRequest pageQuery){
        if (pageQuery == null)
            return AjaxResult.error();
        return AjaxResult.success(cameraService.queryCameraByPageByCondition(pageQuery));
    }

    @PostMapping("updateCamera")
    AjaxResult updateCamera(@RequestBody Camera camera){
        if (camera == null || camera.getCameraId() == 0)
            return AjaxResult.error();
        return AjaxResult.success(cameraService.updateCamera(camera));
    }

    @PostMapping("addCamera")
    AjaxResult addCamera(@RequestBody Camera camera){
        if (camera == null)
            return AjaxResult.error();
        return AjaxResult.success(cameraService.addCamera(camera));
    }

    @GetMapping("getCountByArea")
    AjaxResult getCountByArea(){
        return AjaxResult.success(cameraService.queryCountByArea());
    }

    @GetMapping("getEnabledCount")
    AjaxResult getEnabledCount(){
        return AjaxResult.success(cameraService.queryEnabledCount());
    }

    @GetMapping("checkCameraUrlExistence")
    AjaxResult checkCameraUrlExistence(@RequestParam("cameraUrl") String cameraUrl){
        if (cameraUrl.equals(""))
            return AjaxResult.error();
        return AjaxResult.success(cameraService.checkCameraUrlExistence(cameraUrl));
    }
}
