package com.ikun.backend.service;

import com.ikun.backend.entity.User;
import com.ikun.backend.utils.AjaxResult;
import com.ikun.backend.utils.RedisUtil;
import com.ikun.backend.utils.SendSmsUtil;
import com.ikun.backend.utils.pageUtil.PageRequest;
import com.ikun.backend.utils.pageUtil.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

public interface UserService {


    /**
    * @Description:用户微信扫码登录，轮询判断是否登录成功
    * @Date:  2023/3/7 16:37
    **/
    AjaxResult wxLogin(String tempUserId);

    /**
    * @Description: 用户扫码后更新用户信息，如果存在则更新，不存在则新增
     *              向数据库添加微信用户
    * @Date:  2023/3/7 17:26
    **/
    int insertUser(User user);

    /**
    * @Description: 根据userId查询用户的openid
    * @Date:  2023/3/11 13:38
    **/
    List<String> queryOpenidByUserIds(List<Integer> userIds);



    /**
    * @Description: 绑定用户信息
    * @Date:  2023/3/20 8:32
    **/
    int bindUserInfo(int id, String realName, String phone, String schoolJobId);

    /**
    * @description: 根据id查询用户信息
    * @param id 数据库中的id字段
    * @return user对象
    * @author: GUJianyang
    * @date: 2023/3/21 12:08
    */
    User queryUserById(int id);

    /**
     * @description: 根据openid查询用户信息
     * @param openid
     * @return user对象
     * @author: GUJianyang
     * @date: 2023/3/21 12:08
     */
    User queryUserByOpenid(String openid);

    /**
    * @description: 微信用户登录
    * @param
    * @return
    * @author: GUJianyang
    * @date: 2023/3/21 23:54
    */
    User weixinUserLogin(String code);

    /**
     * @Description: 用户列表分页 + 条件查询
     * @Date:  2023/3/22 16:18
     **/
    PageResult queryUserByPageByCondition(PageRequest pageRequest);

    /**
    * @Description: 根据id批量查询用户信息
    * @Date:  2023/3/25 17:06
    **/
    List<User> queryUserByIds(List<Integer> ids);

    /**
    * @Description: 更新用户信息
    * @Date:  2023/3/28 16:09
    **/
    int updateUserById(User user);

    /**
    * @Description: 根据id删除用户
    * @Date:  2023/3/28 16:20
    **/
    int deleteUserById(int id);

    /**
    * @Description 修改用户的订阅数
    * @Date:  2023/4/3 17:52
    **/
    int updateSubscribeCount(int id, int count);
}
