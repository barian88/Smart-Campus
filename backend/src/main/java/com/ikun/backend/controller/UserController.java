package com.ikun.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.ikun.backend.entity.User;
import com.ikun.backend.service.UserService;
import com.ikun.backend.utils.AjaxResult;
import com.ikun.backend.utils.pageUtil.children.UserPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;


    /**
    * @Description: 码上登录的回调函数，todo写自己的逻辑，其他不用修改
    * @Date:  2023/3/9 8:12
    **/
    @PostMapping(value = "/callback")
    public JSONObject backUserScanedInfo(
            @RequestParam(value = "userId",required = false)  String userId,
            @RequestParam(value = "tempUserId",required = false) String tempUserId,
            @RequestParam(value = "nickname",required = false) String nickname,
            @RequestParam(value = "avatar",required = false) String avatar,
            @RequestParam(value = "ipAddr",required = false) String ipAddr,
            HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("用户信息============\r\n"+ "【userId】"+userId+ " 【tempUserId】 "+tempUserId+ " 【nickname】 "+nickname+ " 【headimgurl】 "+avatar+ " 【ipAddr】"+ipAddr);

        // 写自己的逻辑 保存用户或更新用户信息到数据库
        User user = new User();
        user.setUserId(userId);
        user.setTempUserId(tempUserId);
        user.setAvatar(avatar);
        user.setNickname(nickname);
        user.setIpAddr(ipAddr);
        userService.insertUser(user);

        // 返回状态码
        JSONObject jsonObject = new JSONObject();
        if (userId != null){
            jsonObject.put("errcode",0);
            jsonObject.put("message","success");
        }else {
            jsonObject.put("errcode",1);
            jsonObject.put("message","error");
        }
        return jsonObject;
    }

    @GetMapping("/polling")
    public AjaxResult checkLogin( @RequestParam(value = "tempUserId")  String tempUserId){
        if (tempUserId.equals(""))
            return AjaxResult.error();
        else
            return userService.wxLogin(tempUserId);
    }

    @PostMapping("/register")
    public AjaxResult userRegister(@RequestParam("id") int id,
                                   @RequestParam("realName") String realName,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("schoolJobId") String schoolJobId){
        if (id == 0 || realName.equals("") || phone.equals("") || schoolJobId.equals(""))
            return AjaxResult.error();
        return AjaxResult.success(userService.bindUserInfo(id, realName, phone, schoolJobId));
    }

    @GetMapping("/getUserById")
    public AjaxResult getUserById(@RequestParam("id") int id){
        if (id == 0)
            return AjaxResult.error();
        return AjaxResult.success(userService.queryUserById(id));
    }

    @PostMapping("/getUserByIds")
    public AjaxResult getUserByIds(@RequestBody List<Integer> ids){
        if (ids.size() == 0 || ids == null)
            return AjaxResult.error();
        return AjaxResult.success(userService.queryUserByIds(ids));
    }

    @PostMapping("/weixinUserLogin")
    public AjaxResult weixinUserLogin(@RequestParam("code") String code){
        if(code.equals(""))
            return AjaxResult.error();
        return AjaxResult.success(userService.weixinUserLogin(code));
    }

    @PostMapping("getUserByPageByCondition")
    AjaxResult getCameraByPageByCondition(@RequestBody UserPageRequest pageQuery){
        if (pageQuery == null)
            return AjaxResult.error();
        return AjaxResult.success(userService.queryUserByPageByCondition(pageQuery));
    }

    @PostMapping("updateUserById")
    AjaxResult updateUserById(@RequestBody User user){
        if (user == null)
            return AjaxResult.error();
        return AjaxResult.success(userService.updateUserById(user));
    }

    @DeleteMapping("deleteUserById")
    AjaxResult deleteUserById(@RequestParam("id") int id){
        if (id == 0)
            return AjaxResult.error();
        return AjaxResult.success(userService.deleteUserById(id));
    }


    @GetMapping("/updateSubScribeCount")
    AjaxResult updateSubScribeCount(@RequestParam("id") int id,
                                    @RequestParam("number") int number){
        if (id != 0 && (number == 1 || number == -1))
            return AjaxResult.success(userService.updateSubscribeCount(id,number));
        return AjaxResult.error();
    }
}
