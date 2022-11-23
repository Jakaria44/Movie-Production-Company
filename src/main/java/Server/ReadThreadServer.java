package Server;


import util.*;

import java.io.IOException;
import java.util.HashMap;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final NetworkUtil networkUtil;
    public HashMap<String, String> userMap;

    public ReadThreadServer(HashMap<String, String> map, NetworkUtil networkUtil) {
        this.userMap = map;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);

        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();

                if (o != null) {

                    if(o instanceof Boolean){
                        boolean toSendPC = (boolean) o;
                        if(toSendPC){
                            networkUtil.write(new ListOfPC(Server.listOfPC));
                        }
                    }
                    else if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        String password = userMap.get(loginDTO.getUserName());


                        if(!loginDTO.getPassword().equals(password)){
//                          SEND A MESSAGE
                            networkUtil.write(new String("Password Incorrect"));
                        }
                        else {
//                          asking for list of movies
                            ListOfMovies listOfMovies = new ListOfMovies(loginDTO.getUserName(), Server.pcMap.get(loginDTO.getUserName()));
                            if(!Server.pcConnectedList.containsKey(listOfMovies.getUserName())) {
                                Server.pcConnectedList.put(listOfMovies.getUserName(), networkUtil);
//                              Server.pcConnectedList.forEach((key, value) -> System.out.println(key + " " + value));
                                networkUtil.write(listOfMovies);
                            }
                            else {
                                networkUtil.write(new String("This company is already logged in!!"));
                            }
                        }
                    }
                    else if(o instanceof TransferMoviesUtil){

//                        TRANSFERRING OPTIONS
                        TransferMoviesUtil moviesUtil = (TransferMoviesUtil) o; //casting
                        Movie movie = moviesUtil.getMovie();
                        String receiver = moviesUtil.getReceiver();

                        Server.pcMap.forEach((key, value)->{
                            value.removeIf(m->m.getTitle().equalsIgnoreCase(movie.getTitle()));
                        });
                        Server.pcMap.get(receiver).add(movie);

                        if(Server.pcConnectedList.containsKey(receiver)) {
                            Server.pcConnectedList.get(receiver).write(movie);
                        }

                    }
                    else if (o instanceof Movie){
                        Movie movie = (Movie) o;
                        Server.pcMap.get(movie.getProductionCompany()).add(movie);
                    }
                    else if(o instanceof CloseRequest){
//                        REMOVE FROM CONNECTED LIST
                        CloseRequest closeRequest = (CloseRequest) o;
                        Server.pcConnectedList.remove(closeRequest.getName());
                        System.out.println(closeRequest.getName()+" disconnected");

                    }
                    else if(o instanceof ResetPasswordUtil){
                        ResetPasswordUtil resetPasswordUtil = (ResetPasswordUtil) o;
                        Server.userMap.put(resetPasswordUtil.getPcName(), resetPasswordUtil.getPassword());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {


                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}



