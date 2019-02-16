package com.mohsal.dkvs.registry;


import com.mohsal.dkvs.exception.InvalidServerArgumentsException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Author: Mohamed Saleem
 */
public class RegistryApplication {
    /**
     * Properties file name.
     */
    private static final String PROPERTIES_FILE = "registry.properties";

    public static void main(String args[]) {
        try {

            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Properties properties = new Properties();
            InputStream inputStream = loader.getResourceAsStream(PROPERTIES_FILE);
            properties.load(inputStream);

            Registry registry = new Registry(properties);
            registry.initialize();
            registry.listen();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidServerArgumentsException e) {
            e.printStackTrace();
        }
    }
}
