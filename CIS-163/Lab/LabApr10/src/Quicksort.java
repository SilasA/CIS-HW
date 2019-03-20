// Taken from 
// http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html
public class Quicksort  {
    private int[] numbers;
    private int number;

    public void sort(int[] values) {
        // check for empty or null array
        if (values ==null || values.length==0){
            return;
        }
        this.numbers = values;
        number = values.length;
        quicksort(0, number - 1);
    }

    private void quicksort(int low, int high) {
        System.out.println("low: "+low+" high: "+high);
        if (low == high) {
             printArray(numbers);
            return;
        }
        if (low == high-1) {
            if (numbers[low] <= numbers[high]) {
                 printArray(numbers);
                return;
            }
            else {
                exchange(low,high);
                 printArray(numbers);
                return;
            }
        }

        int i = low+1;
        int j = high;
        // Get the pivot element from the leftmost position in the list
        int pivot = numbers[low];

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller than the pivot
            // element then get the next element from the left list
            while (numbers[i] < pivot && i < j) {
                i++;
            }

            // If the current value from the right list is larger than the pivot
            // element then get the next element from the right list
            while (numbers[j] > pivot) {
                j--;
            }

            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller than the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                exchange(i, j);
                i++;
                //j--;
            }
        }
        if (i > high) { // The pivot is the largest element
            j = high;
        }
            
        exchange(low,j);
        printArray(numbers);

       
        // Recursion
        if (low < j-1)
            quicksort(low, j-1);
        if (i < high)
            quicksort(i, high);
    }

    private void exchange(int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }

    private void printArray(int [] array) {
        for(int i = 0;i < array.length;i++) {
            System.out.print(array[i]+" ");
        }
        System.out.println();
    }
}
