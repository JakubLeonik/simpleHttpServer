package simpleHttpServer.service;

import simpleHttpServer.model.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class RequestService {
    private String rootDirectoryPath;

    public RequestService(String rootDirectoryPath) {
        this.rootDirectoryPath = rootDirectoryPath;
    }

    public void serve(Socket requestSocket) throws IOException {
        Request request = new Request(requestSocket);
        List<String> requestContent = request.getInputContent();
        String[] requestLine = requestContent.get(0).split(" ");
        request.setMethod(requestLine[0]);
        request.setPath(rootDirectoryPath+requestLine[1]);
        request.end();
        System.out.println(requestContent);
    }

}
