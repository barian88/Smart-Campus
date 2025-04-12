package com.ikun.backend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ikun.backend.entity.Camera;
import com.ikun.backend.entity.Log;
import com.ikun.backend.mapper.CameraMapper;
import com.ikun.backend.service.CameraService;
import com.ikun.backend.utils.pageUtil.PageRequest;
import com.ikun.backend.utils.pageUtil.PageResult;
import com.ikun.backend.utils.pageUtil.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CameraServiceImpl implements CameraService {

    @Autowired
    CameraMapper cameraMapper;


    /**
     * @Description: 根据id查询camera
     * @Date:  2023/3/16 17:15
     **/
    @Override
    public Camera queryCameraById(Integer cameraId) {
        return cameraMapper.selectCameraById(cameraId);
    }

    /**
     * @Description: 摄像头列表分页 + 条件查询
     * @Date:  2023/3/22 16:18
     **/
    @Override
    public PageResult queryCameraByPageByCondition(PageRequest pageRequest) {
        return PageUtil.getPageResult(pageRequest, getPageInfo(pageRequest));
    }
    /**
     * 调用分页插件完成分页
     * @param pageRequest
     * @return
     */
    private PageInfo<Camera> getPageInfo(PageRequest pageRequest){
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Camera> cameras = cameraMapper.selectCameraByPageByCondition(pageRequest);
        return new PageInfo<Camera>(cameras);
    }

    /**
     * @Description: 修改摄像头信息
     * @Return: 修改的行数
     * @Date: 2023/3/22 17:12
     **/
    @Override
    public int updateCamera(Camera camera) {
        return cameraMapper.updateCamera(camera);
    }

    /**
     * @Description: 新增摄像头
     * @Return: 新增的cameraId
     * @Date: 2023/3/22 17:12
     **/
    @Override
    public int addCamera(Camera camera) {

        cameraMapper.insertCamera(camera);
        return camera.getCameraId();
    }

    /**
     * @Description: 按地区分类查询摄像头总数
     * @Date:  2023/3/28 17:13
     **/
    @Override
    public List<Map<String, Integer>> queryCountByArea() {
        return cameraMapper.selectCountByArea();
    }

    /**
     * @description: 查询所有摄像头
     * @return 包含id、url两个字段的摄像头列表
     * @author: GUJianyang
     * @date: 2023/4/2 10:24
     */
    @Override
    public List<Camera> queryAllCamera(){
        return cameraMapper.selectAllCamera();
    }

    /**
     * @Description: 查询启动的摄像头总数
     * @Date:  2023/3/29 14:32
     **/
    @Override
    public  Map<String,Integer> queryEnabledCount() {
        Map<String,Integer> res = new HashMap<>();
        res.put("enabledCount",cameraMapper.selectEnabledCount());
        return res;

    }

    @Override
    public boolean checkCameraUrlExistence(String cameraUrl) {
        int i = cameraMapper.checkCameraUrlExistence(cameraUrl);
        return i != 0;
    }
}
