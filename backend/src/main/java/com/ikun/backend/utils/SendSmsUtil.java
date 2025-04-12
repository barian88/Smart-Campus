package com.ikun.backend.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Random;
/**
 * @Description: 发送短信的工具类
 * @Author: 会飞的种花家
 * @date: 2022/01/03
 */
@Component
public class SendSmsUtil {
    @Resource
    RedisUtil redisUtil;

    @Value("${tencent.sms.APP_ID}")
    int APP_ID;
    @Value("${tencent.sms.APP_KEY}")
    String APP_KEY;
    @Value("${tencent.sms.TEMPLATE_ID}")
    int TEMPLATE_ID;
    @Value("${tencent.sms.SIGN}")
    String SIGN;
    @Value("${tencent.sms.EFFECTIVE_Time}")
    int EFFECTIVE_Time;
    /**
     * 腾讯云发送短信
     * @param phone
     * @return
     */
    public String sendSms(String phone){
        if(!isPhone(phone).equals("手机号正确")){
            return "手机号不合法";
        }

        // 生成随机数
        String code=createRandom();
        SmsSingleSenderResult result =null;
        try {
            // 模板需要的参数
            String[] params = {code,"3"};
            SmsSingleSender ssender = new SmsSingleSender(APP_ID, APP_KEY);
            // 单发短信
            result = ssender.sendWithParam("86", phone, TEMPLATE_ID, params, SIGN, "", "");
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        } catch (HTTPException e) {
            e.printStackTrace();
        }
        // 保存验证码到redis
        redisUtil.set(phone,code, EFFECTIVE_Time);
        if(!"OK".equals(result.errMsg)){
            return "验证码发送失败";
        }
        return result.errMsg;
    }

    /**
     * 验证码长度（通过更改i的最大值）
     * 获取6位随机数
     * @return
     */
    public String createRandom(){
        Random random = new Random();
        String result="";
        for (int i=0;i<6;i++)
        {
            result+=random.nextInt(10);
        }
        return result;
    }

    /**
     * 利用正则表达式检查字符串是否为合法的手机号码
     * @param phone
     * @return
     */
    public static String isPhone(String phone) {
        // 开头的"1"代表第一位为数字1，"[3-9]"代表第二位可以为3、4、5、6、7、8、9其中之一，"\\d{9}"代表后面是9位数字
        String regex = "1[3-9]\\d{9}";
        // 字符串变量的matches方法返回正则表达式对该串的检验结果，true表示符合字符串规则，false表示不符合规则
        if(phone.matches(regex)){
            return "手机号正确";
        }else{
            return "手机号错误";
        }
    }
}
