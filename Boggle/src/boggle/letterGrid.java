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

public class letterGrid {
	static int X = 5; //number of rows on letter grid
	static int Y = 5; //number of columns on letter grid
	static char[][] letterGrid = new char[5][5]; //2d array for the letter grid

	static String[][] dies = new String[5][5];
	
	static String[] diceLetters = {"AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", //letters for the 25 dies
									"AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCNSTW", 
									"CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DHHLOR", 
									"DHHNOT", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU", 
									"FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU",};

	public static void letterRandomizer (String[][] dies) { //method randomizes the letter board
		Random rand = new Random(); //random number function
		
		for (int row = 0; row < dies.length; row++) { //for each row
			for (int col = 0; col < dies[row].length; col++) { // for each column 
				for (int k = 0; k < 25; k++) { //for loop for the letters
					dies[row][col] = diceLetters[k]; //puts random letters into each element of the 2d array 
				}
				System.out.print(dies[row][col] + " " ); //prints value found at the row 
			}
			System.out.println(); //prints a blank line 
		}
	}
	
	public static void main(String[] args) {
		letterRandomizer(dies);

	}
}

