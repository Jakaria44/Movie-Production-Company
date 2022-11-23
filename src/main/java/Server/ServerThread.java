package Server;



import java.io.IOException;

public class ServerThread implements Runnable{

    @Override
    public void run() {
        try {
            System.out.println("in serverThread");
            new Server();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("server thread closed");
        }
    }
}
