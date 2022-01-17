package simpleHttpServer.service;

import simpleHttpServer.model.Request;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RequestService implements Runnable{
    //variables
    private String rootDirectoryPath;
    Request request;

    //constructor
    public RequestService(String rootDirectoryPath, Socket requestSocket) throws IOException {
        this.rootDirectoryPath = rootDirectoryPath;
        request = new Request(requestSocket);
    }

    //main thread function
    public void run() {
        try {
            initializeRequest(request);
            byte[] resource = getResource(request.getPath());
            if(resource == null) sendResponse(request, "404 Not Found", "404 Not Found".getBytes(), "text/html");
            else sendResponse(request, "200 OK", resource, Files.probeContentType(request.getPath()));
            request.end();
        } catch (IOException e) {
            System.out.println("Server exception: "+e.getMessage());
        }
    }

    private byte[] getResource(Path path) throws IOException {
        if(Files.isDirectory(path)){
            if(Files.exists(Path.of(path+ "\\index.html"))) path = Path.of(path+"\\index.html");
            else if(Files.exists(Path.of(path + "\\index.php"))) path = Path.of(path+"\\index.php");
            else return null;
        }
        String file;
        if(Files.exists(path)){
            if(path.getFileName().toString().contains(".php")){
                PhpService phpService = new PhpService();
                file = phpService.runPHP(path.toString(), request.getParams());
                return file.getBytes();
            }
            else return Files.readAllBytes(path);
        }
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
        List<String> params = new ArrayList<>();
        if (path.contains("?")) {
            String stringParams = path.substring((path.indexOf('?')+1));
            path = path.substring(0, path.indexOf('?'));
            for(String param : stringParams.split("&")) {
                params.add(param);
            }
            request.setParams(params);
        }
        else request.setParams(null);
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
