package com.mig.data.service.impl;

import lombok.SneakyThrows;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

public  class  DingTalkMessageService {

    private static String accessToken = "31e17602-efe5-4601-917e-f01092a01965";

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String MONITOR_URI = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=%s";
    private static final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    public static void sendText(String content) {
        String json = "{\n" +
                "    \"msgtype\": \"text\",\n" +
                "    \"text\": {\n" +
                "        \"content\": \"" + content + "\"\n" +
                "    },\n" +
                "    \"at\": {\n" +
                "        \"isAtAll\": false\n" +
                "    }\n" +
                "}";
        sentMessage(accessToken, json);
    }

    @SneakyThrows
    public static void sentMessage(String accessToken, String body) {
        HttpPost httpPost = new HttpPost(String.format(MONITOR_URI, accessToken));
        StringEntity stringEntity = new StringEntity(body, "UTF-8");
        httpPost.setEntity(stringEntity);
        httpClient.execute(httpPost);
    }

}
