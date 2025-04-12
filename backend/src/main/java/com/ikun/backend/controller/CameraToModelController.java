package com.ikun.backend.controller;

import com.ikun.backend.entity.CameraToModel;
import com.ikun.backend.service.CameraToModelService;
import com.ikun.backend.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cameraToModel")
public class CameraToModelController {

    @Autowired
    CameraToModelService cameraToModelService;

    @PostMapping("/addRule")
    AjaxResult addRule(@RequestBody List<CameraToModel> rules){
        if (rules.size() == 0)
            return AjaxResult.error();
        return AjaxResult.success(cameraToModelService.addRule(rules));
    }

    @DeleteMapping("/deleteRule")
    AjaxResult deleteRule(@RequestBody List<Integer> ruleIds){
        if (ruleIds.size() == 0 || ruleIds == null)
            return AjaxResult.error();
        return AjaxResult.success(cameraToModelService.deleteRule(ruleIds));
    }

    @GetMapping("getRuleByCameraId")
    AjaxResult getRuleByCameraId(@RequestParam int cameraId){
        if(cameraId == 0)
            return AjaxResult.error();
        return AjaxResult.success(cameraToModelService.queryRuleByCameraId(cameraId));
    }

    @GetMapping("getAllRule")
    AjaxResult getAllRule(){
        return AjaxResult.success(cameraToModelService.queryAllRule());
    }

    @GetMapping("getCountByModel")
    AjaxResult getCountByModel(){
        return AjaxResult.success(cameraToModelService.queryCountByModel());
    }
}
