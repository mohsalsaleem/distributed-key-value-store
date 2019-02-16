package com.mohsal.dkvs.registry;

import com.mohsal.dkvs.exception.InvalidPayloadException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Mohamed Saleem
 */
public class ClientHandler extends Thread {
    final DataInputStream inputStream;
    final DataOutputStream outputStream;

    final Socket socket;

    public ClientHandler(DataInputStream inputStream, DataOutputStream outputStream, Socket socket) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.socket = socket;
    }

    @Override
    public void run() {
        String received;
        String toSend;
        while (true) {
            try {
                received = inputStream.readUTF();

                System.out.println("Received: " + received);

                String[] receivedSplit = received.split("\\s+");
                String command = receivedSplit[0];
                RegistryCommand registryCommand = RegistryCommand.valueOf(command);

                switch (registryCommand) {

                    case REGISTER:
                        Map<String, String> registerPayload = getRegistrationPayloadMap(receivedSplit[1]);
                        String hostPortToRegister = registerPayload.get("host") + ":" + registerPayload.get("port");
                        Registry.serversMap.put(hostPortToRegister, true);
                        System.out.println("Servers: " + Registry.serversMap);

                        outputStream.writeUTF("REGISTERED");

                        break;
                    case DEREGISTER:
                        Map<String, String> deregisterPayload = getRegistrationPayloadMap(receivedSplit[1]);
                        String hostPortToDeregister = deregisterPayload.get("host") + ":" + deregisterPayload.get("port");
                        Registry.serversMap.remove(hostPortToDeregister);
                        System.out.println("Servers: " + Registry.serversMap);

                        outputStream.writeUTF("DEREGISTERED");
                        break;
                    case ACKNOWLEDGED:
                        break;
                }
            } catch (EOFException e) {
              e.printStackTrace();
              System.out.println("Client disconnected");
              break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidPayloadException e) {
                e.printStackTrace();
            }
        }

        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> getRegistrationPayloadMap(String s) throws InvalidPayloadException {
        if(s.isEmpty()) {
            throw new InvalidPayloadException();
        }
        HashMap<String, String> data = new HashMap<>();

        String[] params = s.split("&");
        for (String param : params) {
            String[] split = param.split("=");
            data.put(split[0], split[1]);
        }

        return data;
    }
}
