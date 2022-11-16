package com.iaeep.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.iaeep.common.R;
import com.iaeep.common.SpringUtils;
import com.iaeep.entity.ChatRecord;
import com.iaeep.service.ChatRecordService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author DELL
 * @version 1.0
 * @Classname ChatRecordController
 * @Description TODO
 * @CreateDate 2022/10/2 9:44
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:44
 */
@Controller
@ServerEndpoint("/im_server/{userId}")
@Component
@Slf4j
public class ChatRecordController {

    private static ChatRecordService chatRecordService = SpringUtils.getBean(ChatRecordService.class);

    @Scheduled(fixedDelay = 1000*60)
    public static void saveChatRecord(){
        //每隔60秒将暂存的聊天信息存入数据库
        if (chatRecordList !=null && chatRecordList.size() > 0){
            chatRecordService.saveBatch(chatRecordList);
            //清空聊天记录
            chatRecordList.clear();
        }
    }




    /**暂时保存聊天信息集合*/
    private static ArrayList<ChatRecord> chatRecordList = new ArrayList<ChatRecord>();

    //获取暂时聊天信息集合
    public static ArrayList<ChatRecord> getChatRecordList(){
        return chatRecordList;
    }

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;

    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。string 是用户名，后面是WebSocketServer对象*/
    private static ConcurrentHashMap<Long, ChatRecordController> webSocketMap = new ConcurrentHashMap<>();

    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;

    /**接收userId 表示该WebSocketServer是属于那个客户端（用户）*/
    private Long userId;


    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") Long userId) {
        System.out.println("userId=" + userId);
        this.session = session;
        this.userId=userId;
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
            //加入set中
        }else{
            webSocketMap.put(userId,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());
        try {
            //获取聊天记录
            if (chatRecordList != null && chatRecordList.size() > 0) {
                //当用户登录时先判断临时消息集合是否为空，为空则存入数据库，在查询；
                chatRecordService.saveBatch(chatRecordList);
                chatRecordList.clear();
            }
            List<ChatRecord> chatRecords = new ArrayList<ChatRecord>();
            LambdaQueryWrapper<ChatRecord> queryWrapper= new LambdaQueryWrapper<>();
            //别人发个当前用户和当前用户发给别人的消息；
            queryWrapper.eq(ChatRecord::getFromUserId, this.userId).or().eq(ChatRecord::getToUserId, this.userId);
            chatRecords = chatRecordService.list(queryWrapper);
            //将消息发送给自己
            this.sendMessage(JSON.toJSONString(chatRecords));
        } catch (IOException e) {
            log.error("用户:"+userId+",网络异常!!!!!!");
        }


    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(@RequestBody String message, Session session) {
        log.info("用户消息:"+userId+",报文:"+message);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss");
        //可以群发消息
        //消息保存到数据库
        /** message={"toUserId","fromUserId","content","contentType","time","id"}*/
        if(StringUtils.isNotBlank(message)){
            JSONObject jsonObject = JSON.parseObject(message);
            try {
                //解析发送的报文
                Long toUserId=jsonObject.getLong("toUserId");
                //暂存信息到临时集合
                ChatRecord chatRecord=new ChatRecord();
                chatRecord.setToUserId(toUserId);
                chatRecord.setFromUserId(jsonObject.getLong("fromUserId"));
                chatRecord.setContent(jsonObject.getString("content"));
                chatRecord.setContentType(jsonObject.getString("contentType"));
                chatRecord.setTime(format2.parse(jsonObject.getString("time")));
                //传送给对应toUserId用户的websocket
                // 格式化时间
                jsonObject.put("time",format2.format(format2.parse(jsonObject.getString("time"))));
                if(StringUtils.isNotBlank(String.valueOf(toUserId))&&webSocketMap.containsKey(toUserId)){
                    System.out.println("向对方发送的数据："+jsonObject.toJSONString());
                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
                    chatRecord.setIsRead(0);
                    chatRecordList.add(chatRecord);
                }else{
                    log.info("请求的userId:"+toUserId+"不在该服务器上");
                    System.out.println(webSocketMap);
                    //用户不在线，设置未读标记
                    chatRecord.setIsRead(0);
                    chatRecordList.add(chatRecord);
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */

    public void sendMessage(String message) throws IOException {
        //同步消息发送
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:"+userId+"，报文:"+message);
        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        }else{
            log.error("用户"+userId+",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ChatRecordController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ChatRecordController.onlineCount--;
    }


}
