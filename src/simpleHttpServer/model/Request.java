package simpleHttpServer.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private Socket socket;
    private Path path;
    private String method;
    private BufferedReader input;
    private OutputStreamWriter output;

    public Request(Socket socket) throws IOException {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new OutputStreamWriter(socket.getOutputStream());
    }

    public List<String> getInputContent() throws IOException {
        List<String> content = new ArrayList<>();
        String line;
        while (!(line = input.readLine()).isBlank()){
            content.add(line);
        }
        return content;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(String path) {
        if(path.endsWith("/")) path += "index.html";
        this.path = Path.of(path);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void end() throws IOException {
        input.close();
        output.close();
    }
}
