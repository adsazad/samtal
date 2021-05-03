/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sarabit.server;

import java.io.IOException;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author arashdeep
 */
public class CustomSocket {

    public SSLSocket getSSLSocket(String host) throws IOException {
        SSLSocketFactory factory
                = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket
                = (SSLSocket) factory.createSocket(host, 443);
        
        return socket;
    }
    
    public Socket getSocket(String host) throws IOException {
        Socket socket = new Socket(host, 80);
        return socket;
    }
}
