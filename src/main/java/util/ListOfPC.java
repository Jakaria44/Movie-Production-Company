package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListOfPC implements Serializable {

    private List<String > listOfPC = new ArrayList<>();

    public List<String> getListOfPC() {
        return listOfPC;
    }

    public void setListOfPC(List<String> listOfPC) {
        this.listOfPC = listOfPC;
    }

    public ListOfPC(List<String> listOfPC) {
        this.listOfPC = listOfPC;
    }
}
