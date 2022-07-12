package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import utilities.Guess;
import utilities.INDEX_RESULT;


import java.util.Scanner;

import controller.WordleController;
import model.WordleModel;

import utilities.Guess;

/**
 * @author adityaj2003
 * File: WordleTextView.java
 * Assignment: Assignment 4
 * Course: CSC 335; Spring 2022
 * Purpose:
 *This is the WordleGUiView which is the gui view module for wordle MVC. This is responsible 
 *for running the wordle game in text view. It implements the Observer class. It is an observer of the WordleModel
 *class. It has an update function which updates progress array and letterGuessed array.  
 */


public class WordleTextView implements Observer{
	
	private Guess[] progress;
	private static INDEX_RESULT[] guessedCharacters = new INDEX_RESULT[26];
	
	/**
	 * ArrayLists for holding alphabets of each of the following type
	 */
	public static ArrayList<Character> correct = new ArrayList<Character>();
	public static ArrayList<Character> incorrect = new ArrayList<Character>();
	public static ArrayList<Character> unguessed = new ArrayList<Character>();
	public static ArrayList<Character> correctWrongIndex = new ArrayList<Character>();
	/**
	 * Main class which is responsible for running the wordle program. It takes user input and is responsible for 
	 * making guesses in the model instance and controller instance. Also catches illegal argument error. Is responsible for calling the other 
	 * fcuntions for printing progress array and letters guessed to the user
	 * @param args - Arguments given to run the program.
	 */
	
	
	public void main(String[] args) {
		String tempControl = "YES";
		
		while (tempControl.toUpperCase().equals("YES")) {
			
		
		
		WordleModel model = new WordleModel();
		
		WordleController controller = new WordleController(model);
		model.addObserver(this);
		String answer = controller.getAnswer();
		Scanner s = new Scanner(System.in); 
		
		for (int i = 0; i<6; i++) {
			
			update(model, progress);
			System.out.print("Enter a guess:");
			String guess = s.nextLine();
			/**
			 * Below lines throw an error when an illegal word is provided by user
			 */
			
			
			/**
			 * These lines are responsible for printing the wordle view
			 */
			guess = guess.toLowerCase();
			
			try {
			controller.makeGuess(guess);
			}
			catch(IllegalArgumentException e) {
				
				System.out.println("Illegal Word. Try again");
				continue;
				
			}
			

			System.out.println(printProgress(progress,answer));
			
			/**
			 * Below lines responsible for printing letters which are guessed, unguessed and incorrect letters
			 */
			
			System.out.println(printCharacters());
			
			/**
			 * Displays when the game has ended
			 */
			System.out.println();
			if (controller.isGameOver() == true) {
				System.out.println("Good game! The word was "+model.getAnswer()+".");
				break;
			}
		}
		/**
		 * Asks the user if they want to play again
		 */
		System.out.print("Would you like to play again? ");
		tempControl = s.nextLine();
		
		}
	}
	
	/**
	 * Prints progress array. It displays each letter in the guessed word differently based on whether it 
	 * is correct, incorrect or correct letter but wrong index. 
	 * @param progress : Progress array which contains index_letter enums for each letters of guessed word
	 * @param answer : The correct answer for this game of wordle. 
	 * @return String containing printable format for progress
	 */
	
	public static String printProgress(Guess[] progress, String answer) {
		String line ="";
		for (int k = 0; k<6; k++) {
			
			if (progress[k] == null) 
				line+="_ _ _ _ _ \n";
			else {
			for (int j = 0; j<5; j++) {
				
				INDEX_RESULT temp = progress[k].getIndices()[j];
				if (temp == INDEX_RESULT.CORRECT) {
					if (Character.toUpperCase(progress[k].getGuess().charAt(j)) == Character.toUpperCase(answer.charAt(j)))
						line+= String.valueOf(Character.toUpperCase(progress[k].getGuess().charAt(j)))+" ";
					else
						line+= String.valueOf(Character.toLowerCase(progress[k].getGuess().charAt(j)))+" ";
				}
					
					
				if (temp == INDEX_RESULT.CORRECT_WRONG_INDEX) 
					line+= String.valueOf(Character.toLowerCase(progress[k].getGuess().charAt(j)))+" ";
				if (temp == INDEX_RESULT.INCORRECT) 
					line+= "_ ";
			}
			line+="\n";
			}
			
			
		}
		return line;
	}
	/**
	 * Prints all characters in respective arrays
	 * @param model : WordleModel instance which is being used by the controller. 
	 * @return String of characters in printable format
	 * 
	 */
	
	public static String printCharacters() {
		String answer = "";
		correctlyGuessed();
		incorrectlyGuessed();
		notGuessed();
		correctlyGuessedWrongIndex();
		answer += "Unguessed ";
		answer+= unguessed+"\n";
		answer += "Incorrect ";
		answer+= incorrect+"\n";
		answer += "Correct ";
		answer+= correct+"\n";
		if (correctWrongIndex.isEmpty() == false) {
			answer+="Correct letter, wrong index";
			answer+= correctWrongIndex + "\n";
		}
		return answer;
	}
	
	/**
	 * Updates the array correct with all correctly guessed alphabets
	 */
	public static void correctlyGuessed() {
		
		correct.clear();
		for (int l = 0; l<26; l++) {
			if (guessedCharacters[l] == INDEX_RESULT.CORRECT) {
				char ch = (char) (l+65);
				correct.add(ch);
			}
				
		}
	}
	
	/**
	 * Updates the array incorrect with all incorrectly guessed alphabets
	 */
	
	public static void incorrectlyGuessed() {
		incorrect.clear();
		for (int l = 0; l<26; l++) {
			if (guessedCharacters[l] == INDEX_RESULT.INCORRECT) {
				char ch = (char) (l+65);
				incorrect.add(ch);
			}
				
		}
	}
	
	/**
	 * Updates the array unguessed with all unguessed alphabets
	 */
	public static void notGuessed() {
		unguessed.clear();
		for (int l = 0; l<26; l++) {
			if (guessedCharacters[l] == null) {
				char ch = (char) (l+65);
				unguessed.add(ch);
			}
				
		}
	}
	
	/**
	 * Updates the array correct_wrong_index with all correctly guessed but wrong index alphabets
	 */
	
	public static void correctlyGuessedWrongIndex() {
		correctWrongIndex.clear();
		for (int l = 0; l<26; l++) {
			if (guessedCharacters[l] == INDEX_RESULT.CORRECT_WRONG_INDEX) {
				char ch = (char) (l+65);
				correctWrongIndex.add(ch);
			}
				
		}
	}
	
	@Override
	public void update(Observable model, Object progress) {
		this.progress = ((WordleModel) model).getProgress();
		this.guessedCharacters = ((WordleModel) model).getGuessedCharacters();
	}
	
}
