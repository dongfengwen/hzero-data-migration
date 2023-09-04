package com.mig.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.mig.data.constant.MonitorConstant;
import com.mig.data.controller.dto.MessageTemplateCard;
import com.mig.data.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2022-06-05 15:42
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {


    @Override
    @Async
    public void sendMessage(MessageTemplateCard messageTemplateCard, CloseableHttpClient httpClient, String robot) {
        HttpPost httpPost = new HttpPost(robot);
        String jsonString = JSON.toJSONString(messageTemplateCard);
        StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");
        log.info("jsonString:{}",jsonString);
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse execute = null;
        try {
            execute = httpClient.execute(httpPost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info(execute.getEntity().toString());
    }
}
