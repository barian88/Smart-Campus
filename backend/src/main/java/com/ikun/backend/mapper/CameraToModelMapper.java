package com.ikun.backend.mapper;

import com.ikun.backend.entity.CameraToModel;
import com.ikun.backend.entity.Model;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CameraToModelMapper {

    /**
    * @description: 新增摄像头到模型的映射关系
    * @param rules 要添加的规则实例
    * @return
    * @author: GUJianyang
    * @date: 2023/3/25 14:24
    */
    int insertCameraToModel(List<CameraToModel> rules);

    /**
    * @description: 删除摄像头到模型的映射关系
    * @param ruleIds 要删除的规则实例id
    * @return
    * @author: GUJianyang
    * @date: 2023/3/25 14:24
    */
    int deleteCameraToModel(List<Integer> ruleIds);

    /**
    * @description: 查询摄像头到模型的映射关系
    * @param cameraId 摄像头id
    * @return
    * @author: GUJianyang
    * @date: 2023/3/25 14:28
    */
    List<CameraToModel> selectRuleByCameraId(int cameraId);

    /**
    * @description: 查询不同模型的应用数量
    * @param
    * @return   model包含modelId和modelAppliedNumber两个属性
    * @author: GUJianyang
    * @date: 2023/3/29 14:07
    */
    List<Model> selectCountByModel();
}
