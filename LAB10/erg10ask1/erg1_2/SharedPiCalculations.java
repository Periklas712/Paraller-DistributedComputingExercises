package erg10ask1.erg1_2;
import java.util.HashMap;

public class SharedPiCalculations {

    private HashMap<Long,Double> piCalculationHashMap;

    public SharedPiCalculations(){
        piCalculationHashMap=new HashMap<>();
    }

    public synchronized void addNewCalculation(Long numSteps,Double pi){
        piCalculationHashMap.put(numSteps, pi);
    }

    public synchronized Double getPiCalculation(Long numSteps){
        return piCalculationHashMap.get(numSteps);
        
    } 
    public synchronized boolean hasCalculation(Long numSteps) {
        return piCalculationHashMap.containsKey(numSteps);
    }

}
