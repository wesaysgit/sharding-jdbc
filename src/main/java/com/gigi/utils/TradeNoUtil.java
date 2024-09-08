package com.gigi.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.Date;

public class TradeNoUtil {

    public static String getTradeNo() {
        String dateFormat = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN);
        return dateFormat + RandomUtil.randomNumbers(3);
    }
}
