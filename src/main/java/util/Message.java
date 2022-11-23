package util;

import java.io.Serializable;

public class Message implements Serializable {
    private String message = new String();
    private boolean logging = false;
    private boolean transferring = false;


    public boolean isLogging() {
        return logging;
    }

    public boolean isTransferring() {
        return transferring;
    }

    public String getMessage() {
        return message;
    }

    public Message( String message, boolean logging, boolean transferring) {

        this.logging = logging;
        this.transferring = transferring;
        this.message = message;
    }
}
