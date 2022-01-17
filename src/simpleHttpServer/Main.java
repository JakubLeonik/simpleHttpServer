package simpleHttpServer;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(8080, "E:\\html\\");
        try {
            server.listen();
        } catch (Exception exception) {
            System.out.println("Server exception: "+exception.getMessage());
            exception.printStackTrace();
        }
    }
}
