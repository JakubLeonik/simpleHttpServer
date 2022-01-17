package simpleHttpServer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PhpService {
    private String phpPath = "C:\\Apps\\php\\php-cgi.exe";

    public String runPHP(String path, List<String> params) throws IOException {
        List<String> args = new ArrayList<>();
        args.add(phpPath);
        args.add("-f");
        args.add(path);
        if(params != null) args.addAll(params);
        ProcessBuilder processBuilder = new ProcessBuilder(args);
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
