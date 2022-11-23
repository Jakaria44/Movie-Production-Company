package com.example.movielogin;

import Server.Movie;
import javafx.application.Platform;
import util.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class ReadThread implements Runnable {
    private final Thread thr;
    private final Main main;

    public ReadThread(Main main) {
        this.main = main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = main.getNetworkUtil().read();
                if (o != null) {
//                    for log in window
                    if(o instanceof ListOfPC){
                        ListOfPC listOfPC = (ListOfPC) o;
                        Collections.sort(listOfPC.getListOfPC());
                        main.setListofPC(listOfPC.getListOfPC());
                    }
//                    if entered password is wrong
                   else if(o instanceof String){
                        String  message = (String) o;
                        // the following is necessary to update JavaFX UI components from user created threads
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String title = "Invalid Log in";
                                main.showAlert(title, "");
                            }
                        });

                    }
//                   logged in successfully
                   else if(o instanceof ListOfMovies){
                        ListOfMovies listOfMovies = (ListOfMovies) o;
//                        System.out.println("in readthread");

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showHomePage(listOfMovies);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
//                   transferred from other Production Company
                    else if(o instanceof Movie){
                        Movie movie = (Movie) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.addMovie(movie);
//                                    System.out.println("added "+ movie.getTitle());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e+ " in read thread");
        } finally {
            try {
                main.getNetworkUtil().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
