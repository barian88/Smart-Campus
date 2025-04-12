package com.ikun.backend.controller;

import com.ikun.backend.entity.Model;
import com.ikun.backend.service.ModelService;
import com.ikun.backend.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    ModelService modelService;

    @PostMapping("/addModel")
    AjaxResult addModel(@RequestBody Model model){
        if(model == null)
            return AjaxResult.error();
        return AjaxResult.success(modelService.addModel(model));
    }

   @GetMapping("/queryModelNameExistence")
    AjaxResult queryModelNameExistence(@RequestParam("modelName") String modelName){
        if(modelName == null || modelName.equals(""))
            return AjaxResult.error();
        return AjaxResult.success(modelService.queryModelNameExistence(modelName));
    }

    @GetMapping("/queryModelTypeExistence")
    AjaxResult queryModelTypeExistence(@RequestParam("modelType") String modelType){
        if(modelType == null || modelType.equals(""))
            return AjaxResult.error();
        return AjaxResult.success(modelService.queryModelTypeExistence(modelType));
    }

    @GetMapping("/getAllModel")
    AjaxResult getModel(@RequestParam("onlyComplete") boolean onlyComplete){
        return AjaxResult.success(modelService.queryModel(onlyComplete));
    }

    @DeleteMapping("/deleteModel")
    AjaxResult deleteModel(@RequestParam("modelId") int modelId){
        if(modelId == 0)
            return AjaxResult.error();
        return AjaxResult.success(modelService.deleteModel(modelId));
    }

    @PostMapping("/updateModel")
    AjaxResult updateModel(@RequestBody Model model){
        if(model == null)
            return AjaxResult.error();
        return AjaxResult.success(modelService.updateModel(model));
    }
}
