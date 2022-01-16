package simpleHttpServer;

import simpleHttpServer.service.RequestService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private String rootDirectory;
    private RequestService requestService;

    public Server(int port, String rootDirectory) {
        this.port = port;
        this.rootDirectory = rootDirectory;
        requestService = new RequestService(rootDirectory);
    }

    public void listen() throws IOException {
        serverSocket = new ServerSocket(port);
        while (true){
            Socket requestSocket = serverSocket.accept();
            requestService.serve(requestSocket);
        }
    }
}
