package SortingTechniques;

import java.util.Arrays;
import java.util.Random;

public class Test {

	public static void main(String[] args) {
		
		int[] inverslyArray= new int[10000];
		int[] shuffledArray= new int[10000];
		int[] sortedArray= new int[10000];
		int startTime;
		int endTime;
		float finalTime;
		
		for(int i=0; i<sortedArray.length ; i++) {
			sortedArray[i]=i;
			shuffledArray[i]=i;
		}

		
		int elements=1;
		
		while(elements<=3) {			
		shuffleArray(shuffledArray);
		inverseArray(inverslyArray);
				
		switch(elements) {
			case(1):
				System.out.println("Bubble sort:\n");
				System.out.println("Inversly Array:");
				startTime = (int) System.nanoTime();
				BubbleSort(inverslyArray);
				endTime = (int) System.nanoTime();
				finalTime = (endTime - startTime)/ 1_000_000.0f;
			    System.out.println("Time taken: " + finalTime + " milliseconds \n");
			    
			    System.out.println("Sorted Array:");
				startTime = (int) System.nanoTime();
				BubbleSort(sortedArray);
				endTime = (int) System.nanoTime();
				finalTime = (endTime - startTime)/ 1_000_000.0f;
			    System.out.println("Time taken: " + finalTime + " milliseconds \n");
			    
			    System.out.println("Shuffelled Array:");
				startTime = (int) System.nanoTime();
				BubbleSort(shuffledArray);
				endTime = (int) System.nanoTime();
				finalTime = (endTime - startTime)/ 1_000_000.0f;
			    System.out.println("Time taken: " + finalTime + " milliseconds \n"+
			    					"\n ----------------------");
		    break;
		    
			case(2):
				System.out.println("Quick sort:\n");
				System.out.println("Inversly Array:");
				startTime = (int) System.nanoTime();
				QuickSort(inverslyArray,0,9999);
				endTime = (int) System.nanoTime();
				finalTime = (endTime - startTime)/ 1_000_000.0f;
			    System.out.println("Time taken: " + finalTime + " milliseconds \n");
			    
			    System.out.println("Sorted Array:");
			    startTime = (int) System.nanoTime();
			    QuickSort(sortedArray,0,9999);
				endTime = (int) System.nanoTime();
				finalTime = (endTime - startTime)/ 1_000_000.0f;
			    System.out.println("Time taken: " + finalTime + " milliseconds \n");
			    
			    System.out.println("Shuffelled Array:");
				startTime = (int) System.nanoTime();
				QuickSort(shuffledArray,0,9999);
				endTime = (int) System.nanoTime();
				finalTime = (endTime - startTime)/ 1_000_000.0f;
			    System.out.println("Time taken: " + finalTime + " milliseconds"+
			    					"\n ----------------------");
			    break;
			    
			case(3):
				System.out.println("Counting sort: \n");
				System.out.println("Inversly Array:");
				startTime = (int) System.nanoTime();
				CountingSort(inverslyArray);
				endTime = (int) System.nanoTime();
				finalTime = (endTime - startTime)/ 1_000_000.0f;
			    System.out.println("Time taken: " + finalTime + " milliseconds \n");
			   
			    System.out.println("Sorted Array:");
			    startTime = (int) System.nanoTime();
			    CountingSort(sortedArray);
				endTime = (int) System.nanoTime();
				finalTime = (endTime - startTime)/ 1_000_000.0f;
			    System.out.println("Time taken: " + finalTime + " milliseconds \n");
			    
			    System.out.println("Shuffelled Array:");
				startTime = (int) System.nanoTime();
				CountingSort(shuffledArray);
				endTime = (int) System.nanoTime();
				finalTime = (endTime - startTime)/ 1_000_000.0f;
			    System.out.println("Time taken: " + finalTime + " milliseconds \n"+
			    		            "\n ----------------------");
			    break;
		}
		elements++;
	  }
	}
	
	
	public static void inverseArray(int [] array) {
		int index =0;
		for(int i=array.length; i>0 ; i--) {
			array[index]=i;
			index++;
		}
		
	}
	
	public static void shuffleArray(int[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);

            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

	public static void QuickSort(int [] arr,int low,int high) {
		int comparisons=0;
		int interChanges=0;
		if (low < high) {
		int mid =(int) Math.round(low+((high-low)/2));
		int pivot =arr[mid];
				
		arr[mid]=arr[high];
		arr[high]=pivot;
		
		int pointer =low-1;
		for(int i=low;i<high-low;i++) {
			comparisons++;
			if(arr[i]<pivot) {
				pointer++;
				int value =arr[pointer];
				arr[pointer]=arr[i];
				arr[i]=value;
				interChanges++;
				
			}
		}
	
		arr[high]=arr[pointer+1];
		arr[pointer+1]=pivot;
		QuickSort(arr, low,pointer);
		QuickSort(arr,pointer+2, high);

		}
		
		if (low == 0 && high == arr.length - 1) { 
	        System.out.println("Interchanges = " + interChanges + "\nComparisons = " + comparisons);
	    }
	}

	public static void BubbleSort(int[] arr) {
		int comparisons=0;
		int interChanges=0;
		for (int pars = 0; pars < arr.length; pars++ ) 
			 for (int i = 0; i < arr.length - 1; i++ ) { 
				 comparisons++;
				 if ( arr[ i ] > arr[ i + 1 ] ) { 
					int temp = (int) arr[i]; 
					arr[i] = arr[i + 1]; 
					arr[i + 1] = temp; 
					interChanges++;
				 }
			 }
		
		System.out.println("interchanges=" + interChanges + "\ncomparisons=" + comparisons);
		
	}
	public static void CountingSort(int [] arr) {
		int min= Arrays.stream(arr).min().orElse(0);
		int max= Arrays.stream(arr).max().orElse(Integer.MAX_VALUE);		
		int [] countArray = new int[max-min+1];
		
		for(int value:arr) {
			countArray[value-min]++;
		}
		int arrayIndex=0;
		for(int i=0; i< max-min +1;i++) {
			while(countArray[i]>0) {
				arr[arrayIndex]=i+min;
				countArray[i]--;
				arrayIndex++;
			}
		}
	
	}
}
