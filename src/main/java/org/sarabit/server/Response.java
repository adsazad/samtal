/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sarabit.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.sarabit.utils.Commons;

/**
 *
 * @author arashdeep
 */
public class Response {

    private String protocol;
    private int statusCode;
    private String status;
    private String content = "";
    private String headerString = "";
    private String response;
    private String contentType;
    private Map<String, String> header = new HashMap<>();

    public Response(InputStream inputStream) throws IOException {
        InputStreamReader inStreamReader = new InputStreamReader(inputStream);
        BufferedReader inBufferedReader = new BufferedReader(inStreamReader);
        Commons common = new Commons();
        this.response = common.InputStreamToString(inputStream);
        this.protocol = common.extractResponseProtocol(this.response);
        this.statusCode = Integer.parseInt(common.extractResponseCode(this.response));
        this.status = common.extractResponseStatus(this.response);
        this.header = common.getHeaders(this.response);
        this.content = common.getHttpContent(this.response);
    }

    public String getContent() {
        return this.content.trim();
    }

    public String getCompleteResponse() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getProtocol() + " " + this.getStatusCode() + " " + this.getStatus() + "\r\n");
        for (Map.Entry<String, String> h : header.entrySet()) {
            String key = h.getKey();
            String val = h.getValue();
            if (key.equals("Content-Type")) {
                this.contentType = val;
            }
            if (key.equals("Content-Length")) {
                val = Integer.toString(this.content.length());
            }
            if (!key.equals("Transfer-Encoding")) {
                sb.append(key + ": " + val + "\r\n");
            }
        }
        if (!this.getContent().isEmpty()) {
            sb.append("\r\n");
            sb.append(this.getContent());
        }
        sb.append("\r\n");

        return sb.toString();
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

}
