package com.ikun.backend.controller;

import com.ikun.backend.service.CameraToUserService;
import com.ikun.backend.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cameraToUser")
public class CameraToUserController {

    @Autowired
    CameraToUserService cameraToUserService;

    @GetMapping("/getSuperintendent")
    AjaxResult getSuperintendent(@RequestParam("cameraId") int cameraId){
        if (cameraId == 0)
            return AjaxResult.error();
        return AjaxResult.success(cameraToUserService.querySuperintendents(cameraId));
    }

    @PostMapping("/alterSuperintendent")
    AjaxResult alterSuperintendent(@RequestParam("cameraId") int cameraId,
                                   @RequestBody List<Integer> userIds){
        if (cameraId == 0 || userIds == null || userIds.size() == 0)
            return AjaxResult.error();
        System.out.println(cameraId);
        System.out.println(userIds);
        return AjaxResult.success(cameraToUserService.alterSuperintendent(cameraId, userIds));
    }

    @GetMapping("/getCameraNumByUserId")
    AjaxResult selectCameraNumByUserId(@RequestParam("id") int userId){
        int cameraNum = cameraToUserService.selectCameraNumByUserId(userId);
        return AjaxResult.success(cameraNum);
    }

}
