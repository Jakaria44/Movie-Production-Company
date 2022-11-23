package util;

import Server.Movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListOfMovies implements Serializable {
    private String userName = new String();
    private List<Movie> movieList = new ArrayList<>();
    private long totalProfit;

    public ListOfMovies(){
        userName = null;
        movieList = null;
        totalProfit = 0;
    }
    public void addMovieInListOfMovies(Movie movie){
        movieList.add(movie);
    }
    public ListOfMovies(String userName, List<Movie> movieList) {
        this.userName = userName;
        this.movieList = movieList;
        this.totalProfit = getTotalProfitFromList(movieList);
    }

    public long getTotalProfit() {
        return totalProfit;
    }

    public long getTotalProfitFromList(List<Movie> mList ) {
        long total = 0;
        for(Movie m: mList) {
            total+= m.getProfit();
        }
        return total;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
