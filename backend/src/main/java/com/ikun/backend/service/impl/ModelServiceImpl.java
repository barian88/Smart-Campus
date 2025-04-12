package com.ikun.backend.service.impl;

import com.ikun.backend.entity.Model;
import com.ikun.backend.mapper.ModelMapper;
import com.ikun.backend.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    ModelMapper modelMapper;
    /**
     * @Description: 新增模型
     * @Date:  2023/3/23 23:34
     **/
    @Override
    public int addModel(Model model) {
        return modelMapper.insertModel(model);
    }

    /**
     * @Description: 查询模型名称是否已经存在
     * @Date:  2023/3/24 15:51
     **/
    @Override
    public boolean queryModelNameExistence(String modelName) {
        int i = modelMapper.queryModelNameExistence(modelName);
        return !(i == 0);
    }

    /**
     * @Description: 查询模型类型是否已经存在
     * @Date:  2023/3/24 20:08
     **/
    @Override
    public boolean queryModelTypeExistence(String modelType) {
        int i = modelMapper.queryModelTypeExistence(modelType);
        return !(i == 0);
    }

    /**
     * @Description: 查询所有模型，如果onlyComplete为true，则只查询已经完成的模型
     * @Date:  2023/3/25 13:20
     **/
    @Override
    public List<Model> queryModel(boolean onlyComplete) {
        return modelMapper.selectModel(onlyComplete);
    }

    /**
     * @Description: 删除模型
     * @Date:  2023/3/25 13:27
     **/
    @Override
    public int deleteModel(int modelId) {
        return modelMapper.deleteModel(modelId);
    }

    @Override
    public Model queryModelById(int modelId) {
        return modelMapper.selectModelById(modelId);
    }

    @Override
    public int updateModel(Model model) {
        return modelMapper.updateModel(model);
    }

    @Override
    public Model queryModelByType(String modelType) {
        return modelMapper.selectModelByType(modelType);
    }
}
