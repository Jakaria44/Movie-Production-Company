

package com.example.movielogin;

import Server.Movie;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class RecentMovieController {

    public Button button;
    public Button backButton;
    private Main main;
    public void setMain(Main main){ this.main = main;}
    private Scene preScene;

    public void setPreScene(Scene preScene) {
        this.preScene = preScene;
    }


    @FXML
    TableView<Movie> table = new TableView<>();
    @FXML
    public TableColumn<Movie, String> Title = new TableColumn<>("Title");
    @FXML
    public TableColumn<Movie, Integer> Year = new TableColumn<>("Year");


    public void init( )  {
        List<Movie> movieList = mostRecentMovies(main.getListOfMovies().getMovieList());

        for(Movie m:movieList){
            System.out.println(m.getYearORelease());
        }
        Title.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        Year.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("yearORelease"));
        table.setItems(FXCollections.observableArrayList(movieList));
        table.getColumns().setAll(Title, Year);

    }

    public void onBackButtonClick(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(preScene);
        stage.show();
    }
    public List <Movie> mostRecentMovies(List<Movie> mList) {
        int x = 0, maxReleasedYear = 0;
        List<Movie> returnList = new ArrayList<Movie>();
        for(Movie m: mList){
            if(m.getYearORelease() >maxReleasedYear)  maxReleasedYear= m.getYearORelease();
        }
        for(Movie m: mList){
            if(m.getYearORelease() == maxReleasedYear) returnList.add(m);
        }
        return returnList;
    }
}
