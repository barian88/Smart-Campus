package com.ikun.backend.service;

import com.ikun.backend.entity.Camera;
import com.ikun.backend.entity.CameraToModel;
import com.ikun.backend.entity.Model;

import java.util.List;
import java.util.Map;

public interface CameraToModelService {

    /**
    * @Description: 新增规则
    * @Date:  2023/3/25 16:12
    **/
    int addRule(List<CameraToModel> rules);

    /**
    * @Description: 删除规则
    * @Date:  2023/3/25 16:13
    **/
    int deleteRule(List<Integer> ruleIds);

    /**
    * @description: 查询摄像头应用的模型
    * @param cameraId
    * @return Model实例列表
    * @author: GUJianyang
    * @date: 2023/3/25 16:34
    */
    List<Model> queryRuleByCameraId(int cameraId);

    /**
    * @description: 获取所有配置规则
    * @param
    * @return   Map<Camera,Model>列表，Camera为key，Model为value，表示Camera应用Model模型
    * @author: GUJianyang
    * @date: 2023/4/2 10:18
    */
    List<Map<Camera,List<Model>>> queryAllRule();

    /**
    * @Description: 查询不同模型的应用数量
    * @Date:  2023/3/29 14:19
    **/
    List<Model> queryCountByModel();
}
