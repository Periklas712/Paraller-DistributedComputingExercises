import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//ΑΝΤΙΚΕΙΜΕΝΟ ΜΕ ΜΟΙΡΑΖΟΜΕΝΗ ΔΟΜΗ ΓΙΑ ΝΑ ΚΡΑΤΑΩ ΤΑ SOCKETS
public class SharedSockets {
    
    List<Socket> listOfSockets;
    List<PrintWriter> writers;

    public SharedSockets(){
        listOfSockets = new ArrayList<>();
        writers = new ArrayList<>();
    }

    public synchronized String  getSocketAddress(Socket socket){
        for(Socket s : listOfSockets){
            if(socket.getInetAddress().equals(s.getInetAddress())){
                return s.getInetAddress().toString();
            }
        }
        return null;
    }

    public synchronized List<Socket> getListOfSockets(){
        return this.listOfSockets;
    }

    //ΜΕΘΟΔΟΣ ΠΟΥ ΒΑΖΩ ΣΤΗΝ ΛΙΣΤΑ ΜΙΑ ΝΕΑ ΣΥΝΔΕΣΗ ΚΑΙ ΕΝΑΝ WRITER
    public synchronized void addSocket(Socket s) throws IOException {
        this.listOfSockets.add(s);
        writers.add(new PrintWriter(s.getOutputStream(), true));
        System.out.println(s.getInetAddress().toString()+ " joined. Total clients: " + listOfSockets.size());
    }

    //ΜΕΘΟΔΟΣ ΠΟΥ ΣΤΕΛΝΕΙ ΜΥΝΗΜΑ ΣΕ ΟΛΑ ΤΑ SOCKETS ΕΚΤΟΣ ΑΠΟ ΤΟ Ν ΑΠΟΣΤΟΛΕΑ 
    public synchronized void sendMessage(String message,Socket sender){
        for (int i=0;i<listOfSockets.size();i++){
            if(!listOfSockets.get(i).equals(sender)){
                writers.get(i).println(message);
            }
        }
    }

    //ΜΕΘΟΔΟΣ ΠΟΥ ΑΦΙΑΡΕΙ ΕΝΑ SOCKET ΑΠΟ ΤΗΝ ΛΙΣΤΑ 
    public synchronized void removeSocket(Socket s){
        int socketToRemove = listOfSockets.indexOf(s);
        if (socketToRemove!=-1){
            listOfSockets.remove(socketToRemove);
            writers.remove(socketToRemove);
            System.out.println(s.getInetAddress().toString()+ "left. Total clients: " + listOfSockets.size());
        }
    }

    

}
