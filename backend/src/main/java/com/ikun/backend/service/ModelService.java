package com.ikun.backend.service;

import com.ikun.backend.entity.Model;

import java.util.List;

public interface ModelService {

    /**
     * @Description: 新增模型
     * @Date:  2023/3/23 23:34
     **/
    int addModel(Model model);

    /**
     * @Description: 查询模型名称是否已经存在
     * @Date:  2023/3/24 15:51
     **/
    boolean queryModelNameExistence(String modelName);

    /**
     * @Description: 查询模型类型是否已经存在
     * @Date:  2023/3/24 20:08
     **/
    boolean queryModelTypeExistence(String modelType);

    /**
     * @Description: 查询所有模型，如果onlyComplete为true，则只查询已经完成的模型
     * @Date:  2023/3/25 13:20
     **/
    List<Model> queryModel(boolean onlyComplete);

    /**
     * @Description: 删除模型
     * @Date:  2023/3/25 13:27
     **/
    int deleteModel(int modelId);

    /**
     * @Description: 根据id查询模型
     * @Date:  2023/3/25 16:44
     **/
    Model queryModelById(int modelId);

    /**
     * @Description: 更新模型
     * @Date:  2023/3/27 8:29
     **/
    int updateModel(Model model);

    /**
    * @Description: 根据modelType查询模型
    * @Date:  2023/4/5 13:35
    **/
    Model queryModelByType(String modelType);
}
