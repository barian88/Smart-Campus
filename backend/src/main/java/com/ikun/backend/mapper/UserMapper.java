package com.ikun.backend.mapper;

import com.ikun.backend.entity.Camera;
import com.ikun.backend.entity.User;
import com.ikun.backend.utils.pageUtil.PageRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
    * @Description: 用户扫码后更新用户信息，如果存在则更新，不存在则修改
    * @Date:  2023/3/8 16:40
    **/
    int insertUser(User user);

    /**
    * @Description: 扫码登录时，根据临时userid查询用户是否扫码
    * @Date:  2023/3/8 17:01
    **/
    User selectUserByTempUserId(String tempUserId);

    /**
    * @description: 根据id查询user
    * @param id
    * @return user实例
    * @author: GUJianyang
    * @date: 2023/3/21 11:57
    */
    User selectUserById(int id);
    /**
     *
     * @description: 根据openid查询user
     * @param openid
     * @return user实例
     * @author: GUJianyang
     * @date: 2023/3/21 11:57
     */
    User selectUserByOpenid(String openid);

    /**
    * @Description: 根据userId查询用户的openid（userId对应的是user表中的id）
     * @Rturn: Openids
    * @Date:  2023/3/11 13:40
    **/
    List<String> selectOpenidByUserIds(List<Integer> userIds);

    /**
    * @Description: 绑定用户真实信息
    * @Date:  2023/3/20 8:40
    **/
    int updateUser(User user);


    /**
     * @description: 用户列表分类查询 + 条件查询
     * @param
     * @return 某页列表
     * @author: GUJianyang
     * @date: 2023/3/21 8:34
     */
    List<User> selectUserByPageByCondition(PageRequest pageRequest);

    /**
    * @Description: 根据id批量查询用户信息
    * @Date:  2023/3/25 17:04
    **/
    List<User> selectUserByIds(List<Integer> userIds);

    /**
    * @Description: 修改用户信息
    * @Date:  2023/3/28 16:00
    **/
    int updateUserById(User user);

    /**
    * @Description: 删除用户
    * @Date:  2023/3/28 16:19
    **/
    int deleteUserById(int id);

    /**
    * @Description: 修改微信用户的订阅数
    * @Date:  2023/4/3 17:51
    **/
    int updateSubScribeCount(int id,int number);


}
