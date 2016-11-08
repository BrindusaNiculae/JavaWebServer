package webserver;

import constants.GeneralConstants;
/**
 *
 * @author brindu
 */
public class Main {
    
    /**
     * @param args the command line arguments - no command line arguments used
     */
    public static void main(String[] args) {
        startServer();
    }
    
    /** Starts the server. */
    private static void startServer() {
        if (GeneralConstants.DBG) {
            System.out.println("Main : startServer()");
        }
        new Thread(new Server()).start();
    }

}
