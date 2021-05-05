/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sarabit.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author arashdeep
 */
public class Commons {

    public String BufferReaderToString(BufferedReader bi) throws IOException {
        StringBuffer sb = new StringBuffer();
        int i;
        while ((i = bi.read()) != -1) {
            if (!bi.ready()) {
                break;
            }
            char c = (char) i;
            sb.append(c);

        }
//        bi.close();
        return sb.toString();
    }

    public String InputStreamToString(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        DataInputStream din = new DataInputStream(is);
        int i;
        
        while ((i = din.read()) != -1) {
//            if (!is.ready()) {
//                break;
//            }
            char c = (char) i;
            sb.append(c);

        }
//        bi.close();
        return sb.toString();
    }

    //    Only for Request
    public String extractRequestMethod(String request) {
        Pattern pattern = Pattern.compile("(?:^|(?:[.!?]\\s))(\\w+)");
        Matcher matcher = pattern.matcher(request);
        while (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    //    Only for Request
    public String extractRequestPath(String request) {
        String head = this.extractMainHead(request);
        String[] headspl = head.split(" ", 3);
        return headspl[1];
    }

    //    Only for Request
    public String extractRequestProtocol(String request) {
        String head = this.extractMainHead(request);
        String[] headspl = head.split(" ", 3);
        return headspl[2];
    }

    //    Only For Response
    public String extractResponseProtocol(String request) {
        String head = this.extractMainHead(request);
        String[] headspl = head.split(" ", 3);
        return headspl[0];
    }

    //    Only For Response
    public String extractResponseCode(String request) {
        String head = this.extractMainHead(request);
        String[] headspl = head.split(" ", 3);
        return headspl[1];
    }

    //    Only For Response
    public String extractResponseStatus(String request) {
        String head = this.extractMainHead(request);
        String[] headspl = head.split(" ", 3);
        return headspl[2];
    }

    //    Common for both request and response
    public String extractMainHead(String request) {
        Pattern pattern = Pattern.compile("(.+)");
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    //    Common for both request and response
    public String getHttpContent(String request) {
        String[] stspl = request.split("(?<=\r\n)(\r\n)", 2);
        int len = stspl.length;
        if (len == 1) {
            return "";
        }
        return stspl[1];
    }

    public String getHttpHead(String request) {
        String[] stspl = request.split("(?<=\r\n)(\r\n)", 2);
        int len = stspl.length;
        if (len == 1) {
            return request;
        }
        return stspl[0];
    }

    //    Common for both request and response
    public Map<String, String> getHeaders(String request) {
        Pattern pattern = Pattern.compile("([ -a-zA-z0-9])+: ([ -a-zA-z0-9])+");
        Matcher matcher = pattern.matcher(this.getHttpHead(request));
        Map<String, String> map = new HashMap<>();
        while (matcher.find()) {
            String head = matcher.group(0);
            String[] headSpl = head.split(":", 2);
            map.put(headSpl[0], headSpl[1]);
        }
        return map;
    }

}
