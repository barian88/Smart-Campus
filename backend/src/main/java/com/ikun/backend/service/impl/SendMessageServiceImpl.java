package com.ikun.backend.service.impl;

import com.ikun.backend.entity.Camera;
import com.ikun.backend.entity.Event;
import com.ikun.backend.entity.MessageSended;
import com.ikun.backend.entity.User;
import com.ikun.backend.service.CameraService;
import com.ikun.backend.service.CameraToUserService;
import com.ikun.backend.service.SendMessageService;
import com.ikun.backend.service.UserService;
import com.ikun.backend.utils.AjaxResult;
import com.ikun.backend.utils.RedisUtil;
import com.ikun.backend.utils.SendSmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SendMessageServiceImpl implements SendMessageService {
    @Value("${wx-app.appid}")
    String appid;
    @Value("${wx-app.secret}")
    String secret;
    @Value("${wx-app.send-message.template-id}")
    String templateId;
    @Value("${wx-app.send-message.page}")
    String page;
    @Value("${wx-app.send-message.miniprogram-state}")
    String miniprogramState;
    @Value("${wx-app.send-message.lang}")
    String lang;

    @Autowired
    UserService userService;
    @Autowired
    CameraService cameraService;
    @Autowired
    CameraToUserService cameraToUserService;
    @Resource
    SendSmsUtil sendSmsUtil;
    @Resource
    RedisUtil redisUtil;
    /**
     * @Description:向微信用户发送订阅信息
     * @Date:  2023/3/10 17:01
     **/
    @Override
    public Map<String,Integer> sendWeixinMessage(Event event,String cameraAreaValue,String cameraNameValue) {
//        获取access_token
        String accessToken = this.getAccessToken();
//        获取n个用户的消息发送请求body
        List<MessageSended> mss = this.generateBody(event,cameraAreaValue,cameraNameValue);
//        构造url
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send" +
                "?access_token=" + accessToken;
//        构造请求结果 key为openid，value为发送结果
        Map<String,HashMap<String,String>> results = new HashMap<>();
//        遍历发送消息
        int totalCount = 0;
        for (MessageSended ms : mss){
//            System.out.println(ms);
            HashMap<String,String> result = restTemplate.postForObject(url, ms, HashMap.class);
            results.put(ms.getOpenid(),result);
            totalCount++;
            System.out.println(result);
        }
//        返回消息发送结果
        final int[] successCount = {0};
        results.forEach((openid,result)->{
            if (result.get("errmsg").equals("ok")){
                successCount[0]++;
//                发送成功后，用户订阅可用次数减一
                User user = userService.queryUserByOpenid(openid);
                if(user.getSubscribeCount() > 0){
                    userService.updateSubscribeCount(user.getId(),-1);
                }
            }
        });
        Map<String,Integer> res = new HashMap<>();
        res.put("success", successCount[0]);
        res.put("total",totalCount);
        return res;

    }

    /**
    * @Description: 获取发送请求的access_token
    * @Date:  2023/3/10 17:12
    **/
    public String getAccessToken(){
        String accessToken = "";
//        先从redis中取
        accessToken = (String) redisUtil.get("accessToken");
        if (!(accessToken==null || accessToken.equals(""))){
            return accessToken;
        }
//        没有取到，再次请求
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/token" +
                "?grant_type=client_credential" +
                "&appid=" + appid +
                "&secret=" + secret;
        HashMap map = restTemplate.getForObject(url,HashMap.class);
        accessToken = (String) map.get("access_token");
//        将token添加到redis
        redisUtil.set("accessToken",accessToken,7200);

        return accessToken;
    }

    /**
    * @Description: 构造出发送订阅消息请求的body
    * @Date:  2023/3/10 17:03
    **/
    public List<MessageSended> generateBody(Event event,String cameraAreaValue,String cameraNameValue){
//      data为最终的请求体中的模板消息
        Map<String, Map<String,String>> data = new HashMap<>();
//      事件类型
        Map<String,String> eventType = new HashMap<>();
        eventType.put("value", event.getEventType());
        data.put("thing1", eventType);

//      设备位置
        Map<String,String> cameraArea = new HashMap<>();
        cameraArea.put("value", cameraAreaValue);
        data.put("thing2", cameraArea);
//      设备名称
        Map<String,String> cameraName = new HashMap<>();
        cameraName.put("value", cameraNameValue);
        data.put("thing3", cameraName);
//      发生时间
        Map<String,String> eventOccurrenceTime = new HashMap<>();
        eventOccurrenceTime.put("value", event.getEventOccurrenceTime());
        data.put("time4", eventOccurrenceTime);
//      备注
        Map<String,String> eventRemark = new HashMap<>();
        eventRemark.put("value", "请尽快处置！");
        data.put("thing5", eventRemark);


        List<MessageSended> mss = new ArrayList<>();
//        获取所有需要收到消息的员工的openid
        List<String> openids = getAccordingOpenid(event);
        for ( String openid : openids){
            //      构造不同openid的消息
            MessageSended ms = new MessageSended();
//            传递参数：当前eventId
            String param = "eventId=" + event.getEventId();
            ms.setData(data);
            ms.setTemplateId(templateId);
            ms.setPage(page + "?" + param);
            ms.setMiniprogramState(miniprogramState);
            ms.setLang(lang);
            ms.setOpenid(openid);
            mss.add(ms);
        }
        return mss;
    }

    /**
    * @Description: 获取所有要发送的用户的openid
    * @Date:  2023/3/11 13:50
    **/
    public List<String> getAccordingOpenid(Event event){
//        首先获取摄像头id
        int cameraId = event.getCameraId();
//        查询cameraId对应的userId
        List<Integer> userIds = cameraToUserService.queryUserIdByCameraId(cameraId);
//        根据userid查询用户的openid
        List<String> openids = userService.queryOpenidByUserIds(userIds);

        return openids;
    }

    /**
     * @Description: 登录注册时发送手机验证码
     * @Return: "OK" || "验证码发送失败"
     * @Date:  2023/3/17 17:08
     **/
    @Override
    public String sendPhoneMessage(String phone) {

        //发送验证码并存储到redis中
        return sendSmsUtil.sendSms(phone);
    }

    /**
    * @Description: 检查手机验证码是否正确
    * @Date:  2023/3/20 8:57
    **/
    @Override
    public boolean checkCaptcha(String phone, String captcha) {
        String realCaptcha = (String) redisUtil.get(phone);
        System.out.println(realCaptcha);
        return realCaptcha != null && realCaptcha.equals(captcha);
    }
}
