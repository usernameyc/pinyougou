package com.pinyougou.service;

import java.util.Map;

public interface WeixinPayService {

    Map<String,String> genPayCode(String outTradeNo, String totalFee);

    Map<String,String> queryPayStatus(String outTradeNo);
}
