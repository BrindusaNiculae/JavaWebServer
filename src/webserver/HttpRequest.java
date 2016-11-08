package webserver;

import constants.GeneralConstants;
import constants.RequestConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The representation for the HttpRequest.
 *
 * <p>
 * Each HttpRequest has a RequestLine, zero or more headers and optional
 * message-body.
 *
 * TODO: add functionality for message-body.
 */
public class HttpRequest {

    /**
     * The RequestLine.
     */
    private RequestLine requestLine;

    /**
     * The headers.
     *
     * <p>
     * The headers are saved in a HashMap. Right now they are simply copied from
     * the request to the response, but in case some ulterior processing was
     * intended, it would be relatively easy to add functionality from this
     * point based on this representation.
     */
    private final HashMap<String, String> headers;

    public HttpRequest() {
        requestLine = null;
        headers = new HashMap<>();
    }

    /**
     * Separates the inputStream into the RequestLine and the headers.
     *
     * @param input The message read from the socket.
     * @throws java.io.IOException
     */
    public void handleRequest(InputStream input) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));

        readRequestLine(bufferedReader);
        readHeaders(bufferedReader);

        if (GeneralConstants.DBG) {
            System.out.println("HttpRequest : handleRequest()");
            System.out.println("--- requestLine: " + requestLine);
            System.out.println("--- headers: " + headers.toString());
        }
    }

    /**
     * Initializes the requestLine.
     */
    private void readRequestLine(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        if (line == null) {
            requestLine = new RequestLine(RequestConstants.HTTP_BAD_REQUEST, null, null);
        } else {
            requestLine = new RequestLine(line);
        }
    }

    /**
     * Initializes the headers.
     */
    private void readHeaders(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        while (!line.isEmpty() && line != null) {
            headers.put(line.substring(0, line.indexOf(":")),
                    line.substring(line.indexOf(":") + 1, line.length()));
            line = bufferedReader.readLine();
        }
    }

    /**
     * Returns the RequestLine.
     */
    public RequestLine getRequestLine() {
        return requestLine;
    }

    /**
     * Returns the headers.
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }
}
