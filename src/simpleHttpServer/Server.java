package simpleHttpServer;

import simpleHttpServer.service.RequestService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private String rootDirectory;

    public Server(int port, String rootDirectory) {
        this.port = port;
        this.rootDirectory = rootDirectory;
    }

    public void listen() throws IOException {
        serverSocket = new ServerSocket(port);
        while (true){
            Socket requestSocket = serverSocket.accept();
            RequestService requestService = new RequestService(rootDirectory, requestSocket);
            Thread requestThread = new Thread(requestService);
            requestThread.start();
        }
    }
}
