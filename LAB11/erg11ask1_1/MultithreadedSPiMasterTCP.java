import java.net.*;
import java.io.*;

public class MultithreadedSPiMasterTCP {
    private static final int PORT = 1234;
    private static final int numWorkers = 4;
    public static Pi pi = new Pi(10000000);

    public static void main(String args[]) throws IOException {
        ServerSocket connectionSocket = new ServerSocket(PORT);
        MasterThread mthread[] = new MasterThread[numWorkers];
        
        System.out.println("=== Pi Calculation Master Started ===");
        System.out.println("Listening on port: " + PORT);
        System.out.println("Waiting for " + numWorkers + " workers to connect...");
        System.out.println("==========================================");
        
        for (int i = 0; i < numWorkers; i++) {    
            System.out.println("Waiting for worker " + (i+1) + "/" + numWorkers + "...");
            Socket dataSocket = connectionSocket.accept();
            System.out.println("✓ Worker " + (i+1) + " connected from: " + dataSocket.getRemoteSocketAddress());
            mthread[i] = new MasterThread(dataSocket, i, pi);
            mthread[i].start();
        }
        System.out.println("\n=== All " + numWorkers + " workers connected and started ===");
        
        for (int i = 0; i < numWorkers; i++) {
            try {
                mthread[i].join();
                System.out.println("Worker " + (i+1) + " finished");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e);
            }
        }
         
        System.out.println("\n=== Calculation Complete ===");
        pi.printResult();
        connectionSocket.close();
        System.out.println("Master shutdown complete.");
    }
}