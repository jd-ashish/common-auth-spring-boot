package com.projects.app.commons.utils;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.projects.app.commons.constants.AppConstants.EncodeDecodeTimes;

public class EncodeDecode {

    public static String encode(String data) {
        String loop_data = data;
        for(int i = 0; i < EncodeDecodeTimes; i++){
            loop_data = commonEncode(loop_data);
        }
        return loop_data;
    }

    protected static String commonEncode(String data){
        byte[] bytesEncoded = Base64.getEncoder().encode(data.getBytes());
        return new String(bytesEncoded, StandardCharsets.ISO_8859_1);
    }

    protected static String commonDecode(String data){
        byte[] valueDecoded = Base64.getDecoder().decode(data);
        return new String(valueDecoded,StandardCharsets.ISO_8859_1);
    }
    public static String decode(String data) {
        String loop_data = data;
        for(int i = 0; i < EncodeDecodeTimes*2; i++){
            loop_data = commonDecode(loop_data);
        }
        return loop_data;
    }
}
