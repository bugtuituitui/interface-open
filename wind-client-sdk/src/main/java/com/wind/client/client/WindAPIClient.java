package com.wind.client.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wind.client.utils.SignatureUtils;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author kfg
 * @date 2023/6/8 12:12
 */
@Data
public class WindAPIClient {

    private static final String HOST = "http://127.0.0.1:9002";
    private String apiKey = "12312";
    private String apiSecret = "123123";

    public WindAPIClient(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    /**
     * 构建请求头
     *
     * @return
     */
    private Map<String, String> createHeader(String body) throws NoSuchAlgorithmException {

        HashMap<String, String> map = new HashMap<>();
        map.put("apiKey", apiKey);
        map.put("apiSecret", apiSecret);

        long timestamp = System.currentTimeMillis();
        String nonce = RandomUtil.randomNumbers(5);

        // 时间戳
        map.put("timestamp", String.valueOf(timestamp));
        // 流水号
        map.put("nonce", nonce);

        // 拼接参数
        String params = SignatureUtils.getKeyAndValueStr(getParams(body));
        params = StringUtils.isEmpty(params) ? "" : "&" + params + "";

        map.put("signature", SignatureUtils.generateSignature(apiKey, apiSecret, params, timestamp, Integer.parseInt(nonce)));
        return map;
    }

    /**
     * 解析参数
     *
     * @param body
     * @return
     */
    private Map<String, Object> getParams(String body) {
        JSONObject json = JSON.parseObject(body);

        // 保证参数有序
        TreeMap<String, Object> map = new TreeMap<>();

        Set<Map.Entry<String, Object>> set = json.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();

        while (it.hasNext()) {
            Map.Entry e = it.next();
            map.put(e.getKey().toString(), e.getValue());
        }

        return map;
    }

    /**
     * 发起请求
     *
     * @param body
     * @return
     */
    public String sendRequest(String body, String api) throws NoSuchAlgorithmException {

        HttpResponse response = HttpRequest.post(HOST + api)
                .addHeaders(createHeader(body))
                .form(getParams(body))
                .execute();

        return response.body();
    }

//    public static void main(String[] args) throws NoSuchAlgorithmException {
//
//
//        JSONObject json = new JSONObject();
//        json.put("address", "shandong");
//
//        String result = sendRequest(json.toJSONString(), "/apis/getWeather");
//        System.out.println(result);
//    }
}
