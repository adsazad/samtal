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
public class Request {

    private String method;
    private String path;
    private String protocol;
    private Map<String, String> headers = new HashMap<>();
    private String request = "";

    public Request(InputStream inputStream) throws IOException {
        InputStreamReader inStreamReader = new InputStreamReader(inputStream);
        BufferedReader inBufferedReader = new BufferedReader(inStreamReader);
        Commons common = new Commons();
        String request = common.BufferReaderToString(inBufferedReader);
        this.request = request;
        this.setMethod(common.extractRequestMethod(request));
        this.setPath(common.extractRequestPath(request));
        this.setProtocol(common.extractRequestProtocol(request));
        this.headers = common.getHeaders(request);
    }

    public String getRequest() {
        return this.request;
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
}
