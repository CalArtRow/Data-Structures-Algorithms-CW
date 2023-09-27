import java.io.*;
import java.util.ArrayList;


class ExtractWords {

    /* 
    This is a mergeSort method that sorts an ArrayList of strings in ascending alphabetical order. 
    It takes as input the ArrayList to be sorted, the startIndex and endIndex position of the sort, 
    and a helperList list for merging.
    */

    public static void mergeSort(ArrayList<String> validWords, int startIndex, int endIndex, String[] helperList, int[] comparisonCount, int[] moveCount) {
        
        // If the startIndex position is greater than or equal to the endIndex position, there is nothing to sort
        if (startIndex < endIndex) {

            /* Calculate the midpoint of the list, and call mergeSort recursively on both halves, 
               until the list is broken down into single elements */
            int middleIndex = (startIndex + endIndex) / 2;
            mergeSort(validWords, startIndex, middleIndex, helperList, comparisonCount, moveCount);
            mergeSort(validWords, middleIndex+1, endIndex, helperList, comparisonCount, moveCount);

           // Merge the two sorted halves, and call merge recursively on the merged halves until the list is sorted
            merge(validWords, startIndex, endIndex, helperList, comparisonCount, moveCount);
        }
    }

    /* 
    This method merges two sorted parts of the list: startIndex to middleIndex and middleIndex+1 to endIndex.
    It takes as input the ArrayList to be merged, the startIndex and endIndex positions of the merge,
    a helperList list for merging, and a counter for the number of comparisons made.
    */

    public static void merge(ArrayList<String> validWords, int startIndex, int endIndex, String[] helperList, int[] comparisonCount, int[] moveCount) {

        // Calculate the midpoint of the list
        int middleIndex = (startIndex + endIndex) / 2;

        // Initialize indices for the two halves of the list, and the current index of the helperList list
        int i = startIndex;
        int j = middleIndex + 1;
        int currentIndex = startIndex;

        // Merge the two sorted halves by comparing the ith and jth words in alphabetical order, the lowest alphabetical value gets put in the helperList list 
        while (i <= middleIndex && j <= endIndex) {
            // Increment the comparison count because a comparison is being made
            comparisonCount[0]++; 
            if (validWords.get(i).compareToIgnoreCase(validWords.get(j)) < 0) {
                helperList[currentIndex++] = validWords.get(i++);
            } else {
                helperList[currentIndex++] = validWords.get(j++);
            }
            // Increment the move count because a move is being made
            moveCount[0]++;
        }

        // Copy the remaining words in the first half of the list to the helperList list
        while (i <= middleIndex) {
            helperList[currentIndex++] = validWords.get(i++);
            // Only increment the move count because no comparison is being made, the remaining words are already in the correct order
            moveCount[0]++;
        }

        // Copy the remaining words in the second half of the list to the helperList list
        while (j <= endIndex) {
            helperList[currentIndex++] = validWords.get(j++);
            // Only increment the move count because no comparison is being made, the remaining words are already in the correct order
            moveCount[0]++;
        }

        // Copy the merged words in helperList to the original ArrayList
        for (int k = startIndex; k <= endIndex; k++) {
            // Only increment the move count because no comparison is being made, the words are already in the correct order
            moveCount[0]++;
            validWords.set(k, helperList[k]);
        }
    }

 public static void main(String args[]) {

    // Create an ArrayList to store the vocabulary words and an ArrayList to store the valid words
    ArrayList<String> vocabulary = new ArrayList<>();
    ArrayList<String> validWords = new ArrayList<>();

    // Try to read in the vocabulary file and add each line (word) to the ArrayList
    try (BufferedReader br = new BufferedReader(new FileReader("./google-10000-english-no-swears.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            vocabulary.add(line.toLowerCase());
        }
    } catch (IOException e) {
        System.err.println(e.getMessage());
    }
    // Try to read in the input file and add each valid word to the ArrayList
    try (BufferedReader br = new BufferedReader(new FileReader("./Input219.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            // Split the line into individual words (including adjacent punctuation)
            for (String word : line.split("\\s+")) {
                // Check if the word (without punctuation) is in the vocabulary
                String wordWithoutPunctuation = word.replaceAll("[^\\w\\s]", "");
                if (wordWithoutPunctuation != null && vocabulary.contains(wordWithoutPunctuation.toLowerCase())) {
                    // Add the original word (with punctuation) to the list
                    validWords.add(word);
            }
        }
        }
    } catch (IOException e) {
        System.err.println(e.getMessage());
    }
    // Print the valid words
    System.out.println(validWords);

    // Initialize variables for sorting performance tracking
    int numValidWords = validWords.size();
    int increment = 100;
    String[] helperList = new String[numValidWords];
    long startTime, endTime, totalTime;
    int[] comparisonCount = new int[1];
    int[] moveCount = new int[1];

    // Create a copy of the valid words list to be used for each iteration of the sort
    String[] copyValidWords = new String[numValidWords];
    // Copy the original list of valid words to the new list
    for (int i = 0; i < numValidWords; i++) {
        copyValidWords[i] = validWords.get(i);
    }


    // Sort the list of valid words using merge sort in increments of 100 and measure the performance of the sort
    for (int k = 100; k <= numValidWords + increment; k += increment) {
        // Copy the original list of valid words to a new list for each iteration of the loop
        for (int i = 0; i < numValidWords; i++) {
            validWords.set(i, copyValidWords[i]);
        }
        // Reset the comparison and move counts for each iteration of the loop
        comparisonCount[0] = 0;
        moveCount[0] = 0;
        // Reset the helperList array for each iteration of the loop
        helperList = new String[numValidWords];
        // set the start time of the sort
        startTime = System.nanoTime();
        // Sort the list of valid words using merge sort
        mergeSort(validWords, 0, Math.min(k, numValidWords)-1, helperList, comparisonCount, moveCount);
        // set the end time of the sort
        endTime = System.nanoTime();
        // calculate the total time it took to sort the List
        totalTime = endTime - startTime;
        // Print out the performance measurements of the sort, including the number of comparisons and moves
        System.out.println("To sort the first " + Math.min(k, numValidWords) + " words into alphabetical order it took " + totalTime + " nanoseconds, " + comparisonCount[0] + " comparisons and " + moveCount[0] + " moves. ");
    
    }
    
    // Print out the fully sorted list of valid words
    System.out.println(validWords);

}

    
}

