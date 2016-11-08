package webserver;

import constants.GeneralConstants;
import java.util.StringTokenizer;

/**
 * The representation for the RequestLine of a HttpRequest.
 *
 * <p>
 * Each RequestLine has a RequestMethod, a URI and the HTTP protocol version.
 */
public class RequestLine {

    /**
     * The RequestMethod: one of the constants defined in RequestConstants.java
     */
    private final String requestMethod;

    /**
     * The URI that points for the requested file.
     */
    private final String filepath;

    /**
     * The HTTP protocol version.
     */
    private final String httpVersion;

    /**
     * Constructs the RequestLine based on the input line and separates into the
     * three parameters.
     * @param requestLine the input line
     */
    public RequestLine(String requestLine) {
        StringTokenizer tokenizer = new StringTokenizer(requestLine);
        requestMethod = tokenizer.nextToken();
        filepath = tokenizer.nextToken();
        httpVersion = tokenizer.nextToken();

        if (GeneralConstants.DBG) {
            System.out.println("RequestLine :");
            System.out.println("--- requestMethod: " + requestMethod);
            System.out.println("--- filepath: " + filepath);
            System.out.println("--- HttpVersion: " + httpVersion);
        }
    }

    /**
     * Constructs the RequestLine directly based on parameters.
     * @param requestMethod the RequestMethod
     * @param filepath the URI
     * @param httpVersion the HTTP protocol version
     */
    public RequestLine(String requestMethod, String filepath, String httpVersion) {
        this.requestMethod = requestMethod;
        this.filepath = filepath;
        this.httpVersion = httpVersion;
    }

    /**
     * Return the RequestMethod.
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * Returns the URI.
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * Returns the HttpVersion.
     */
    public String getHttpVersion() {
        return httpVersion;
    }
}
