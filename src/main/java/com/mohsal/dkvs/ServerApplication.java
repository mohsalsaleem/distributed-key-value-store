package com.mohsal.dkvs;

import com.mohsal.dkvs.exception.InvalidServerArgumentsException;

/**
 * Author: Mohamed Saleem
 */
public class ServerApplication {
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
            e.printStackTrace();
        }
    }
}
