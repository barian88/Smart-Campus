package com.ikun.backend.service.impl;

import com.ikun.backend.entity.*;
import com.ikun.backend.mapper.CameraToModelMapper;
import com.ikun.backend.service.CameraService;
import com.ikun.backend.service.CameraToModelService;
import com.ikun.backend.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CameraToModelServiceImpl implements CameraToModelService {
    @Autowired
    CameraToModelMapper cameraToModelMapper;
    @Autowired
    ModelService modelService;
    @Autowired
    CameraService cameraService;

    /**
     * @Description: 新增规则
     * @Date:  2023/3/25 16:12
     **/
    @Override
    public int addRule(List<CameraToModel> rules) {
        return cameraToModelMapper.insertCameraToModel(rules);
    }

    /**
     * @Description: 删除规则
     * @Date:  2023/3/25 16:13
     **/
    @Override
    public int deleteRule(List<Integer> ruleIds) {
        return cameraToModelMapper.deleteCameraToModel(ruleIds);
    }

    /**
     * @description: 查询摄像头应用的模型
     * @param cameraId
     * @return Model实例列表
     * @author: GUJianyang
     * @date: 2023/3/25 16:34
     */
    @Override
    public List<Model> queryRuleByCameraId(int cameraId) {
        List<CameraToModel> rules = cameraToModelMapper.selectRuleByCameraId(cameraId);
        List<Model> result = new ArrayList<>();
        for (CameraToModel rule : rules) {
            Model model = modelService.queryModelById(rule.getModelId());
            model.setRuleId(rule.getRuleId());
            result.add(model);
        }
        return result;
    }

    /**
     * @description: 获取所有配置规则
     * @param
     * @return   Map<Camera,Model>列表，Camera为key，Model为value，表示Camera应用Model模型
     * @author: GUJianyang
     * @date: 2023/4/2 10:18
     */
    @Override
    public List<Map<Camera, List<Model>>> queryAllRule() {
//        获取所有摄像头
        List<Camera> cameraList = cameraService.queryAllCamera();
//        构造返回结果
        List<Map<Camera,List<Model>>> result = new ArrayList<>();
        for(Camera camera : cameraList){
            Map<Camera,List<Model>> item = new HashMap<>();
//            查询摄像头应用的模型
            List<Model> modelList = this.queryRuleByCameraId(camera.getCameraId());
            if (modelList.size() != 0){
                item.put(camera,modelList);
                result.add(item);
            }
        }
        if (result.size() == 0){
            return null;
        }
        return result;
    }

    /**
     * @Description: 查询不同模型的应用数量
     * @Date:  2023/3/29 14:19
     **/
    @Override
    public List<Model> queryCountByModel() {
//       得到 modelId,count
        List<Model> models = cameraToModelMapper.selectCountByModel();
//       补充modelName字段
        for (Model model : models ){
            model.setModelName(modelService.queryModelById(model.getModelId()).getModelName());
        }
        return models;

    }
}
