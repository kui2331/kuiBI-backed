package com.yupi.springbootinit.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Excel 相关工具类
 */
@Slf4j
public class ExcelUtils {
    public static String excelToCsv(MultipartFile multipartFile) {
//        File file = null;
//        try {
//            file = ResourceUtils.getFile("classpath:网站数据.xlsx");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 读取数据
        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("表格处理错误", e);
        }
        // 如果数据为空
        if (CollUtil.isEmpty(list)) {
            return "";
        }
        // 转换为 csv
        //CSV（Comma-Separated Values）是一种简单的文件格式，用于存储表格数据。CSV文件中，每一行代表一个记录，字段之间用逗号分隔。
        StringBuilder stringBuilder = new StringBuilder();
        // 读取表头(第一行)
        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap) list.get(0);
        List<String> headerList = headerMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
            //System.out.println(StringUtils.join(headerList, ","));
        //StringBuilder 是Java中用于操作字符串的一个类，它的主要作用是高效地进行字符串的拼接。
        //因为它不会创建新的字符串对象，而是通过修改内部的字符数组来实现字符串的拼接。
        stringBuilder.append(StringUtils.join(headerList, ",")).append("\n");
        // 读取数据(读取完表头之后，从第一行开始读取)
        for (int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap) list.get(i);
            List<String> dataList = dataMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
                //System.out.println(StringUtils.join(dataList, ","));
            stringBuilder.append(StringUtils.join(dataList, ",")).append("\n");
        }
            //System.out.println(list);
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        excelToCsv(null);
    }
}
