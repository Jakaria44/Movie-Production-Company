package util;

import javafx.fxml.Initializable;

import java.io.Serializable;

public class ResetPasswordUtil implements Serializable {
    private String pcName;
    private String password;

    public String getPcName() {
        return pcName;
    }

    public String getPassword() {
        return password;
    }

    public ResetPasswordUtil(String pcName, String password) {
        this.pcName = pcName;
        this.password = password;
    }
}
