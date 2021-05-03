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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author arashdeep
 */
public class RequestReader {

    String requestStr;

    public RequestReader(InputStream inputStream, OutputStream outputStream) throws IOException {
        InputStreamReader inStreamReader = new InputStreamReader(inputStream);
        BufferedReader inBufferedReader = new BufferedReader(inStreamReader);
        String line;
        String backHost = this.getBackHost().get("host");
        String backPort = this.getBackHost().get("port");
        CustomSocket cs = new CustomSocket();
        Socket s = cs.getSocket(backHost);
        PrintWriter pw = new PrintWriter(s.getOutputStream());
//        s.startHandshake();
//        String request = "";
        while ((line = inBufferedReader.readLine()) != null) {
            if (line.length() > 0) {
                if (line.startsWith("Host:")) {
                    line = "Host: bjsint.com";
                }
                line = line + "\r\n";
                pw.print(line);
                System.err.println(line);
//                request = request + line;
            } else {
                break;
            }
        }
        pw.print("\r\n");
        pw.flush();

//        s.getOutputStream().write(request.getBytes("UTF-8"));
//        System.out.println(request);
        InputStreamReader isr = new InputStreamReader(s.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        String line2 = "";
        PrintWriter pw2 = new PrintWriter(outputStream);
        while ((line2 = reader.readLine()) != null) {
                System.out.println(line2);
                pw2.println(line2);
//                line2 = reader.readLine();
        }
        pw2.println("");
        pw2.flush();
        System.out.println("end");
        s.close();
        inputStream.close();
        outputStream.close();
    }

    public String toString() {
        return this.requestStr;
    }

    private Map<String, String> getBackHost() {
        Map<String, String> map = new HashMap<>();
        map.put("host", "bjsint.com");
        map.put("port", "80");
        return map;
    }

}
