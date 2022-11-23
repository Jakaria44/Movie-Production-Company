package Server;

import java.util.Scanner;

public class ServerIOThread implements Runnable {

    Scanner input;
    Server server;

    ServerIOThread(Server server) {
        this.server = server;
        input = new Scanner(System.in);
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("ServerIOThread started");
        while (true) {
            String text = input.next();
            if (text.equalsIgnoreCase("close")) {
//                CHECKING BEFORE CLOSING SERVER
                if (Server.pcConnectedList.isEmpty()) break;
                else System.out.println("All clients must be disconnected");
            }
        }
//        SAVING BEFORE CLOSING
        try {
            server.save();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        server.close();
        System.out.println("ServerIOThread closed");
    }
}
