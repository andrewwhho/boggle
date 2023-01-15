package boggle;
import java.util.*;

import javax.swing.JLabel;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.*; 

/*
 * Boggle 
 * Andrew H 
 * due June 14th, 2021
 */

public class boggleGame {
	static int X = 5; //number of rows on letter grid
	static int Y = 5; //number of columns on letter grid
	static ArrayList<String> words = new ArrayList<String>(); //array list for dictionary 
	static char[][] letterGrid = new char[5][5]; //2d array for the letter grid

	public static void main(String[] args) throws FileNotFoundException {
		boolean[][] letterGridVisited = new boolean[5][5]; //2d array to show if the letter has been visited during the search process

		letterRandomizer(letterGrid, letterGridVisited); //randomizes letter grid
		dictionaryRead(words); //reads dictionary into array list
		System.out.println(firstMove()); //returns heads or tails for the first move
		System.out.println(wordVerification("chicken")); //returns boolean value to verify if word exists in dictionary
		
		System.out.println("Following words of dictionary are present");
        findWords(letterGrid);
		
		findWordsUtil(letterGrid, letterGridVisited, 5, 5, "pineapple", words);
		
	}

	public static void letterRandomizer (char[][] array2d, boolean[][] array2dVisited) { //method randomizes the letter board
		char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}; //puts all 26 letters of the alphabet into array 
		Random rand = new Random(); //random number function 

		for (int row = 0; row < array2d.length; row++) { //for each row
			for (int col = 0; col < array2d[row].length; col++) { // for each column 
				for (int l = 0; l < 27; l++) { //for loop for the letters
					array2d[row][col] = letters[rand.nextInt(26)]; //puts random letters into each element of the 2d array 
				}
				array2dVisited[row][col] = false;
				System.out.print(array2d[row][col] + " " ); //prints value found at the row 
			}
			System.out.println(); //prints a blank line 
		}
		for (int row = 0; row < array2d.length; row++) { //for each row
			for (int col = 0; col < array2d[row].length; col++) { // for each column 
				array2dVisited[row][col] = false;
				// System.out.print(array2dVisited[row][col] + " " ); //prints value found at the row 
			}
			// System.out.println(); //prints a blank line 
		}
	}

	public static String firstMove() { //method returns heads or tails 
		Random rand = new Random();
		int randomNum = rand.nextInt(2); //generates random number between 1-2 
		if (randomNum == 1) { //if the number is 1, it returns heads
			return "heads";
		}
		else {
			return "tails"; //if number is 2, it returns tails 
		}
	}
	
	public static void dictionaryRead(ArrayList<String> words) throws FileNotFoundException  { //method to read dictionary words into array list
		File input = new File("dictionary.txt"); //uses the .txt file as an input
		Scanner fileReader = new Scanner(input); //file reader

		while(fileReader.hasNext()) { //checks if the text file has next lines to read
			words.add(fileReader.nextLine()); //adds whatever is on the next line to the array list 
		}
		fileReader.close();
		
		String[] wordsArray = new String[words.size()]; //creates array with array list size as index size 
		for (int i = 0; i < words.size(); i++) { //for loop for array 
			wordsArray[i] = words.get(i); //puts all of the elements in the array list into a regular array
		}
		for (int i = 0; i <= words.size(); i++) { 
			//System.out.println(wordsArray[i]); //prints all of the array elements just to confirm the array contents
		}

	}

	public static boolean wordVerification (String userWord) { //method to verify if user input matches with the dictionary
		for (int i = 0 ;i < words.size(); i++) { //for loop for array list size 
			if (userWord.equals(words.get(i))) {
				return true; //if the user inputed word matches with a word in the dictionary, it returns true 
			}
		}
		return false; //other wise it returns false
	}
	
	public static void findWordsUtil(char letterGrid[][], boolean visited[][], int i, int j, String word, ArrayList<String> words) {
		
		visited[i][j] = true; 
		word = word + letterGrid[i][j];
		
		if (wordVerification(word)) {
			System.out.println(word);
		}
		
		 for (int row = i - 1; row <= i + 1 && row < X; row++) {
	            for (int col = j - 1; col <= j + 1 && col < Y; col++) {
	                if (row >= 0 && col >= 0 && !visited[row][col]) {
	                    findWordsUtil(letterGrid, visited, row, col, word, words);
	                }
	            }
		 }
		 
		 word = "" + word.charAt(word.length()-1);
		 visited[i][j] = false; 

	}
	
	public static void findWords(char letterGrid[][]) {
		
		boolean visited[][] = new boolean[X][Y];
		
		String word = "";
		
		for (int i = 0; i < X; i++) {
			for (int j = 0; j < Y; j++) {
				findWordsUtil(letterGrid, visited, i, j, word, words);
			}
		}
	}
		
	
	/* Mark current cell as visited and append current character
	// to str
	visited[i][j] = true;
	word = word + boggle[i][j];

	// If str is present in dictionary, then print it
	if (wordVerification(word, words)) {
		System.out.println(word);
	}

	// Traverse 8 adjacent cells of boggle[i][j]
	for (int row = i - 1; row <= i + 1 && row < 5; row++) {
		for (int col = j - 1; col <= j + 1 && col < 5; col++) {
			if (row >= 0 && col >= 0 && !visited[row][col]) {
				findWordsUtil(boggle, visited, row, col, word, words);
			}
		}
	}
	// Erase current character from string and mark visited
	// of current cell as false
	word = "" + word.charAt(word.length() - 1);
	visited[i][j] = false;
	*/
}
