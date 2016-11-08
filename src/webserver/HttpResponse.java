package webserver;

import java.util.HashMap;

/**
 * The representation for the HttpResponse.
 *
 * <p>
 * Each HttpResponse has a statusLine, zero or more headers and optional
 * messageBody.
 */
public class HttpResponse {

    /**
     * The statusLine: one of the _STATUS constants defined in
     * ResponseConstants.java
     */
    private String statusLine;

    /** The headers. */
    private HashMap<String, String> headers;

    /**
     * The messageBody: either the content of the requested file or one of the
     * _BODY constants defined in ResponseConstants.java
     */
    private String messageBody;

    public HttpResponse() {
        statusLine = null;
        headers = new HashMap<>();
        messageBody = null;
    }

    /**
     * Sets the statusLine.
     *
     * @param statusLine The statusLine.
     */
    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    /** Return the statusLine. */
    public String getStatusLine() {
        return statusLine;
    }

    /**
     * Sets the headers
     *
     * @param headers A HashMap of the headers.
     */
    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    /** Return the headers as a HashMap. */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /** Returns the headers as a String.*/
    public String getHeadersString() {
        StringBuilder headersBuilder = new StringBuilder();
        for (String key : headers.keySet()) {
            headersBuilder.append(key);
            headersBuilder.append(":");
            headersBuilder.append(headers.get(key));
        }
        return headersBuilder.toString();
    }

    /**
     * Sets the messageBody
     *
     * @param messageBody HTML code for the page's body.
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    /** Returns the body of the message as a String.  */
    public String getMessageBody() {
        return messageBody;
    }

    /** Returns the full HttpResponse. */
    public String getHttpResponse() {
        return this.getStatusLine()
                //            + ResponseConstants.HTTP_HEADER
                + this.getHeadersString()
                + this.getMessageBody();
    }
}
