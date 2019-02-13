package com.mohsal.dkvs;

import com.mohsal.dkvs.exception.InvalidServerArgumentsException;
import javafx.util.Pair;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Mohamed Saleem
 * The Server node.
 */
public class Server {
    private static final Logger LOGGER =
            Logger.getLogger(Server.class.getName());

    private String host;

    private int port;

    // Map of other servers in the cluster. ServerID - Host, Port
    private ConcurrentHashMap<Integer, Pair<String, Integer>> serverMap;

    // Map of the key value store. Key - Clock, Value
    // Clock represents the vector clock to identify the values newness.
    private ConcurrentHashMap<String, Pair<Long, String>> store;

    // Map of the key value store. Key - Clock, Value
    // Clock represents the vector clock to identify the values newness.
    // This map contains the keys and values that have not been propagated between the server nodes yet.
    private ConcurrentHashMap<String, Pair<Long, String>> writeAheadLog;

    // TODO: Replace the ServerID with an Object that encapsulates server information such as ServerID, type of node - Client or Server or Registry
    // Maintain a map of connections to the servers in the cluster. ServerID - Socket
    private ConcurrentHashMap<Long, Socket> serverConnections;

    Server(String host, int port) {
        this.host = host;
        this.port = port;
        this.serverMap = new ConcurrentHashMap<>();
        this.store = new ConcurrentHashMap<>();
        this.writeAheadLog = new ConcurrentHashMap<>();
        this.serverConnections = new ConcurrentHashMap<>();
    }

    // Register myself in a service registry
    // Retrieve all other server information in the cluster and save them
    // Setup a thread that periodically refreshes the cluster's servers data by fetching them from the registry.
    public void initializeServer() {

    }

    public static void main(String[] args) {
        String host = "";
        int port = -1;
        try {
            // Parse arguments and get the port and host values for this server to run on.
            for (String arg: args) {
                String[] argument = arg.split("=");
                if(argument.length <2) {
                    throw new InvalidServerArgumentsException();
                }
                String param = argument[0];
                String value = argument[1];
                if ("host".equals(param)) {
                    host = value;

                } else if ("port".equals(param)) {
                    port = Integer.parseInt(value);

                }
                if(host.isEmpty() || port == -1) {
                    throw new InvalidServerArgumentsException();
                }
            }

        } catch (InvalidServerArgumentsException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
}
