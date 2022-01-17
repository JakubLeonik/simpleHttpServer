package simpleHttpServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

//Main app class
public class Main {
    public static void main(String[] args) {
        //load properties
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream("src/simpleHttpServer/server.config")));
        } catch (IOException e) {
            System.out.println("Loading properties failed!");
            System.exit(-1);
        }
        int port = Integer.parseInt(properties.getProperty("port"));
        String rootDirectory = properties.getProperty("rootDirectory");

        //start server
        Server server = new Server(port, rootDirectory);
        try {
            server.listen();
        } catch (Exception exception) {
            System.out.println("Server exception: "+exception.getMessage());
            exception.printStackTrace();
        }
    }
}
