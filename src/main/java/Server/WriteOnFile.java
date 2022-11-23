package Server;

import javafx.scene.paint.Paint;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WriteOnFile {


    private static final String OUTPUT_FILE_NAME = "movies.txt";

    private static final String PASSWORD_OUTPUT_FILE_NAME = "password.txt";


    static void writeMovieFromArrayToFile() throws Exception {

        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));

        Server.pcMap.forEach((key, value) ->{
            for(Movie m:  value){
                String text = textFromMovie(m);
                try {
                    bw.write(text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    bw.write(System.lineSeparator());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        bw.close();
    }

    static void writeAllPasswords() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(PASSWORD_OUTPUT_FILE_NAME));
        for(Map.Entry<String, String> e : Server.userMap.entrySet()){
            bw.write(e.getKey()+","+e.getValue());
            bw.write(System.lineSeparator());
        }
        bw.close();
    }
    static String textFromMovie(Movie m){
        String [] genreArray = m.getGenre();

        String text = m.getTitle()+",";
        text+=m.getYearORelease()+",";
        text+= genreArray[0] +"," ;
        text+= genreArray[1] +"," ;
        text+= genreArray[2] +"," ;
        text+= m.getRunTime() +"," ;
        text+= m.getProductionCompany() +"," ;
        text+= m.getBudget() +"," ;
        text += m.getRevenue()  ;

        return text;
    }
}
