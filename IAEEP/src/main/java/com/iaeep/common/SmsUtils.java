package com.iaeep.common;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.iaeep.entity.Sms;
import org.apache.http.HttpException;
import org.json.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
/**
 * @author DELL
 * @version 1.0
 * @Classname LoginController
 * @Description TODO
 * @CreateDate 2022/9/24 8:59
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/9/24 8:59
 */
public class SmsUtils {
    /**
     *
     * @param phoneNumber
     * @return resultInfo
     * @throws HttpException
     */
    public static String sentSms(String phoneNumber) throws HttpException {
        //建立相应实体类
        Sms sms=new Sms(phoneNumber);
        //腾讯云短信服务所需的参数
        Integer appId=1400697976;
        //秘钥
        String appKey="19ed7b012112a357bafcf1d8a57d4eaa";
        //模板id
        Integer templateId=1454414;
        //签名内容
        String smsSign="创新创业交流服务公众号";
        try {
            //参数数组
            String[] params={sms.getCode(),Integer.toString(sms.getOutTime())};
            SmsSingleSender smsSingleSender=new SmsSingleSender(appId,appKey);
            SmsSingleSenderResult smsSingleSenderResult=smsSingleSender.sendWithParam("86",sms.getPhoneNumber(),templateId,params,smsSign,"","");
            JSONObject resultObject = JSONObject.parseObject(String.valueOf(smsSingleSenderResult));
            String msg = resultObject.getString("errmsg");
            System.out.println("msg="+msg);
            if("OK".equals(msg)){
                return sms.getCode();
            }else {
                return "false";
            }
        }catch (HTTPException | JSONException | IOException e){
            ///e.printStackTrace();
            return "false";
        }
    }
}
