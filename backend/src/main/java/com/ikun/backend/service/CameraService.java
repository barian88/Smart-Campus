package com.ikun.backend.service;

import com.ikun.backend.entity.Camera;
import com.ikun.backend.utils.pageUtil.PageRequest;
import com.ikun.backend.utils.pageUtil.PageResult;

import java.util.List;
import java.util.Map;

public interface CameraService {

    /**
     * @Description: 根据id查询camera
     * @Date:  2023/3/16 17:15
     **/
    Camera queryCameraById(Integer cameraId);

    /**
    * @Description: 摄像头列表分页 + 条件查询
    * @Date:  2023/3/22 16:18
    **/
    PageResult queryCameraByPageByCondition(PageRequest pageRequest);


    /**
    * @Description: 修改摄像头信息
    * @Date:  2023/3/22 16:47
    **/
    int updateCamera(Camera camera);

    /**
     * @Description: 新增摄像头
     * @Return: 新增cameraId
     * @Date: 2023/3/22 17:12
     **/
    int addCamera(Camera camera);

    /**
    * @Description: 按地区分类查询摄像头总数
    * @Date:  2023/3/28 17:13
    **/
    List<Map<String,Integer>> queryCountByArea();

    /**
    * @description: 查询所有摄像头
    * @return 包含id、url两个字段的摄像头列表
    * @author: GUJianyang
    * @date: 2023/4/2 10:24
    */
    List<Camera> queryAllCamera();

    /**
    * @Description: 查询启动的摄像头总数
    * @Date:  2023/3/29 14:32
    **/
    Map<String,Integer> queryEnabledCount();

    /**
    * @Description: 检测摄像头url是否重复
    * @Date:  2023/4/2 9:56
    **/
    boolean checkCameraUrlExistence(String cameraUrl);
}
