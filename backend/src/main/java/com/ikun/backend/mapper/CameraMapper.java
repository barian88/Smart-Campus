package com.ikun.backend.mapper;

import com.ikun.backend.entity.Camera;
import com.ikun.backend.entity.Event;
import com.ikun.backend.utils.pageUtil.PageRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CameraMapper {

    /**
    * @Description: 根据id查询camera
    * @Date:  2023/3/16 17:15
    **/
    Camera selectCameraById(int cameraId);

    /**
     * @description: 摄像头列表分类查询 + 条件查询
     * @param
     * @return 某页列表
     * @author: GUJianyang
     * @date: 2023/3/21 8:34
     */
    List<Camera> selectCameraByPageByCondition(PageRequest pageRequest);

    /**
    * @Description: 更新摄像头信息
    * @Date:  2023/3/22 16:42
    **/
    int updateCamera(Camera camera);

    /**
    * @Description:新增摄像头
     * @Return: 新增cameraId
    * @Date:  2023/3/22 17:05
    **/
    int insertCamera(Camera camera);

    /**
    * @Description: 按地区分类查询摄像头总数
    * @Date:  2023/3/28 17:06
    **/
    List<Map<String,Integer>> selectCountByArea();

    /**
    * @Description: 获取所有摄像头
     * @Return: 摄像头列表，包含摄像头id、摄像头url
    * @Date:  2023/4/2 10:19
    **/
    List<Camera> selectAllCamera();

    /**
    * @Description: 查询启动的摄像头总数
    * @Date:  2023/3/29 14:30
    **/
    int selectEnabledCount();

    /**
    * @Description: 检测camera_url是否重复
    * @Date:  2023/4/2 9:55
    **/
    int checkCameraUrlExistence(String cameraUrl);


}
