package com.mig.data.service.impl;

import com.mig.data.entity.PriceRecord;
import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: dongbx@dianzhong.com
 * @createDate: 2024-12-10 10:25
 */
public class StockPriceFetcher {
    private static final OkHttpClient client = new OkHttpClient();
    private static final Map<String, Deque<PriceRecord>> priceCache = new ConcurrentHashMap<>();
    private static final long CACHE_EXPIRY_TIME = 60_000; // 1 minute in milliseconds
    private static final Logger log = LoggerFactory.getLogger(StockPriceFetcher.class);
    private static final Map<String, Double> transactionCache = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    public static void main(String[] args) {
        // 定时任务每1.5秒查询一次接口
        scheduler.scheduleAtFixedRate(StockPriceFetcher::fetchDataIfInTimeWindow, 0, 1500, TimeUnit.MILLISECONDS);
    }

    private static void fetchDataIfInTimeWindow() {
        if(shouldExecute()){
            fetchData();
        }
    }
    private static boolean shouldExecute() {
        // 获取当前时间
        LocalTime now = LocalTime.now();

        // 定义每天的执行时间段
        LocalTime morningStart = LocalTime.of(9, 30); // 9:30
        LocalTime morningEnd = LocalTime.of(11, 30); // 11:30
        LocalTime afternoonStart = LocalTime.of(13, 0); // 13:00
        LocalTime afternoonEnd = LocalTime.of(15, 0); // 15:00

        // 判断当前时间是否在指定时间段内
        return (now.isAfter(morningStart) && now.isBefore(morningEnd)) ||
                (now.isAfter(afternoonStart) && now.isBefore(afternoonEnd));
    }
    private static void transaction(){
            transactionCache.forEach((k,v)->{
                Deque<PriceRecord> priceRecords = priceCache.get(k);
                if (priceRecords != null && priceRecords.size() > 1) {
                    // 计算涨幅
                    double firstPrice = priceRecords.peekFirst().price;
                    double lastPrice = priceRecords.peekLast().price;
                    double priceChangeF = (lastPrice - firstPrice) / firstPrice * 100;
                    //卖掉
                    double wareChangeF = (lastPrice - v) / v * 100;

                    // 格式化打印输出
                    System.out.printf(
                            "持仓编码: %-10s 持仓单价: %-10.2f 1分钟前单价: %-10.2f 最新价: %-10.2f 1分钟涨幅: %-6.2f%% 持仓涨幅: %-6.2f%%\n",
                            k, v, firstPrice, lastPrice, priceChangeF, wareChangeF
                    );
                    if(priceChangeF<0.8){
                        System.out.printf(
                                "交易编码: %-10s 单价: %-10.2f 最新价: %-10.2f 涨幅: %-6.2f%%\n",
                                k, v, lastPrice, wareChangeF
                        );
                        transactionCache.remove(k);
                    }
                }
            });
    }


    private static void fetchData() {
        String url = "https://55.push2.eastmoney.com/api/qt/clist/get?cb=jQuery11240049610032218081024_1733620717030&pn=1&pz=50&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&dect=1&wbp2u=|0|0|0|web&fid=f3&fs=b:MK0354&fields=f1,f152,f2,f3,f12,f13,f14,f227,f228,f229,f230,f231,f232,f233,f234,f235,f236,f237,f238,f239,f240,f241,f242,f26,f243&_=1733620717141";

        // 发起请求
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String body = response.body().string();

                // 解析 JSONP 响应，去掉回调函数包裹的部分
                String jsonpData = body.substring(body.indexOf("(") + 1, body.lastIndexOf(")"));
                JSONObject jsonObject = new JSONObject(jsonpData);

                // 获取数据部分
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray items = data.getJSONArray("diff");
                System.out.println(LocalDateTime.now()+" 查询成功，响应数据："+items.length());

                // 打印每条可转债数据
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    String code = item.getString("f12");
                    String name = item.getString("f14");
                    double currentPrice = item.getDouble("f2");
                    double priceChange = item.getDouble("f3");

//                    System.out.println("可转债代码: " + code);
//                    System.out.println("转债名称: " + name);
//                    System.out.println("当前价格: " + currentPrice);
//                    System.out.println("涨跌幅: " + priceChange);

                    // 更新缓存
                    updatePriceCache(code, currentPrice);
                    // 计算1分钟内的涨幅
                    calculatePriceChange(code, name,currentPrice,priceChange);
                }
                transaction();
            } else {
                System.out.println("请求失败: " + response.code());
            }
        } catch (IOException e) {
            log.error("请求过程中发生异常: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("发生未知异常: " + e.getMessage(), e);
        }
    }

    // 更新价格缓存，保留1分钟内的数据
    private static void updatePriceCache(String code, double currentPrice) {
        long currentTime = System.currentTimeMillis();
        Deque<PriceRecord> priceRecords = priceCache.computeIfAbsent(code, k -> new LinkedList<>());

        // 删除超过1分钟的数据
        while (!priceRecords.isEmpty() && currentTime - priceRecords.peekFirst().timestamp > CACHE_EXPIRY_TIME) {
            priceRecords.pollFirst();
        }

        // 将新记录加入队列
        priceRecords.offer(new PriceRecord(currentPrice, currentTime));
    }

    private static void calculatePriceChange(String code, String name, double currentPrice, double priceChange) {
        Deque<PriceRecord> priceRecords = priceCache.get(code);
        if (priceRecords != null && priceRecords.size() > 1) {
            // 计算涨幅
            PriceRecord firstRecord = priceRecords.peekFirst();
            double priceChangeF = (currentPrice - firstRecord.price) / firstRecord.price * 100;
            if(priceChangeF>0.5){
                // 将数据写入到 Excel 中
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                row.createCell(1).setCellValue(code);
                row.createCell(2).setCellValue(name);
                row.createCell(3).setCellValue(currentPrice);
                row.createCell(4).setCellValue(priceChange);
                row.createCell(5).setCellValue(firstRecord.price);
                row.createCell(6).setCellValue(priceChangeF);
                saveToFile();
            }
            if (priceChangeF > 1.5) {
                String message = String.format(
                        "时间：%-20s 编码：%-10s 名称：%-20s 当前价格：%-10.2f 涨幅：%-6.2f%% 1分钟之前价格：%-10.2f 1分钟涨幅：%-6.2f%%\n",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        code, name, currentPrice, priceChange, firstRecord.price, priceChangeF
                );
                System.out.println(message);
                DingTalkMessageService.sendText(message);
                transactionCache.putIfAbsent(code, currentPrice);
            }
        }
    }

    // Excel 文件路径
    private static final String EXCEL_FILE_PATH = "/Users/world/IdeaProjects/dongfengwen/share-certificate-data/price_change_log_"+ LocalDate.now() +".xlsx";

    private static Workbook workbook;
    private static Sheet sheet;
    private static int rowCount = 0; // 记录行数

    static {
        // 加载现有的 Excel 文件，如果文件不存在，则创建一个新的文件
        File file = new File(EXCEL_FILE_PATH);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0); // 获取第一个工作表
                rowCount = sheet.getPhysicalNumberOfRows(); // 获取现有行数
            } catch (IOException e) {
                System.err.println("读取现有 Excel 文件时发生错误: " + e.getMessage());
            }
        } else {
            // 如果文件不存在，创建新的工作簿和工作表
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Price Change Logs");

            // 设置表头
            Row headerRow = sheet.createRow(rowCount++);
            headerRow.createCell(0).setCellValue("时间");
            headerRow.createCell(1).setCellValue("编码");
            headerRow.createCell(2).setCellValue("名称");
            headerRow.createCell(3).setCellValue("当前价格");
            headerRow.createCell(4).setCellValue("涨幅");
            headerRow.createCell(5).setCellValue("1分钟之前价格");
            headerRow.createCell(6).setCellValue("1分钟涨幅");
        }
    }



    // 将 Excel 文件保存到磁盘
    private static void saveToFile() {
        try (FileOutputStream fileOut = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
            workbook.write(fileOut);
        } catch (IOException e) {
            System.err.println("写入 Excel 文件时发生错误: " + e.getMessage());
        }
    }

    // 价格记录类
    @Data
    private static class PriceRecord {
        double price;
        long timestamp;

        PriceRecord(double price, long timestamp) {
            this.price = price;
            this.timestamp = timestamp;
        }
    }

}