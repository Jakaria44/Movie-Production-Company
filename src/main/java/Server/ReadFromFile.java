package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadFromFile {
    private final String INPUT_FILE_NAME = "movies.txt";
    private final String PASSWORD_INPUT_FILE_NAME = "password.txt";
    private List<Movie> movieList = new ArrayList<Movie>();
    private HashMap< String ,String > userMap = new HashMap<>();
    private List<String > productionCompanyNames = new ArrayList<>();
    private HashMap<String , List<Movie> > pcMap= new HashMap<>();


    ReadFromFile() throws IOException {
        loadAll();      // loads all Movie and adds to the movieList
        makeListOfProductionCompanies();        // adds to the productioncompanyList
        for(String s:productionCompanyNames){
            pcMap.put(s,getProductionCompanyMovies(s));     // adds to the pcMap

        }
        loadPasswords();
    }
//    GETTERS

    public List<String> getProductionCompanyNames() {
        return productionCompanyNames;
    }

    public HashMap<String, List<Movie>> getPCMap() {
        return pcMap;
    }

    public HashMap<String, String> getUserMap() {
        return userMap;
    }

    public void loadPasswords() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(PASSWORD_INPUT_FILE_NAME ));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String []out = line.split(",");
            System.out.println(out[0]);
            Server.userMap.put(out[0], out[1]);
        }
        br.close();
    }

    public void makeListOfProductionCompanies(){
        String str = new String();
        for(Movie m : movieList){
            str = m.getProductionCompany();
            if(!productionCompanyNames.contains(str)){
                productionCompanyNames.add(str);
            }
        }
    }
    public void loadAll() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            movieList.add(MovieFromText(line));
        }
        br.close();
    }
    public List<Movie> getProductionCompanyMovies(String productionCompanyName){
        List<Movie> mList= new ArrayList<Movie>();
        for(Movie i:  movieList) {
            String  x = i.getProductionCompany();
            if(x.equalsIgnoreCase(productionCompanyName)) {    mList.add(i)  ;   }
        }

        return mList;
    }

    public Movie MovieFromText(String line) {
        String[] out = line.split(",");
        String title = out[0];
        int year = Integer.parseInt( out[1]);
        String [] genre = { out[2] , out[3], out[4]};
        int time = Integer.parseInt(out[5]);
        String productionCompany = out[6];
        long tk = Long.parseLong(out[7]);
        long rev = Long.parseLong(out[8]);

        return new Movie(title, year, genre, time, productionCompany, tk,rev);
    }
}
