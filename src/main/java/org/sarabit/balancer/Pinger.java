/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sarabit.balancer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.sarabit.loader.ConfigurationLoader;
import org.sarabit.loader.Proxy;

/**
 *
 * @author arashdeep
 */
public class Pinger {

    public Proxy getFastestProxy() throws FileNotFoundException {
        ConfigurationLoader configurationLoader = new ConfigurationLoader();
        Map<String, Proxy> proxies = configurationLoader.getProxies();
        Map<String, Long> responseTimes = new HashMap<>();
        for (Map.Entry<String, Proxy> p : proxies.entrySet()) {
            String key = p.getKey();
            Proxy val = p.getValue();
            System.out.println(val.getHost());
            InetAddress inetaddr;
            try {
                inetaddr = InetAddress.getByName(val.getHost());

                InetSocketAddress socketAddress = new InetSocketAddress(inetaddr, val.port);

                SocketChannel sc = SocketChannel.open();
                sc.configureBlocking(true);
                long timeToRespond = 0;
                Date start = new Date();
                if (sc.connect(socketAddress)) {
                    Date stop = new Date();
                    timeToRespond = (stop.getTime() - start.getTime());
                }
                responseTimes.put(key, timeToRespond);
            } catch (UnknownHostException ex) {
//                Logger.getLogger(Pinger.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
//                Logger.getLogger(Pinger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return proxies.get(this.getMinValKey(responseTimes));
    }

    public String getMinValKey(Map<String, Long> itemTime) {
        Long smallestValue = Long.MAX_VALUE;
        String smallestKey = "";
        for (HashMap.Entry<String, Long> entry : itemTime.entrySet()) {
            if (entry.getValue() < smallestValue) {
                smallestKey = entry.getKey();
                smallestValue = entry.getValue();
            }
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        return smallestKey;
    }

}
