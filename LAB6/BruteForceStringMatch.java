import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class BruteForceStringMatch {

    public static void main(String args[]) throws IOException {

        int numThreads =1;

        // if (args.length != 2) {
        // 	System.out.println("BruteForceStringMatch  <file name> <pattern>");
        // 	System.exit(1);
        // }

        //ΘΑ ΒΑΛΩ ΕΔΩ MANUALLY ΝΑ ΑΛΛΑΖΩ ΤΟ ΑΡΧΕΙΟ ΕΒΑΛΑ ΝΑ ΕΙΝΑΙ 16 ΦΟΡΕΣ ΤΟ ΑΡΧΙΚΟ ΑΡΧΕΙΟ Ε.COLI
        //283 MB (296,876,160 bytes)
        String filePath = "C:\\Users\\perik\\Downloads\\Week6\\bigger_sixteentimes.txt";
        String fileString = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);

        char[] text = fileString.toCharArray();
        int n = text.length;
        String patternString = "tacccagattatcgccatcaacgg";
        char[] pattern = patternString.toCharArray();
        int m = patternString.length();

        int matchLength = n - m;
        char[] match = new char[matchLength + 1];
        for (int i = 0; i <= matchLength; i++) {
            match[i] = '0';
        }

        MyThread[] threads = new MyThread[numThreads];
        int[] localCount =  new int[numThreads];

        int block  = matchLength/numThreads;
        int start=0;
        int stop=0;

        long startTime = System.currentTimeMillis();
        for (int i=0;i<numThreads;i++){
            start=i*block;
            stop = i*block+block;
            if (i == numThreads - 1) stop = matchLength;
            threads[i] = new MyThread(start,stop,text,pattern,match,localCount,i);
            threads[i].start();
        }

        try {
            for (int i = 0; i < numThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int total=0;
        for(int i =0 ; i<numThreads;i++){
            total=total+localCount[i];
        }

        // get current time and calculate elapsed time
        long elapsedTimeMillis = System.currentTimeMillis()-startTime;
        System.out.println("time in ms = "+ elapsedTimeMillis);
        System.out.println("Total matches: "+total);
    }
}

class MyThread extends Thread{
    private int myStart;
    private int myStop;
    private char[] text;
    private char[] pattern;
    private char[] match;
    private int m;
    private int localCount[];
    private int index;


    public MyThread(int myStart,int myStop,char[] text,char[] pattern,char[] match,int[] localCount,int index){
        this.myStart=myStart;
        this.myStop=myStop;
        this.text=text;
        this.pattern=pattern;
        this.match = match;
        this.m = pattern.length;
        this.localCount=localCount;
        this.index=index;
    }

    public void run(){
        int count = 0;

        for (int j = myStart; j < myStop; j++) {
            int i;
            for (i = 0; i < m && pattern[i] == text[i + j]; i++);
            if (i >= m) {
                match[j] = '1';
                count++;
            }
        }

        localCount[index] = count;
    }
}