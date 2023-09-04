package com.mig.data.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2023-08-24 22:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageTemplateCard implements Serializable {

    private String msgtype;

    private TemplateCard news;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TemplateCard implements Serializable {

        private List<CardImage> articles;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CardImage{
            private String url;
            private String title;
            private String picurl;
            private String description;
        }
    }



}
