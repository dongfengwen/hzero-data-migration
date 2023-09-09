package com.mig.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.mig.data.constant.MonitorConstant;
import com.mig.data.controller.dto.*;
import com.mig.data.entity.DcPrice;
import com.mig.data.mapper.DcPriceMapper;
import com.mig.data.service.MessageService;
import com.mig.data.service.MonitorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.mig.data.constant.MonitorConstant.CACHE_CDDE.*;


/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2023-08-20 19:54
 */
@Service
@Slf4j
public class MonitorServiceImpl implements MonitorService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private DcPriceMapper dcPriceMapper;

    @Override
    public List<MonitorChgDTO> chg(MonitorChgRequest request) {
        //调用 东方财富 API地址 http://77.push2.eastmoney.com/api/qt/clist/get
        request = request == null ? new MonitorChgRequest() : request;
        request.setFs(StringUtils.isEmpty(request.getFs()) ? MonitorConstant.CODE_CONSTANT.FS : request.getFs());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(MonitorConstant.CODE_CONSTANT.URL + request.getFs() + "&_" + (System.currentTimeMillis() / 1000));
        try {
            // 3. 执行GET请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            // 4. 获取响应实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                MonitorChgResponse monitorChgResponse = JSON.parseObject(new JSONObject(EntityUtils.toString(entity, "UTF-8")).toString(), MonitorChgResponse.class);
                List<MonitorChgDTO> diff = monitorChgResponse.getData().getDiff();
                MonitorChgDTO monitorChgDTO = diff.get(0);
                //涨速
                String speedNowF22 = monitorChgDTO.getF22();
                if (new BigDecimal(speedNowF22).compareTo(new BigDecimal(3)) > 0) {
                    log.info("涨速:{}", speedNowF22);
                    String key = "DC:CHG:" + monitorChgDTO.getF12();
                    if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(key))) {
                        stringRedisTemplate.boundValueOps(key).set(speedNowF22, 30, TimeUnit.SECONDS);
                        this.changeDbDcPrice(monitorChgDTO);
                        messageService.sendMessage(changeChgMessage(monitorChgDTO), httpClient, MonitorConstant.WE_CHAT_CONSTANT.ROBOT);
                    } else {
                        String speedDbF22 = stringRedisTemplate.opsForValue().get(key);
                        if (new BigDecimal(speedNowF22).compareTo(new BigDecimal(speedDbF22)) > 0) {
                            stringRedisTemplate.boundValueOps(key).set(speedNowF22, 30, TimeUnit.SECONDS);
                            this.changeDbDcPrice(monitorChgDTO);
                            messageService.sendMessage(changeChgMessage(monitorChgDTO), httpClient, MonitorConstant.WE_CHAT_CONSTANT.ROBOT);
                        }
                    }
                }
                return diff;
            }
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return Collections.emptyList();
    }

    private void changeDbDcPrice(MonitorChgDTO monitorChgDTO) {
        DcPrice dcPrice = new DcPrice();
        dcPrice.setCode(monitorChgDTO.getF12())
                .setName(monitorChgDTO.getF14())
                .setNowPrice(monitorChgDTO.getF2())
                .setIncreaseTage(monitorChgDTO.getF3())
                .setIncreasePrice(monitorChgDTO.getF4())
                .setTotalHand(monitorChgDTO.getF5())
                .setTurnover(monitorChgDTO.getF6())
                .setPeMove(monitorChgDTO.getF9())
                .setHighest(monitorChgDTO.getF15())
                .setMinimum(monitorChgDTO.getF16())
                .setPrefix(monitorChgDTO.getF13())
                .setCreateTime(new Date())
                .setOpenPrice(monitorChgDTO.getF17())
                .setReceived(monitorChgDTO.getF7())
                .setSpeed(monitorChgDTO.getF22());
        dcPriceMapper.insert(dcPrice);
    }

    private MessageTemplateCard changeChgMessage(MonitorChgDTO monitorChgDTO) {
        return changeMessage("DC行情:" + monitorChgDTO.getF14() + " " + monitorChgDTO.getF22(), changeText(monitorChgDTO), changeSCode(monitorChgDTO), changeCode(monitorChgDTO));
    }

    private MessageTemplateCard changeMessage(String title, String description, String codeCn, String codeNum) {
        MessageTemplateCard messageTemplateCard = new MessageTemplateCard();
        messageTemplateCard.setMsgtype("news");
        MessageTemplateCard.TemplateCard templateCard = new MessageTemplateCard.TemplateCard();
        MessageTemplateCard.TemplateCard.CardImage cardImage = new MessageTemplateCard.TemplateCard.CardImage();
        //跳转URL
        cardImage.setUrl(MonitorConstant.CODE_CONSTANT.JUMP_URL + codeCn + ".html");
        //展示图片
        cardImage.setPicurl(MonitorConstant.CODE_CONSTANT.DAY_IMG + codeNum + "&timespan=" + (System.currentTimeMillis() / 1000));
        cardImage.setTitle(title);
        cardImage.setDescription(description);
        templateCard.setArticles(Collections.singletonList(cardImage));
        messageTemplateCard.setNews(templateCard);
        return messageTemplateCard;
    }


    private String changeText(MonitorChgDTO monitorChgDTO) {
        return "代码 : " + monitorChgDTO.getF12() + " \t名称 : " + monitorChgDTO.getF14()
                + "\n\n当前价: " + new BigDecimal(monitorChgDTO.getF2()).setScale(2, RoundingMode.HALF_DOWN) + "元\t涨跌幅: " + monitorChgDTO.getF3() + "%"
                + "\n\n成交量: " + (new BigDecimal(monitorChgDTO.getF6()).divide(new BigDecimal(100000000), 2, RoundingMode.HALF_DOWN)) + "亿\t换手率: " + monitorChgDTO.getF8() + "%"
                + "\n\n总值 : " + (new BigDecimal(monitorChgDTO.getF20()).divide(new BigDecimal(100000000), 2, RoundingMode.HALF_DOWN)) + "亿\tPE(动): " + monitorChgDTO.getF9();
    }

    private String changeSCode(MonitorChgDTO monitorChgDTO) {
        String f12 = monitorChgDTO.getF12();
        if (f12.startsWith("30")) {
            if (f12.startsWith("301")) {
                return "sz" + monitorChgDTO.getF12();
            } else {
                return "sz" + monitorChgDTO.getF12();
            }
        } else if (f12.startsWith("60")) {
            return "sh" + monitorChgDTO.getF12();
        } else if (f12.startsWith("00")) {
            return "sz" + monitorChgDTO.getF12();
        }
        return (StringUtils.equals("1", monitorChgDTO.getF13()) ? "sz" : "sh") + monitorChgDTO.getF12();
    }

    private String changeCnCode(String code) {
        if (code.startsWith("30")) {
            if (code.startsWith("301")) {
                return "sz" + code;
            } else {
                return "sz" + code;
            }
        } else if (code.startsWith("60")) {
            return "sh" + code;
        } else {
            return "sz" + code;
        }
    }


    @Override
    public void monitorChg(List<MonitorChgRequest> monitorChgRequests) {
        stringRedisTemplate.delete("DC:CC");
        monitorChgRequests.forEach(request -> {
            stringRedisTemplate.opsForList().leftPush("DC:CC", JSON.toJSONString(request));
        });
    }

    @Override
    public void monitorLogic() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        List<DcPrice> closePrice = dcPriceMapper.selectList(new QueryWrapper<DcPrice>().isNull("close_price"));
        closePrice.forEach(dcPrice -> {
            String url = MonitorConstant.CODE_CONSTANT.DAY_PRICE +dcPrice.getPrefix()+"."+ dcPrice.getCode() + "&_" + (System.currentTimeMillis() / 1000);
            HttpGet httpGet = new HttpGet(url);
            try {
                // 3. 执行GET请求
                CloseableHttpResponse response = httpClient.execute(httpGet);
                // 4. 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    DayPriceDTO dayPriceDTO = JSON.parseObject(new JSONObject(EntityUtils.toString(entity, "UTF-8")).get("data").toString(), DayPriceDTO.class);
                    dcPrice.setClosePrice(dayPriceDTO.getF43());
                }
            } catch (Exception e) {
                log.error("Exception:{}", e);
            }
            dcPriceMapper.updateById(dcPrice);
        });
    }

    @Override
    public void monitorLogicAmount() {
        List<DcPrice> closePriceList = dcPriceMapper.selectList(new QueryWrapper<DcPrice>().isNull("income_amount"));
        closePriceList.forEach(dcPrice -> {
            BigDecimal nowPrice = new BigDecimal(dcPrice.getNowPrice());
            BigDecimal closePrice = new BigDecimal(dcPrice.getClosePrice());
            BigDecimal divide = new BigDecimal(100000).divide(nowPrice, 0, RoundingMode.HALF_DOWN);
            BigDecimal now = nowPrice.multiply(divide);
            BigDecimal close = closePrice.multiply(divide);
            dcPrice.setIncomeAmount(close.subtract(now).toString());
            dcPriceMapper.updateById(dcPrice);
        });
    }

    private String changeCode(MonitorChgDTO monitorChgDTO) {
        return monitorChgDTO.getF13() + "." + monitorChgDTO.getF12();
    }

    @Override
    public List<MonitorChgDTO> monitor(MonitorChgRequest request) {
        List<String> monitorRange = stringRedisTemplate.opsForList().range("DC:CC", 0, -1);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        for (int i = 0; i < monitorRange.size(); i++) {
            String range = monitorRange.get(i);
            MonitorChgRequest monitorChgRequest = JSON.parseObject(range, MonitorChgRequest.class);
            String url = MonitorConstant.CODE_CONSTANT.DAY_PRICE + monitorChgRequest.getCode() + "&_" + (System.currentTimeMillis() / 1000);
            HttpGet httpGet = new HttpGet(url);
            try {
                // 3. 执行GET请求
                CloseableHttpResponse response = httpClient.execute(httpGet);
                // 4. 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    DayPriceDTO dayPriceDTO = JSON.parseObject(new JSONObject(EntityUtils.toString(entity, "UTF-8")).get("data").toString(), DayPriceDTO.class);
                    changeDayMonitorChg(dayPriceDTO, monitorChgRequest, httpClient);
                }
            } catch (Exception e) {
                log.error("Exception:{}", e);
            }

        }

        return Collections.emptyList();
    }

    private void changeDayMonitorChg(DayPriceDTO dayPriceDTO, MonitorChgRequest dabRequest, CloseableHttpClient httpClient) {
        //成本
        BigDecimal cost = dabRequest.getCost();
        //最新价格
        BigDecimal nowPrice = new BigDecimal(dayPriceDTO.getF43());
        if (nowPrice.compareTo(cost) > 0) {
            //1、高于持仓价  0 设置提醒为1
            changePriceFlag("1", "0", "DC持仓+ " + dayPriceDTO.getF58(), dayPriceDTO, dabRequest, httpClient);
        } else {
            //1、低于持仓价  0 设置提醒为0
            changePriceFlag("0", "1", "DC持仓-" + dayPriceDTO.getF58(), dayPriceDTO, dabRequest, httpClient);
        }
        //排除新股
        if (StringUtils.isNotEmpty(dayPriceDTO.getF51()) && !dayPriceDTO.getF51().equals("-")) {
            //涨停价格
            BigDecimal limitUpPrice = new BigDecimal(dayPriceDTO.getF51());
            //涨停价格
            BigDecimal limitDownPrice = new BigDecimal(dayPriceDTO.getF52());
            if (nowPrice.compareTo(limitUpPrice) == 0) {
                //3、涨停监控
                changeMaxBuyNumber(dabRequest, dayPriceDTO, httpClient);
            } else if (nowPrice.compareTo(limitDownPrice) == 0) {
                //4、跌停监控

            } else {
                //是否触发过涨停、跌停

                //其他 暂时不处理
                //交易量异常

            }
        }


    }

    private void changeMaxBuyNumber(MonitorChgRequest dabRequest, DayPriceDTO dayPriceDTO, CloseableHttpClient httpClient) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(DC_CC_PRICE_ZT + dayPriceDTO.getF57()))) {
            String dbNumber = stringRedisTemplate.opsForValue().get(DC_CC_PRICE_ZT + dayPriceDTO.getF57());
            BigDecimal dbNumberL = new BigDecimal(dbNumber);
            BigDecimal nowNumberL = new BigDecimal(dayPriceDTO.getF20());
            if (dbNumberL.compareTo(nowNumberL) > 0) {
                //买1量减少 减少 30% 减少 50% 减少 80%
                BigDecimal priceLass80 = dbNumberL.multiply(BigDecimal.valueOf(0.2));
                BigDecimal priceLass50 = dbNumberL.multiply(BigDecimal.valueOf(0.5));
                BigDecimal priceLass30 = dbNumberL.multiply(BigDecimal.valueOf(0.7));
                if (priceLass80.compareTo(nowNumberL) > 0) {
                    if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(DC_CC_PRICE_ZT_80_FLAG + dayPriceDTO.getF57()))) {
                        stringRedisTemplate.opsForValue().set(DC_CC_PRICE_ZT_80_FLAG + dayPriceDTO.getF57(), "1", 8, TimeUnit.HOURS);
                        //发送消息 交易量变大
                        MessageTemplateCard messageTemplateCard = changeMessage("DC持仓涨停异常20", changeProfitLossAmount(dabRequest, dayPriceDTO), changeCnCode(dayPriceDTO.getF57()), dabRequest.getCode());
                        messageService.sendMessage(messageTemplateCard, httpClient, MonitorConstant.WE_CHAT_CONSTANT.HOLD);
                    }
                } else if (priceLass50.compareTo(nowNumberL) > 0) {
                    if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(DC_CC_PRICE_ZT_50_FLAG + dayPriceDTO.getF57()))) {
                        stringRedisTemplate.opsForValue().set(DC_CC_PRICE_ZT_50_FLAG + dayPriceDTO.getF57(), "1", 8, TimeUnit.HOURS);
                        //发送消息 交易量变大
                        MessageTemplateCard messageTemplateCard = changeMessage("DC持仓涨停异常50", changeProfitLossAmount(dabRequest, dayPriceDTO), changeCnCode(dayPriceDTO.getF57()), dabRequest.getCode());
                        messageService.sendMessage(messageTemplateCard, httpClient, MonitorConstant.WE_CHAT_CONSTANT.HOLD);
                    }
                } else if (priceLass30.compareTo(nowNumberL) > 0) {
                    if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(DC_CC_PRICE_ZT_30_FLAG + dayPriceDTO.getF57()))) {
                        stringRedisTemplate.opsForValue().set(DC_CC_PRICE_ZT_30_FLAG + dayPriceDTO.getF57(), "1", 8, TimeUnit.HOURS);
                        //发送消息 交易量变大
                        MessageTemplateCard messageTemplateCard = changeMessage("DC持仓涨停异常70", changeProfitLossAmount(dabRequest, dayPriceDTO), changeCnCode(dayPriceDTO.getF57()), dabRequest.getCode());
                        messageService.sendMessage(messageTemplateCard, httpClient, MonitorConstant.WE_CHAT_CONSTANT.HOLD);
                    }
                }
            } else {
                //买1量增加
                stringRedisTemplate.opsForValue().set(DC_CC_PRICE_ZT + dayPriceDTO.getF57(), dayPriceDTO.getF20(), 8, TimeUnit.HOURS);
            }
        } else {
            stringRedisTemplate.opsForValue().set(DC_CC_PRICE_ZT + dayPriceDTO.getF57(), dayPriceDTO.getF20(), 8, TimeUnit.HOURS);
            //发送消息 触发涨停
            MessageTemplateCard messageTemplateCard = changeMessage("DC持仓涨停", changeProfitLossAmount(dabRequest, dayPriceDTO), changeCnCode(dayPriceDTO.getF57()), dabRequest.getCode());
            messageService.sendMessage(messageTemplateCard, httpClient, MonitorConstant.WE_CHAT_CONSTANT.HOLD);
        }
    }

    private void changePriceFlag(String number, String remind, String message, DayPriceDTO dayPriceDTO, MonitorChgRequest dabRequest, CloseableHttpClient httpClient) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(MonitorConstant.CACHE_CDDE.DC_CC_PRICE_FLAG + dayPriceDTO.getF57()))) {
            String flag = stringRedisTemplate.opsForValue().get(MonitorConstant.CACHE_CDDE.DC_CC_PRICE_FLAG + dayPriceDTO.getF57());
            if (Objects.equals(flag, remind)) {
                stringRedisTemplate.opsForValue().set(MonitorConstant.CACHE_CDDE.DC_CC_PRICE_FLAG + dayPriceDTO.getF57(), number, 8, TimeUnit.HOURS);
                MessageTemplateCard messageTemplateCard = changeMessage(message, changeProfitLossAmount(dabRequest, dayPriceDTO), changeCnCode(dayPriceDTO.getF57()), dabRequest.getCode());
                messageService.sendMessage(messageTemplateCard, httpClient, MonitorConstant.WE_CHAT_CONSTANT.HOLD);
            }
        } else {
            stringRedisTemplate.opsForValue().set(MonitorConstant.CACHE_CDDE.DC_CC_PRICE_FLAG + dayPriceDTO.getF57(), number, 8, TimeUnit.HOURS);
            MessageTemplateCard messageTemplateCard = changeMessage(message, changeProfitLossAmount(dabRequest, dayPriceDTO), changeCnCode(dayPriceDTO.getF57()), dabRequest.getCode());
            messageService.sendMessage(messageTemplateCard, httpClient, MonitorConstant.WE_CHAT_CONSTANT.HOLD);
        }
    }

    private String changeProfitLossAmount(MonitorChgRequest dabRequest, DayPriceDTO dayPriceDTO) {
        return "代码 : " + dayPriceDTO.getF57() + " \t名称 : " + dayPriceDTO.getF58()
                + "\n\n当前价: " + new BigDecimal(dayPriceDTO.getF43()).setScale(2, RoundingMode.HALF_DOWN) + "元\t涨跌幅: " + dayPriceDTO.getF170() + "%"
                + "\n\n成本价: " + dabRequest.getCost() + "元\t数量 : " + dabRequest.getNumber()
                + "\n\n盈亏价: " + new BigDecimal(dayPriceDTO.getF43()).subtract(dabRequest.getCost()) + "元\t盈亏额: " + changeProfitLossText(dabRequest, dayPriceDTO)
                + "\n\n买一价: " + dayPriceDTO.getF19() + "元\t买一量: " + dayPriceDTO.getF20()
                + "\n\n主力流入:" + (new BigDecimal(dayPriceDTO.getF137()).divide(new BigDecimal(100000000), 2, RoundingMode.HALF_DOWN)) + "亿\t行业 : " + dayPriceDTO.getF127();
    }

    private String changeProfitLossText(MonitorChgRequest dabRequest, DayPriceDTO dayPriceDTO) {
        BigDecimal cost = dabRequest.getCost();
        Integer number = dabRequest.getNumber();
        BigDecimal nowPrice = new BigDecimal(dayPriceDTO.getF43());
        BigDecimal costAmount = cost.multiply(BigDecimal.valueOf(number));
        BigDecimal nowAmount = nowPrice.multiply(BigDecimal.valueOf(number));
        return nowAmount.subtract(costAmount).toString();
    }


    @Override
    public List<MonitorChgDTO> monitorTemplate() {
        return Collections.emptyList();
    }
}
