package com.ikun.backend.service;

import com.ikun.backend.entity.User;

import java.util.List;
import java.util.Map;

public interface CameraToUserService {

    /**
     * @Description: 根据cameraId查询负责人的userId
     * @Date:  2023/3/14 16:55
     **/
    List<Integer> queryUserIdByCameraId(Integer cameraId);

    /**
     * @Description: 查询摄像头对应的负责人信息
     * @Date:  2023/3/25 17:15
     **/
    List<User> querySuperintendents(int cameraId);

    /**
    * @description: 修改摄像头的负责人
    * @param userIds 对应user表id
    * @return
    * @author: GUJianyang
    * @date: 2023/3/23 16:39
    */
    Map<String,List<Integer>> alterSuperintendent(int cameraId, List<Integer> userIds);

    /**
     * @Description:判断 ListA 比 ListB 多了哪些元素，ListB 比 ListA 多了哪些元素
     * @Date:  2023/3/23 17:05
     **/
    Map<String,List<Integer>> compareList(List<Integer> oldList,List<Integer> newList);

    /**
     * 查询负责的摄像头个数
     * @param userId
     * @return
     */
    int selectCameraNumByUserId(int userId);

}
