package com.mig.data.service;

import com.mig.data.controller.dto.MessageTemplateCard;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2023-08-20 21:10
 */
public interface MessageService {

    void sendMessage(MessageTemplateCard messageTemplateCard, CloseableHttpClient httpClient, String robot) ;
}
