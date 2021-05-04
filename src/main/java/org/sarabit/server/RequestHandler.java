/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sarabit.server;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arashdeep
 */
public class RequestHandler implements Runnable {

    private Request request;
    private Socket clientSocket;
    private Socket backSocket;

    public RequestHandler(Request request, Socket clientSocket) throws IOException {
        this.request = request;
        this.clientSocket = clientSocket;
    }

    public void getBackSocket() {

    }

    public void handle() throws IOException {
        String response = this.getResponse();
        System.out.println(response);
        this.writeOutResponse(response, clientSocket);
    }

    public void writeOutResponse(String data, Socket clientSocket) throws IOException {
//        OutputStream dout = this.clientSocket.getOutputStream();
//        FileOutputStream fout = new FileOutputStream(new File("./test.txt"));
//        fout.write(data.getBytes(), 0, data.length());
//        fout.flush();
        PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
        pw.print(data);
        pw.print("\r\n");
        pw.flush();
    }

    public String getResponse() throws IOException {
        this.backSocket = new Socket("demo.shopping.sarabit.com", 80);
        String requeststr = this.request.getRequest();
        System.out.println(requeststr);
        PrintWriter pw = new PrintWriter(backSocket.getOutputStream());
        pw.print(requeststr);
        pw.print("\r\n");
        pw.flush();
        Response response = new Response(backSocket.getInputStream());
        return response.getCompleteResponse();
    }

    public void close() {
        try {
            this.clientSocket.close();
            this.backSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            this.handle();
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.close();
    }


}
