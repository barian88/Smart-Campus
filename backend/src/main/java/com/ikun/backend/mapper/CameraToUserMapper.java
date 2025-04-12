package com.ikun.backend.mapper;

import com.ikun.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CameraToUserMapper {
    /**
     * @Description: 根据cameraId查询对应的userId（userId对应的是user表中的id）
     * @Return: userIds
     * @Date:  2023/3/14 16:37
     **/
    List<Integer> selectAccordingUserIds(int cameraId);

    /**
    * @description: 添加摄像头的负责用户，即增加一条记录
    * @param userIds 对应user表的字段是id
    * @return 成功增加的记录数
    * @author: GUJianyang
    * @date: 2023/3/23 16:13
    */
    int insertSuperintendent(int cameraId, List<Integer> userIds);

    /**
     * @description: 删除摄像头的负责用户，即删除一条记录
     * @param userIds 对应user表的字段是id
     * @return 成功删除的记录数
     * @author: GUJianyang
     * @date: 2023/3/23 16:13
     */
    int deleteSuperintendent(int cameraId, List<Integer> userIds);

    /**
     * 查询负责的摄像头个数
     * @param userId
     * @return
     */
    int selectCameraNumByUserId(int userId);

}
