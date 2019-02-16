package com.mohsal.dkvs.registry;

/**
 * Author: Mohamed Saleem
 */

import com.mohsal.dkvs.Server;
import com.mohsal.dkvs.exception.InvalidServerArgumentsException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A Registry maintains the list of active servers in the cluster.
 * Servers register with the registry when they start up.
 * A hearbeat message is sent out to all the servers in the cluster to check if they are up or not.
 * The servers which do not acknowledge the heartbeat message within a certain period of time are removed from the registry
 */
public class Registry {

    private static final String HOST = "host";
    private static final String PORT = "port";

    private String host;
    private int port;

    /**
     * Servers in the cluster.
     */
    public static ConcurrentHashMap<String, Boolean> serversMap = new ConcurrentHashMap<>();

    /**
     * Registry properties.
     */
    private Properties properties;

    private ServerSocket serverSocket = null;

    Registry(Properties properties) {
        this.properties = properties;
    }

    public void initialize() throws InvalidServerArgumentsException, IOException {
        System.out.println("Initializing...");
        System.out.println("Properties: " + this.properties);

        if(properties.getProperty(HOST).isEmpty() || properties.getProperty(PORT).isEmpty()) {
            throw new InvalidServerArgumentsException();
        }
    }

    public void listen() throws IOException {
        System.out.println("Listening...");
        host = properties.getProperty(HOST);
        port = Integer.valueOf(properties.getProperty(PORT));

        serverSocket = new ServerSocket(port);

        while (true) {
            Socket socket = serverSocket.accept();
            try {
                System.out.println("New connection: " + socket);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                Thread thread = new ClientHandler(dataInputStream, dataOutputStream, socket);
                thread.start();

            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }

        }
    }
}
