package simpleHttpServer.service;

import simpleHttpServer.model.Param;
import simpleHttpServer.model.Request;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
        if(resource == null) sendResponse(request, "404 Not Found", null, null);
        else {
            sendResponse(request, "200 OK", resource, Files.probeContentType(request.getPath()));
        }
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
        requestLine[1] = excludeParams(request, requestLine[1]);
        request.setPath(rootDirectoryPath + requestLine[1]);
    }

    private String excludeParams(Request request, String path){
        List<Param> params = new ArrayList<>();
        if (path.contains("?")) {
            String stringParams = path.substring((path.indexOf('?')+1));
            path = path.substring(0, path.indexOf('?'));
            for(String param : stringParams.split("&"))
                params.add(new Param(param.split("=")[0], param.split("=")[1]));
        }
        request.setParams(params);
        return path;
    }

    private void sendResponse(Request request, String status, byte[] resource, String resourceType) throws IOException {
        request.send("HTTP/1.1 \r\n" + status);
        request.send("ContentType: " + resourceType + "\r\n");
        request.send("\r\n".getBytes());
        request.send(resource);
        request.send("\r\n\r\n".getBytes());
    }
}
