package Server;


import javafx.scene.image.Image;

import java.io.Serializable;

public class Movie implements Serializable {
     private String title, productionCompany,   imgSource;
     private int yearORelease, runTime;
     private long budget,revenue;
     private String [] genre;

     public Movie(String title, int yearORelease, String []genre, int runTime, String productionCompany, long budget, long revenue){

         this.title = title;
         this.yearORelease = yearORelease;
         this.genre = genre;
         this.runTime = runTime;
         this.productionCompany = productionCompany;
         this.budget= budget;
         this.revenue = revenue;
     }

    public Movie() {
        this.title = new String();
        this.yearORelease = 0;
        this.genre = new String[3];
        this.runTime = 0;
        this.productionCompany = new String();
        this.budget= 0;
        this.revenue = 0;
    }

    public String getImgSource() {
        return imgSource;
    }

//    public void setImgSource(String imgSource) {
//        try {
//            new Image(getClass().getResourceAsStream(imgSource));
//            this.imgSource = imgSource;
//        } catch (Exception e) {
//            this.imgSource = "/images/unknown.png";
//        }
//    }
//    public void setTitle(String name) {
//        this.title = name;
//        setImgSource("/images/" + name.replace(' ', '') + ".png");
//    }
    public String getTitle() {  return title;  }
    public String getProductionCompany() {   return productionCompany;    }
    public int getYearORelease() {    return yearORelease;    }
    public int getRunTime() {    return runTime;    }
    public long getBudget() {    return budget;    }
    public long getRevenue() {   return revenue;    }
    public long getProfit() {  return (this.revenue - this.budget);  }
    public String[] getGenre() {   return genre;    }

    @Override
    public String toString(){
         String str = "    The details of the Movie: "+  " Title:  "+ title +  "Year of Release:     "+ yearORelease+   "  Production Company:  " ;
         str=str+ productionCompany +   "     Genre:   "+  genre[0] ;
        if(genre[1].length() > 0 ) str = str+  genre[1] ;
        if(genre[2].length() > 0 )  str = str+  genre[2] ;
        str= str+ "  Running Time:   "+ runTime+" Budget:"+budget+  "  Revenue: "+ revenue ;
        return str;
    }

}
