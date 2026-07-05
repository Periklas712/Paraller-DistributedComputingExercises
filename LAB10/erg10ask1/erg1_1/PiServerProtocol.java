public class PiServerProtocol {

	private long numSteps;

	public String processRequest(String numStepsString) {
		numSteps=Long.parseLong(numStepsString);
		System.out.println("Request for calculation of pi with "+numSteps+" steps");
        String result="";
		//ypologismos toy pi akolouthiaka kodikas numInt
		double sum = 0.0;
		double step = 1.0 / (double)numSteps;
        /* do computation */
		long startTime = System.nanoTime();
        for (long i=0; i < numSteps; ++i) {
            double x = ((double)i+0.5)*step;
            sum += 4.0/(1.0+x*x);
        }
        double pi = sum * step;
		long endTime = System.nanoTime();
		double durationInMillis = (endTime - startTime) / 1_000_000.0;
		result = String.format("%.15f (calculated in %.3f ms)", pi, durationInMillis);
		return result;
	}
}
