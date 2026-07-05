import java.io.*;
import java.net.*;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(HOST, PORT);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        Request req = new Request();
        req.setNumber();
        out.writeObject(req);

        Reply rep = (Reply) in.readObject();
        System.out.println("Server response: " + rep.getResult());

        in.close();
        out.close();
        socket.close();
    }
}


