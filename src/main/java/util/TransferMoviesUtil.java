package util;

import Server.Movie;

import java.io.Serializable;

public class TransferMoviesUtil  implements Serializable {
    public String receiver = new String();
    public Movie movie ;
    public boolean toRemove= false;

    public boolean isToRemove() {
        return toRemove;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public TransferMoviesUtil(String receiver, Movie movie, boolean toRemove) {

        this.receiver = receiver;
        this.movie = movie;
        this.toRemove = toRemove;
    }
}
