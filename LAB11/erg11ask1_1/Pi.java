public class Pi {
	private static double pi;
	private static double sum;
	private  long numSteps;
	
	public Pi (long numSteps) {
		pi=0.0;
		sum=0.0;
		this.numSteps=numSteps;
	}

	public synchronized void addTo(double toAdd) {
		sum=sum+toAdd;
	}

	public synchronized void printResult () {
		pi=(1.0 / (double) numSteps)*sum;
	    System.out.println("Result ="+ pi);
	}

	public synchronized long getNumSteps(){
		return this.numSteps;
	}

	public synchronized String printInit() {
        return String.valueOf(numSteps);
    }
	
}
