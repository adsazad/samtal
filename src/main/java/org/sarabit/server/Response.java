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
    private Map<String, String> header = new HashMap<>();

    public Response(InputStream inputStream) throws IOException {
        InputStreamReader inStreamReader = new InputStreamReader(inputStream);
        BufferedReader inBufferedReader = new BufferedReader(inStreamReader);
        String line = "";
        int lineon = 1;
        boolean isReadContent = false;
        while ((line = inBufferedReader.readLine()) != null) {
            if (isReadContent == true) {
                if (line.length() > 0) {
                    this.content = this.content + line + "\r\n";
                }
            } else {
                if (line.length() > 0) {
                    this.readRequestLine(line, lineon);
                } else {
                    isReadContent = true;
                    if (line.length() > 0) {
                        this.content = this.content + line + "\r\n";
                    }
                }
            }
            lineon++;
        }
    }

    private void readRequestLine(String line, int linenumber) {
        if (linenumber == 1) {
            String[] lineone = line.split(" ", 2);
            this.protocol = lineone[0];
            this.status = lineone[1];
            String[] statusAr = status.split(" ");
            this.statusCode = Integer.parseInt(statusAr[0]);
        } else {
            String[] otlines = line.split(":", 2);
            header.put(otlines[0].trim(), otlines[1].trim());
        }
        this.headerString = this.headerString + line + "\r\n";
    }

    public Map<String, String> getHeaders() {
        return this.header;
    }

    public String getContent() {
        return this.content.trim();
    }

    public String getHeaderString() {
        String header = this.getProtocol() + " " + this.getStatus() + "\r\n";
        for (Map.Entry<String, String> headvars : this.header.entrySet()) {
            String key = headvars.getKey();
            String val = headvars.getValue();
            if (!key.equals("Transfer-Encoding")) {
                header = header + key + ": " + val + "\r\n";
            }
        }
        return header;
    }

    public String getCompleteResponse() {
        return this.getHeaderString()+"\r\n" + this.content;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getStatusCodel() {
        return this.statusCode;
    }

}
