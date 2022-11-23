package util;

import java.io.Serializable;

public class CloseRequest implements Serializable {
    public String name = new String();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CloseRequest(String name) {
        this.name = name;
    }
}
