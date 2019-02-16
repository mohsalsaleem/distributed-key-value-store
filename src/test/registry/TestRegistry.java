package registry;

import com.mohsal.dkvs.registry.Registry;
import com.mohsal.dkvs.registry.RegistryCommand;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Author: Mohamed Saleem
 */
public class TestRegistry {
    private Socket socket = null;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;

    HashMap<String, String> commandsToSend = new HashMap<>();

    public TestRegistry() {
    }

    public void start() {
        commandsToSend.put(RegistryCommand.REGISTER.name(), "host=127.0.0.1&port=8082");
        commandsToSend.put(RegistryCommand.DEREGISTER.name(), "host=127.0.0.1&port=8082");
        try {
            socket = new Socket("localhost", 8081);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String toSend = RegistryCommand.REGISTER.name() + " " + commandsToSend.get("REGISTER");
        String received = "";
        try {
            dataOutputStream.writeUTF(toSend);
            while (!received.equals("DONE")) {
                received = dataInputStream.readUTF();

                System.out.println("Received: " + received);

                if(received.equals("REGISTERED")) {
                    toSend = RegistryCommand.DEREGISTER.name() + " " + commandsToSend.get("DEREGISTER");
                    dataOutputStream.writeUTF(toSend);
                } else if (received.equals("DEREGISTERED")) {
                    System.out.println("Test successful");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        TestRegistry testRegistry = new TestRegistry();
        testRegistry.start();
    }
}
