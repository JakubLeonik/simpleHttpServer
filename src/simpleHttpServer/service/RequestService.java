package simpleHttpServer.service;

import simpleHttpServer.model.Request;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RequestService {
    private String rootDirectoryPath;

    public RequestService(String rootDirectoryPath) {
        this.rootDirectoryPath = rootDirectoryPath;
    }

    public void serve(Socket requestSocket) throws IOException {
        Request request = new Request(requestSocket);
        initializeRequest(request);
        byte[] resource = findResource(request.getPath());
        if(resource == null) sendResponse(request, "404 Not Found", resource);
        else sendResponse(request, "200 OK", resource);
        request.end();
    }

    private byte[] findResource(Path path) throws IOException {
        if(Files.exists(path)) return Files.readAllBytes(path);
        else return null;
    }

    private void initializeRequest(Request request) throws IOException {
        List<String> requestContent = request.getInputContent();
        String[] requestLine = requestContent.get(0).split(" ");
        request.setMethod(requestLine[0]);
        request.setPath(rootDirectoryPath+requestLine[1]);
    }
    private void sendResponse(Request request, String status, byte[] resource){
        System.out.println("I will send answer with status: "+status);
    }
}
