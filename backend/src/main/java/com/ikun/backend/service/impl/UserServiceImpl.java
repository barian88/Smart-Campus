package com.ikun.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ikun.backend.entity.Camera;
import com.ikun.backend.entity.User;
import com.ikun.backend.mapper.UserMapper;
import com.ikun.backend.service.UserService;
import com.ikun.backend.utils.AjaxResult;
import com.ikun.backend.utils.RedisUtil;
import com.ikun.backend.utils.SendSmsUtil;
import com.ikun.backend.utils.pageUtil.PageRequest;
import com.ikun.backend.utils.pageUtil.PageResult;
import com.ikun.backend.utils.pageUtil.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${wx-app.appid}")
    String appid;
    @Value("${wx-app.secret}")
    String secret;


    /**
     * @Description:用户微信扫码登录，轮询判断是否登录成功
     * @Date:  2023/3/7 16:37
     **/
    @Override
    public AjaxResult wxLogin(String tempUserId) {

        User user = userMapper.selectUserByTempUserId(tempUserId);
//        System.out.println(user);
        if (user == null)
            return AjaxResult.success("用户信息不存在");
        else
            return AjaxResult.success("OK",user);
    }

    /**
     * @Description: 用户扫码后更新用户信息，如果存在则更新，不存在则修改
     * @Date:  2023/3/7 17:26
     **/
    @Override
    public int insertUser(User user) {
//        设置角色为管理员
        user.setRoleId(1);
        int i = userMapper.insertUser(user);
        return i;
    }

    /**
     * @Description: 根据userId查询用户的openid
     * @Date:  2023/3/11 13:38
     **/
    @Override
    public List<String> queryOpenidByUserIds(List<Integer> userIds) {
        return userMapper.selectOpenidByUserIds(userIds);
    }

    /**
    * @Description: 绑定用户信息
    * @Date:  2023/3/20 8:32
    **/
    @Override
    public int bindUserInfo(int id, String realName, String phone, String schoolJobId) {

        User user = userMapper.selectUserById(id);
        user.setRealName(realName);
        user.setPhone(phone);
        user.setSchoolJobId(schoolJobId);
//        更新用户信息
        return userMapper.updateUser(user);
    }

    /**
     * @Description: 通过id查询user信息
     * @Date:  2023/3/21 23:55
     **/
    @Override
    public User queryUserById(int id) {
        return userMapper.selectUserById(id);
    }

    /**
     * @description: 获取user的openid
     * @param code
     * @return
     * @author: GUJianyang
     * @date: 2023/3/21 21:51
     */
    public String getOpenid(String code) {

        String grantType = "authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/jscode2session" + "?appid=" + appid
                + "&secret=" + secret + "&js_code=" + code + "&grant_type=" + grantType;
        String res =  restTemplate.getForObject(url, String.class );
        JSONObject jsonObject =  JSONObject.parseObject(res);

        return (String) jsonObject.get("openid");
    }

    /**
    * @description: 重写insert方法，向数据库添加微信用户
    * @param code,nickname,avatar
    * @return
    * @author: GUJianyang
    * @date: 2023/3/21 22:40
    */
    @Override
    public User weixinUserLogin(String code){

        User user = new User();
        String openid = getOpenid(code);
        user.setOpenid(openid);
//        设置角色为安保人员
        user.setRoleId(2);
//        更新数据库中的信息，增加或更新
        userMapper.insertUser(user);
//        查询此用户信息
        User userRes = userMapper.selectUserByOpenid(openid);
        return userRes;
    }

    @Override
    public User queryUserByOpenid(String openid) {
        return userMapper.selectUserByOpenid(openid);
    }

    /**
     * @Description: 用户列表分页 + 条件查询
     * @Date:  2023/3/22 16:18
     **/
    @Override
    public PageResult queryUserByPageByCondition(PageRequest pageRequest) {
        return PageUtil.getPageResult(pageRequest, getPageInfo(pageRequest));
    }
    /**
     * 调用分页插件完成分页
     * @param pageRequest
     * @return
     */
    private PageInfo<User> getPageInfo(PageRequest pageRequest){
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectUserByPageByCondition(pageRequest);
        return new PageInfo<User>(users);
    }

    /**
     * @Description: 根据id批量查询用户信息
     * @Date:  2023/3/25 17:06
     **/
    @Override
    public List<User> queryUserByIds(List<Integer> ids) {
        return userMapper.selectUserByIds(ids);
    }

    /**
    * @Description: 更新用户信息
    * @Date:  2023/3/28 16:10
    **/
    @Override
    public int updateUserById(User user) {
        return userMapper.updateUserById(user);
    }

    /**
    * @Description: 删除用户信息
    * @Date:  2023/3/28 16:20
    **/
    @Override
    public int deleteUserById(int id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public int updateSubscribeCount(int id, int count) {
        return userMapper.updateSubScribeCount(id,count);
    }
}
