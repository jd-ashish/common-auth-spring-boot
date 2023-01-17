package com.projects.app.commons.utils;

import java.util.HashMap;
import java.util.Map;

import static com.projects.app.commons.constants.AppConstants.OTPLength;

public class Utils {

    public static String OTP(){
        String OTP = "1234567890";
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<OTPLength; i++){
            int index = (int) (Math.random() * OTP.length());
            sb.append(OTP.charAt(index));
        }
        return sb.toString();
    }

    public static Map<String, String> ByteToMegabyte(String  bytes, double max_kb) {
        double kilobytes = (Double.parseDouble(bytes) / 1024);
        double megabytes = (kilobytes / 1024);
        double gigabytes = (megabytes / 1024);
        Map<String,String> map = new HashMap<>();
        map.put("kb", String.valueOf(kilobytes));
        map.put("mb", String.valueOf(megabytes));
        map.put("gb", String.valueOf(gigabytes));
        System.out.println();

        if(max_kb==0 || kilobytes<=max_kb){
            map.put("message",null);
        }else{
            map.put("message","Maximum file size is " + max_kb+" KB or "+ (max_kb/1024)+" MB");
        }
        return map;

    }

}
