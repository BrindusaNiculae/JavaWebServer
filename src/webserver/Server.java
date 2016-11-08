package webserver;

import constants.GeneralConstants;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Implementation for a multi-threaded web server with thread-pooling.
 */
public class Server implements Runnable {

    /**
     * The server socket
     */
    private ServerSocket serverSocket;

    /**
     * The pool of threads available for the server
     */
    private final ExecutorService threadPool;

    public Server() {
        serverSocket = null;
        threadPool = Executors.newFixedThreadPool(GeneralConstants.MAX_CONNECTIONS);
    }

    /** Runs the server. */
    @Override
    public void run() {
        if (GeneralConstants.DBG) {
            System.out.println("Server : run()");
        }
        openServerSocket();
        handleConnection();
        shutDownServer();
    }

    /**
     * Tries to open the server socket, exits the program if it fails.
     */
    private void openServerSocket() {
        if (GeneralConstants.DBG) {
            System.out.println("Server : openServerSocket() on port:"
                    + GeneralConstants.PORT);
        }
        try {
            this.serverSocket = new ServerSocket(GeneralConstants.PORT);
        } catch (IOException e) {
            System.err.println("Error opening server socket: " + e + "\nExiting...");
            System.exit(1);
        }
    }

    /**
     *
     * Handles the connection in a while loop.
     *
     * <p>
     * While the server is running, each connection request is passed to the
     * thread pool and is executed when a thread from the pool is available.
     */
    private void handleConnection() {
        if (GeneralConstants.DBG) {
            System.out.println("Server : handleConnection()");
        }
        while (!Thread.interrupted()) {
            threadPool.execute(new Worker(openClientSocket()));
        }
    }

    /**
     * Return a socket that corresponds to the connected client.
     */
    private Socket openClientSocket() {
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException("Error accepting client connection", e);
        }
        if (GeneralConstants.DBG) {
            System.out.println("Server : accepted client connection on socket: "
                    + clientSocket);
        }
        return clientSocket;
    }

    /**
     * Terminated all the threads running: the server and the threads from the
     * pool.
     */
    private void shutDownServer() {
        if (GeneralConstants.DBG) {
            System.out.println("Server : shutDownServer()");
        }
        stopServer();
        stopThreadPool();
    }

    /**
     * Terminates the thread pool.
     *
     * <p>
     * After the shutdown command is passed, the program waits a defined time
     * before force closing the threads. The waiting time is defined in
     * GeneralConstants.java
     *
     */
    private void stopThreadPool() {
        try {
            if (GeneralConstants.DBG) {
                System.out.println("Server : stopThreadPool()");
            }
            threadPool.shutdown();
            if (!threadPool.awaitTermination(GeneralConstants.SHUTDOWN_TIME,
                    TimeUnit.SECONDS)) {
                List<Runnable> droppedTasks = threadPool.shutdownNow();
                System.out.println("ThreadPool executor terminated. "
                        + droppedTasks.size()
                        + " tasks will not be executed.");
            }
        } catch (InterruptedException e) {
            System.err.println(
                    "Server : stopThreadPool() InterruptedException:"
                    + e);
            System.exit(1);
        }
    }

    /** Closes the server socket */
    private synchronized void stopServer() {
        if (GeneralConstants.DBG) {
            System.out.println("Server : stopServer() on port:"
                    + GeneralConstants.PORT);
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server.", e);
        }
    }

}
