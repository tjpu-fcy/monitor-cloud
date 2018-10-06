package com.protocol;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class test1 {

    private static  Map<String, String > nameMap = new LinkedHashMap<String, String>();

    static{
        nameMap.put("1","温度");
        nameMap.put("2","湿度");

    }
    private  Map<String, String > ManageMap = new ConcurrentHashMap<String, String>();



    public byte[][]  send() {
        byte[][] bytes = {
                {(byte) 0x01, (byte) 0x03, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0x45, (byte) 0x78},
                {(byte) 0x01, (byte) 0x03, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0x45, (byte) 0x78}


        } ;

        return  bytes;
    }

    public void receive() {

    }

    private byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

}
