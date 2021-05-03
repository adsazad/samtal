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
public class Request {

    private String method;
    private String path;
    private String protocol;
    private Map<String, String> additionalVars = new HashMap<>();

    public Request(InputStream inputStream) throws IOException {
        InputStreamReader inStreamReader = new InputStreamReader(inputStream);
        BufferedReader inBufferedReader = new BufferedReader(inStreamReader);
        String line;
        String backHost = this.getBackHost().get("host");
        String backPort = this.getBackHost().get("port");
        int lineon = 1;
        while ((line = inBufferedReader.readLine()) != null) {
            if (line.length() > 0) {
                this.readRequestLine(line, lineon);
            } else {
                break;
            }
            lineon++;
        }
    }

    private void readRequestLine(String line, int linenumber) {
        if (linenumber == 1) {
            String[] lineone = line.split(" ", 3);
            this.setMethod(lineone[0].trim());
            this.setPath(lineone[1].trim());
            this.setProtocol(lineone[2].trim());
        } else {
            String[] otlines = line.split(":", 2);
            additionalVars.put(otlines[0].trim(), otlines[1].trim());
        }
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getProtocol() {
        return this.protocol;
    }

    private void setMethod(String method) {
        this.method = method;
    }

    private void setPath(String path) {
        this.path = path;
    }

    private void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    private Map<String, String> getBackHost() {
        Map<String, String> map = new HashMap<>();
        map.put("host", "bjsint.com");
        map.put("port", "80");
        return map;
    }

}
