import java.util.Random;

class ParallelMergeSort {
    public static void main(String args[]) {
        int threads = Runtime.getRuntime().availableProcessors(); //παιρνω το πληθοσ των επεξαργτων 
        int maxDepth = (int) (Math.log(threads) / Math.log(2)); //οριζω το κατωφλι αποκοπησ τησ ανδρομης-μεγιστο βαθοσ τησ αναδρομης 
        //φτιαψνω εναν τυχαιο πινακα 100 θεσεων 
        int[] arr = new int[100];
        Random random = new Random();
        
        for (int i = 0; i < 100; i++) {
           
            arr[i] = random.nextInt(100);
        }
        
        System.out.println("Given array is");
        printArray(arr);

        //φτιαψνω το νημα κα ιτο ξεικανω
        Thread myThread = new Thread(new MergeSortThread(arr,0,arr.length-1,0,maxDepth));
        myThread.start();

        try{
            myThread.join(); //περιμενω ολα τα νηματα να τελειωσουν
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nSorted array is");
        printArray(arr);
    }

     // A utility function to print array of size n
     static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    static void merge(int arr[], int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        // Create temp arrays
        int L[] = new int[n1];
        int R[] = new int[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        // Merge the temp arrays
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
}

//κωδικας του νηματος 
class MergeSortThread implements Runnable{
    private int[] array;
    private int myStart;
    private int myStop;
    private int depth; //βαθος της ανδρομης του τρεχων νηματος καθε φορα (μετρητης) το χρησιμιποιω καθε αναδρομη να ελεγχω ποτε το τρεχων βαθοσ ειναι ισο με το μεγιστο βαθοσ 
    private int maxDepth; 

    public MergeSortThread(int[] array, int myStart, int myStop, int depth, int maxDepth) {
        this.array = array;
        this.myStart = myStart;
        this.myStop = myStop;
        this.depth = depth;
        this.maxDepth = maxDepth;
    }

    public void run(){
        mergeSort(array, myStart, myStop, depth);
    }

    private void mergeSort(int[] arr, int left, int right, int currentDepth) {
        if (left<right) {
            int mid=left +(right - left) / 2;
            
            if (currentDepth < maxDepth) { //ελεγχω το τρεχων βαθοσ με το μεγιστο 
                Thread leftThread = new Thread(new MergeSortThread(arr, left, mid, currentDepth + 1, maxDepth));
                Thread rightThread = new Thread(new MergeSortThread(arr, mid + 1, right, currentDepth + 1, maxDepth));
                leftThread.start();
                rightThread.start();
                
                try {
                    leftThread.join();
                    rightThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                mergeSort(arr, left, mid, currentDepth + 1);
                mergeSort(arr, mid + 1, right, currentDepth + 1);
            }
            ParallelMergeSort.merge(arr, left, mid, right);
        }
    }
}


 

  
