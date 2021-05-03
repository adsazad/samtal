/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sarabit.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sarabit.loader.Configuration;
import org.sarabit.loader.ConfigurationLoader;

/**
 *
 * @author Arashdeep Singh
 */
public class Server implements Runnable {

    ServerSocket serverSocket;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        try {

            while (true) {
                Socket s = serverSocket.accept();
                Request request = new Request(s.getInputStream());
                System.out.println(request.getMethod());
//                RequestReader request = new RequestReader(s.getInputStream(), s.getOutputStream());
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
}
