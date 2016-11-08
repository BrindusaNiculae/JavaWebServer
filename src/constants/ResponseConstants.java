package constants;

public class ResponseConstants {

    public static final String HTTP_OK_STATUS = "HTTP/1.1 200 OK\n\n";
    public static final String HTTP_NOT_FOUND_STATUS = "HTTP/1.1 404 Not Found\n\n";
    public static final String HTTP_BAD_REQUEST_STATUS = "HTTP/1.1 400 Bad Request\n\n";
    public static final String HTTP_NOT_IMPLEMENTED_STATUS = "HTTP/1.1 501 Not Implemented\n\n";
    
    public static final String HTTP_FOUND_BODY
            = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n"
            + "<html>\n"
            + "<head>\n"
            + " <title>200 Found</title>\n"
            + "</head>\n"
            + "<body>\n"
            + " <h1>Hello world!</h1>\n"
            + "</body>\n"
            + "</html>";
    public static final String HTTP_NOT_FOUND_BODY
            = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n"
            + "<html>\n"
            + "<head>\n"
            + " <title>404 Not Found</title>\n"
            + "</head>\n"
            + "<body>\n"
            + " <h1>Not Found</h1>\n"
            + " <p>The requested URL does not exist on this server.</p>\n"
            + "</body>\n"
            + "</html>";
    public static final String HTTP_BAD_REQUEST_BODY
            = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n"
            + "<html>\n"
            + "<head>\n"
            + " <title>400 Bad Request</title>\n"
            + "</head>\n"
            + "<body>\n"
            + " <h1>Bad Request</h1>\n"
            + " <p>Your browser sent a request that this server could not understand.<p>\n"
            + " <p>The request line contained invalid characters following the protocol string.<p>\n"
            + "</body>\n"
            + "</html>";
    public static final String HTTP_NOT_IMPLEMENTED_BODY
            = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n"
            + "<html>\n"
            + "<head>\n"
            + " <title>Not Implemented Function</title>\n"
            + "</head>\n"
            + "<body>\n"
            + " <h1>The HTTP Request Method is not implemented in the server.</h1>\n"
            + "</body>\n"
            + "</html>";
    public static final String HTTP_PUT_BODY
            = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n"
            + "<html>\n"
            + "<head>\n"
            + " <title>Put Request</title>\n"
            + "</head>\n"
            + "<body>\n"
            + " <h1>The file was successfully added to the server.</h1>\n"
            + "</body>\n"
            + "</html>";
    public static final String HTTP_DELETE_BODY
            = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n"
            + "<html>\n"
            + "<head>\n"
            + " <title>Delete request</title>\n"
            + "</head>\n"
            + "<body>\n"
            + " <h1>The file was successfully deleted from the server.</h1>\n"
            + "</body>\n"
            + "</html>";

}
