package org.sarabit.loader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author arashdeep
 */
public class ConfigurationLoader {

    public Configuration getConfiguration() throws FileNotFoundException {
        Path configFile = Paths.get("configuration.yml");
        Constructor constructor = new Constructor(Configuration.class);
        Yaml yaml = new Yaml(constructor);
        Configuration conf = (Configuration) yaml.load(new FileInputStream(configFile.toFile()));
        return conf;
    }

    public Map<String, Proxy> getProxies() throws FileNotFoundException {
        Configuration conf = this.getConfiguration();
        Map<String, Proxy> proxies = conf.getProxies();
        return proxies;
    }

}
