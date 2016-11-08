package webserver;

import constants.GeneralConstants;
import constants.RequestConstants;
import constants.ResponseConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * The representation for each worker thread from the pool.
 */
public class Worker implements Runnable {

    /**
     * The socket on which the connection is made.
     */
    private Socket clientSocket = null;

    /**
     * The HttpRequest received.
     */
    private final HttpRequest httpRequest;

    /**
     * The HttpResponse formed by the worker.
     */
    private final HttpResponse httpResponse;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
        httpRequest = new HttpRequest();
        httpResponse = new HttpResponse();
    }

    /**
     * The operations made by each worker for each request.
     */
    @Override
    public void run() {
        try {
            readFromSocket();
            setHttpResponse();
            writeToSocket();
            closeSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the HttpRequest from the socket and sends it to the HttpRequest to
     * be processed.
     */
    private void readFromSocket() throws IOException {
        InputStream input = clientSocket.getInputStream();
        if (GeneralConstants.DBG) {
            System.out.println("Worker : readFromSocket() input:" + input);
        }
        httpRequest.handleRequest(input);
    }

    /**
     * Writes the HttpResponse back to socket.
     */
    private void writeToSocket() throws IOException {
        OutputStream output = clientSocket.getOutputStream();
        output.write((httpResponse.getHttpResponse())
                .getBytes());
        if (GeneralConstants.DBG) {
            System.out.println("Worker : writeToSocket() ");
//                    + "httpReponse:" + httpResponse.getHttpResponse());
        }
    }

    /**
     * Closes the client socket.
     */
    private void closeSocket() throws IOException {
        if (GeneralConstants.DBG) {
            System.out.println("Worker : closeSocket()");
        }
        clientSocket.close();
    }

    /**
     * Forms the HttpResponse based on the HttpRequest.
     */
    private void setHttpResponse() {
        if (GeneralConstants.DBG) {
            System.out.println("Worker : setHttpResponse()");
        }
        switch (httpRequest.getRequestLine().getRequestMethod()) {
            case RequestConstants.HTTP_GET:
                handleGetRequest();
                break;
            case RequestConstants.HTTP_POST:
                httpResponse.setStatusLine(ResponseConstants.HTTP_NOT_IMPLEMENTED_STATUS);
                httpResponse.setMessageBody(ResponseConstants.HTTP_NOT_IMPLEMENTED_BODY);
                break;
            case RequestConstants.HTTP_HEAD:
                httpResponse.setStatusLine(ResponseConstants.HTTP_NOT_IMPLEMENTED_STATUS);
                httpResponse.setMessageBody(ResponseConstants.HTTP_NOT_IMPLEMENTED_BODY);
                break;
            case RequestConstants.HTTP_PUT:
                httpResponse.setStatusLine(ResponseConstants.HTTP_NOT_IMPLEMENTED_STATUS);
                httpResponse.setMessageBody(ResponseConstants.HTTP_NOT_IMPLEMENTED_BODY);
                break;
            case RequestConstants.HTTP_DELETE:
                httpResponse.setStatusLine(ResponseConstants.HTTP_NOT_IMPLEMENTED_STATUS);
                httpResponse.setMessageBody(ResponseConstants.HTTP_NOT_IMPLEMENTED_BODY);
                break;
            case RequestConstants.HTTP_CONNECT:
                httpResponse.setStatusLine(ResponseConstants.HTTP_NOT_IMPLEMENTED_STATUS);
                httpResponse.setMessageBody(ResponseConstants.HTTP_NOT_IMPLEMENTED_BODY);
                break;
            case RequestConstants.HTTP_OPTIONS:
                httpResponse.setStatusLine(ResponseConstants.HTTP_NOT_IMPLEMENTED_STATUS);
                httpResponse.setMessageBody(ResponseConstants.HTTP_NOT_IMPLEMENTED_BODY);
                break;
            case RequestConstants.HTTP_TRACE:
                httpResponse.setStatusLine(ResponseConstants.HTTP_NOT_IMPLEMENTED_STATUS);
                httpResponse.setMessageBody(ResponseConstants.HTTP_NOT_IMPLEMENTED_BODY);
                break;
            case RequestConstants.HTTP_BAD_REQUEST:
                httpResponse.setStatusLine(ResponseConstants.HTTP_BAD_REQUEST_STATUS);
                httpResponse.setMessageBody(ResponseConstants.HTTP_BAD_REQUEST_BODY);
                break;
            default:
                httpResponse.setStatusLine(ResponseConstants.HTTP_NOT_IMPLEMENTED_STATUS);
                httpResponse.setMessageBody(ResponseConstants.HTTP_NOT_IMPLEMENTED_BODY);
                break;
        }
    }

    /**
     * Handles the GET HTTP Requests.
     */
    private void handleGetRequest() {
        if (GeneralConstants.DBG) {
            System.out.println("Worker : handleGetRequest()");
        }
        if (httpRequest.getRequestLine().getFilepath().equals("/")) {
            httpResponse.setStatusLine(ResponseConstants.HTTP_OK_STATUS);
            httpResponse.setMessageBody(ResponseConstants.HTTP_FOUND_BODY);
            return;
        }

        File file = new File(httpRequest.getRequestLine().getFilepath());
        if (!file.exists()) {
            httpResponse.setStatusLine(ResponseConstants.HTTP_NOT_FOUND_STATUS);
            httpResponse.setMessageBody(ResponseConstants.HTTP_NOT_FOUND_BODY);
        } else {
            try {
                StringBuilder body = new StringBuilder();
                BufferedReader inputReader = new BufferedReader(new FileReader(file));
                String line = null;
                try {
                    while ((line = inputReader.readLine()) != null) {
                        body.append(line);
                    }
                } catch (IOException ex) {
                    System.out.println("Bad file ex: " + ex);
                    httpResponse.setStatusLine(ResponseConstants.HTTP_BAD_REQUEST_STATUS);
                    httpResponse.setMessageBody(ResponseConstants.HTTP_BAD_REQUEST_BODY);
                }
                httpResponse.setStatusLine(ResponseConstants.HTTP_OK_STATUS);
                httpResponse.setMessageBody(body.toString());
            } catch (FileNotFoundException ex) {
                System.out.println("File not found: " + ex);
                httpResponse.setStatusLine(ResponseConstants.HTTP_NOT_FOUND_STATUS);
                httpResponse.setMessageBody(ResponseConstants.HTTP_NOT_FOUND_BODY);
            }

        }
    }
}
