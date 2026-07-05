public class ServerProtocol {

    public Reply processRequest(Request req) {
        long numSteps = req.getNumber();
        if (numSteps == -1) {
            return new Reply("EXIT");
        }

        double sum = 0.0;
        double step = 1.0 / (double) numSteps;
        long start = System.nanoTime();
        for (long i = 0; i < numSteps; i++) {
            double x = (i + 0.5) * step;
            sum += 4.0 / (1.0 + x * x);
        }
        double pi = sum * step;
        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        String result = String.format("π ≈ %.15f (calculated in %.3f ms)", pi, timeMs);
        return new Reply(result);
    }
}
