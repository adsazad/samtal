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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;

/**
 *
 * @author arashdeep
 */
public class RequestHandler implements Runnable {

    private Request request;
    private InputStream clientInputStream;
    private OutputStream clientOutputStream;
    private Socket clientSocket;
    private Socket backSocket;

    public RequestHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientInputStream = this.clientSocket.getInputStream();
        this.clientOutputStream = this.clientSocket.getOutputStream();
        this.request = new Request(this.clientInputStream);
    }

    public void getBackSocket() {

    }

    public void handle() throws IOException {
        String response = this.getResponse();
        System.out.println(response);
        this.writeOutResponse(response);
    }

    public void writeOutResponse(String data) throws IOException {
//        OutputStream dout = this.clientSocket.getOutputStream();
//        FileOutputStream fout = new FileOutputStream(new File("./test.txt"));
//        fout.write(data.getBytes(), 0, data.length());
//        fout.flush();
        PrintWriter pw = new PrintWriter(this.clientOutputStream);
        pw.print(data);
        pw.flush();
    }

    public String getResponse() throws IOException {
        this.backSocket = new Socket(InetAddress.getByName("bjsint.com"), 80);
        String requeststr = this.request.getRequest();
        PrintWriter pw = new PrintWriter(backSocket.getOutputStream());
        pw.print(requeststr);
        pw.flush();
        Response response = new Response(backSocket.getInputStream());
        return response.getCompleteResponse();
    }

    public void close() {
        try {
            System.out.println("Close");
            this.clientInputStream.close();
            this.clientOutputStream.close();
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
