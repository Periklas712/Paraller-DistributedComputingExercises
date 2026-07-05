import java.net.*;
import java.io.*;

public class WorkerProtocol {

	private int numWorkers;
	
	public WorkerProtocol(int num) {
		numWorkers = num;
	}	

	public String compute(String theInput) throws IOException {
	
		String[] splited = theInput.split("\\s+");
		long numSteps = Long.parseLong(splited[0]);
		int id = Integer.parseInt(splited[1]);
		
		System.out.println("Worker "+ id +" calculates");
		
		long block = numSteps / numWorkers;
		long start = id * block;
		long stop = start + block;
		if (id == numWorkers-1) stop = numSteps;
		
		double sum = 0.0;
        double step = 1.0 / (double) numSteps;
        
        for (long i = start; i < stop; i++) {
            double x = (i + 0.5) * step;
            sum += 4.0 / (1.0 + x * x);
        }
             
        String theOutput = String.valueOf(sum);    
        return theOutput;
	}
}
