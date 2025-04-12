package com.ikun.backend.service.impl;

import com.ikun.backend.entity.User;
import com.ikun.backend.mapper.CameraToUserMapper;
import com.ikun.backend.service.CameraService;
import com.ikun.backend.service.CameraToUserService;
import com.ikun.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CameraToUserServiceImpl implements CameraToUserService {

    @Autowired
    CameraToUserMapper cameraToUserMapper;
    @Autowired
    UserService userService;

    /**
     * @Description: 根据cameraId查询负责人的userId
     * @Date:  2023/3/14 16:55
     **/
    @Override
    public List<Integer> queryUserIdByCameraId(Integer cameraId) {
        return cameraToUserMapper.selectAccordingUserIds(cameraId);
    }

    /**
     * @Description: 查询摄像头对应的负责人信息
     * @Date:  2023/3/25 17:15
     **/
    @Override
    public List<User> querySuperintendents(int cameraId) {

        List<Integer> userIds = cameraToUserMapper.selectAccordingUserIds(cameraId);
        List<User> users = userService.queryUserByIds(userIds);
        return users;
    }

    /**
     * @description: 修改摄像头的负责人
     * @param userIds 对应user表id
     * @return
     * @author: GUJianyang
     * @date: 2023/3/23 16:39
     */
    @Override
    public Map<String, List<Integer>> alterSuperintendent(int cameraId, List<Integer> userIds) {
//       先查询现在摄像头的负责人有哪些
        List<Integer> oldUserIds = cameraToUserMapper.selectAccordingUserIds(cameraId);
//       获取前后差异
        Map<String,List<Integer>> contrast = this.compareList(oldUserIds,userIds);
//        原来没有，现在有的应该新增
        List<Integer> insertList = contrast.get("newListRedundancy");
        if(insertList.size() !=0 )
            cameraToUserMapper.insertSuperintendent(cameraId,insertList);
//        原来有，现在没有的应该删除
        List<Integer> deleteList = contrast.get("oldListRedundancy");
        if(deleteList.size() != 0)
            cameraToUserMapper.deleteSuperintendent(cameraId,deleteList);
//        封装返回结果
        Map<String, List<Integer>> res = new HashMap<>();
        res.put("新增",insertList);
        res.put("删除",deleteList);
        return res;

    }

    /**
    * @Description:判断 ListA 比 ListB 多了哪些元素，ListB 比 ListA 多了哪些元素
     * @Return: newListRedundancy,oldListRedundancy
    * @Date:  2023/3/23 17:05
    **/
    @Override
    public Map<String,List<Integer>> compareList(List<Integer> oldList,List<Integer> newList){

        Set<Integer> setB= new HashSet<Integer>();
        if(oldList != null && oldList.size() > 0){
            for(int element : oldList){
                setB.add(element);
            }
        }

        List<Integer> newListRedundancy = new ArrayList<Integer>();
        if(newList != null && newList.size() > 0){
            for(int element : newList){
                if(setB.contains(element)){
                    setB.remove(element);
                }
                else{
                    newListRedundancy.add(element);
                }
            }
        }

        List<Integer> oldListRedundancy = new ArrayList<Integer>();
        oldListRedundancy.addAll(setB);

        Map<String,List<Integer>> res = new HashMap<>();
        res.put("newListRedundancy",newListRedundancy);
        res.put("oldListRedundancy",oldListRedundancy);
        return res;
//        for(int elementA : newListRedundancy){
//            System.out.println("newList多余的元素:"+elementA);
//        }
//        for(int elementB : oldListRedundancy){
//            System.out.println("oldList多余的元素:"+elementB);
//        }

    }

    /**
     * 查询负责的摄像头个数
     */
    @Override
    public int selectCameraNumByUserId(int userId) {

        return cameraToUserMapper.selectCameraNumByUserId(userId);
    }
}
