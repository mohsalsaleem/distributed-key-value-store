package com.mohsal.dkvs;


import javafx.util.Pair;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Mohamed Saleem
 * The Server node.
 */
public class Server {

    /**
     * Hostname
     */
    private String host;

    /**
     * Port in which the server will run on.
     */
    private int port;

    /**
     * Map of other servers in the cluster. ServerID - Host, Port
     */
    private ConcurrentHashMap<Integer, Pair<String, Integer>> serverMap;

    /**
     * Map of the key value store. Key - Clock, Value
     * Clock represents the vector clock to identify the values newness.
     */
    private ConcurrentHashMap<String, Pair<Long, String>> store;

    /**
     * Map of the key value store. Key - Clock, Value
     * Clock represents the vector clock to identify the values newness.
     * This map contains the keys and values that have not been propagated between the server nodes yet.
     */
    private ConcurrentHashMap<String, Pair<Long, String>> writeAheadLog;

    /**
     * TODO: Replace the ServerID with an Object that encapsulates server information such as ServerID, type of node - Client or Server or Registry
     * Maintain a map of connections to the servers in the cluster. ServerID - Socket
     */
    private ConcurrentHashMap<Long, Socket> serverConnections;

    Server(String host, int port) {
        this.host = host;
        this.port = port;
        this.serverMap = new ConcurrentHashMap<>();
        this.store = new ConcurrentHashMap<>();
        this.writeAheadLog = new ConcurrentHashMap<>();
        this.serverConnections = new ConcurrentHashMap<>();
    }

    /**
     * Setup a thread that periodically refreshes the cluster's servers data by fetching them from the registry.
     * Retrieve all other server information in the cluster and save them
     * Register myself in a service registry
     */
    public void initializeServer() {

    }
}
