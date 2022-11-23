package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import util.*;

public class Server {
    private ServerSocket serverSocket;


    public static HashMap<String , NetworkUtil> pcConnectedList = new HashMap<>();
//        Passwords
    public static HashMap<String, String> userMap =new HashMap<>();
    static List<String>listOfPC = new ArrayList<>();

//      all list of PCname and list of their movies

    public static HashMap<String , List<Movie> >pcMap= new HashMap<>() ;

    public HashMap<String, List<Movie>> getPcMap() {
        return pcMap;
    }

    Server() throws  Exception{
        ReadFromFile readFromFileObject = new ReadFromFile();

        pcMap = readFromFileObject.getPCMap();

        listOfPC = readFromFileObject.getProductionCompanyNames();

        System.out.println("successfully read from file");

        try {
            serverSocket = new ServerSocket(26262);
            System.out.println("Server Running....");
            new ServerIOThread(this);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println( e);
        }
    }



    public void save() throws Exception {
        WriteOnFile.writeMovieFromArrayToFile();
        WriteOnFile.writeAllPasswords();
        System.out.println("saved");
    }

    public void close() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {

        new Server();

    }
    public void serve(Socket clientSocket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        new ReadThreadServer(userMap,  networkUtil);
    }
}
