package simpleHttpServer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PhpService {

    private String phpPath = "C:\\Apps\\php\\php.exe";
    public String runPHP(String path) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(phpPath, path);
        Process process = processBuilder.start();
        BufferedReader processInputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line, file = "";
        while ((line = processInputStream.readLine()) != null){
            file+=line;
            file+='\n';
        }
        return file;
    }
}
