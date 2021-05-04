/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sarabit.balancer;

import java.io.FileNotFoundException;
import org.sarabit.loader.ConfigurationLoader;
import org.sarabit.loader.Configuration;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sarabit.loader.Proxy;
import org.sarabit.server.Server;

/**
 *
 * @author Arashdeep Singh
 */
public class Main {

    public static void main(String[] args) {
        Pinger pinger = new Pinger();
        //            pinger.getFastestProxy();
        Thread server = new Thread(new Server(8000));
        server.run();
    }
}
