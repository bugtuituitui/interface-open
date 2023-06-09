package com.wind.client.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SignatureUtils {

    public static String generateSignature(String apiKey, String apiSecret, String params, long timestamp, int nonce) throws NoSuchAlgorithmException {

        String result = "apiKey=" + apiKey + "&apiSecret=" + apiSecret + params + "&timestamp=" + timestamp + "&nonce=" + nonce;

        System.out.println(result);

        // 计算MD5哈希值
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hashBytes = digest.digest(result.getBytes(StandardCharsets.UTF_8));
        // 将字节数组转换成十六进制字符串
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString().toUpperCase();
    }

    /**
     * 获得k1=v1&k2=v2 字符串
     *
     * @param map
     * @return
     */
    public static String getKeyAndValueStr(Map<String, Object> map) {
        String result = "";
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
                @Override
                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    return (o1.getKey().toString()).compareTo(o2.getKey().toString());
                }
            });
            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey().toString();
                    String val = item.getValue().toString();
                    if (!(val == "" || val == null)) {
                        sb.append(key + "=" + val + "&");
                    }
                }
            }
            result = sb.toString().substring(0, sb.length() - 1);
        } catch (Exception e) {
            return null;
        }
        return result;
    }

}
