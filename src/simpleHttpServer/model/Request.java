package simpleHttpServer.model;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private Socket socket;
    private Path path;
    private String method;
    private BufferedReader input;
    private OutputStream output;
    private List<String> params;

    public Request(Socket socket) throws IOException {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = socket.getOutputStream();
    }

    public List<String> getInputContent() throws IOException {
        List<String> content = new ArrayList<>();
        String line;
        while (!(line = input.readLine()).isEmpty()){
            content.add(line);
        }
        return content;
    }

    public void send(String answer) throws IOException {
        output.write((answer).getBytes());
    }

    public void send(byte[] answer) throws IOException {
        output.write(answer);
    }

    public Path getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = Path.of(path);
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void end() throws IOException {
        output.flush();;
        input.close();
        output.close();
    }

    public List<String> getParams() {
        return this.params;
    }
    public void setParams(List<String> params){
        if(params != null){
            this.params = new ArrayList<>();
            this.params.addAll(params);
        }
        else this.params = null;
    }
}
