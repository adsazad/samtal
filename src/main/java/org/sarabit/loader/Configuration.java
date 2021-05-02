/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sarabit.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arashdeep
 */
public class Configuration {

    public String nodename;
    public int port;
    public Map<String, Proxie> proxies = new HashMap<>();

    public String getNodename() {
        return this.nodename;
    }

    public Map<String, Proxie> getProxies() {
        return this.proxies;
    }

    public int getPost() {
        return port;
    }

}
