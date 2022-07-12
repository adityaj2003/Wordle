package model;

/**
 * Author: Aditya Jadhav 
 * File: WordleModel.java
 * Assignment: Assignment 4
 * Course: CSc 335; Spring 2022
 * Purpose: This is the model section of the wordle assignment.
 * 			It sets the data structures to be used in the controller and view and 
 * 			It holds all the private data structures 
 *
 */

import java.util.Observable;

import utilities.Guess;
import utilities.INDEX_RESULT;
import java.io.*;
import java.util.*;


public class WordleModel extends Observable {
	
	/**
	 * private variable for the filename of dictionary from which words are taken
	 */
	private static final String FILENAME = "dictionary.txt";
	
	/**
	 * Maintains the progress the user has made so far. This array should have
	 * as many indices as there are turns/guesses for the user. Indices for turns
	 * that the user has not had yet (that are in the future) should be indicated
	 * by a 'null' value.
	 */
	private Guess[] progress = new Guess[6];
	
	/**
	 * private variable holiding the answer for current game cycle
	 */
	private String answer;
	/* 
	 * Maintains an array of INDEX_RESULTs for the guessed characters. There
	 * should be 26 indices in this array, one for each character in the english
	 * alphabet. Before a character has been guessed, its position in the array
	 * should hold the value 'null'.
	 */
	private INDEX_RESULT[] guessedCharacters = new INDEX_RESULT[26];
	
	/**
	 * ArrayList for the words in dictionary
	 */
	private ArrayList<String> words = new ArrayList<String>();
	
	/**
	 * ArrayLists for holding alphabets of each of the following type
	 */
	public ArrayList<Character> correct = new ArrayList<Character>();
	public ArrayList<Character> incorrect = new ArrayList<Character>();
	public ArrayList<Character> unguessed = new ArrayList<Character>();
	public ArrayList<Character> correctWrongIndex = new ArrayList<Character>();

	public WordleModel() { 

		try {
			Scanner s = new Scanner(new File("Dictionary.txt"));
			while(s.hasNext()) {
				String word = s.nextLine();
				words.add(word.toLowerCase());
			}
			s.close();
			Random r = new Random();
			int index = r.nextInt(words.size());
			this.answer = words.get(index);
			}
			catch (FileNotFoundException e) {
				return;
			}
	}

	/**
	 * This is the function which updates all data structures after user makes a guess.
	 * It updates the guessed character array with the correct index_result enum for
	 * every alphabet. Also updates arrays of each type which are guessed, unguessed, correct
	 * and incorrect
	 * @param guess which is a string input made by the user
	 */
	@SuppressWarnings("deprecation")
	public void makeGuess(int guessNumber, String guess) {
		checkGuess(guess);
		
		
		
		for (int i = 0; i<guess.length(); i++) {
			if (answer.contains(String.valueOf(guess.charAt(i))) && guessedCharacters[Character.toUpperCase(guess.charAt(i))-'A'] != INDEX_RESULT.CORRECT) {
				guessedCharacters[Character.toUpperCase(guess.charAt(i))-'A'] = INDEX_RESULT.CORRECT_WRONG_INDEX;
				
			}
			
			if (answer.charAt(i) == guess.charAt(i)) {
				guessedCharacters[Character.toUpperCase(guess.charAt(i))-'A'] = INDEX_RESULT.CORRECT;
			}
			
			if (answer.contains(String.valueOf(guess.charAt(i))) == false) {
				guessedCharacters[Character.toUpperCase(guess.charAt(i))-'A'] = INDEX_RESULT.INCORRECT;
			}
				
		}
		
		
		
		INDEX_RESULT[] indices = new INDEX_RESULT[5];
		for (int j = 0; j<guess.length();j++) {
			indices[j] = guessedCharacters[Character.toUpperCase(guess.charAt(j))-'A'];
		}
		Guess guessObj = new Guess(guess,indices, guess.equalsIgnoreCase(answer));
		progress[guessNumber] = guessObj;
		setChanged();
		notifyObservers(progress);
		notifyObservers(guessedCharacters);
		
		
	}
	
	/**
	 * Private function to check if guess given is Illegal or not
	 * @param guess : Guess object containing user's guess
	 */
	private void checkGuess(String guess) {
		for (int i = 0; i < guess.length(); i++) {
            if (Character.isDigit(guess.charAt(i)) || guess.length()!=5) {
            	throw new IllegalArgumentException("Word contains numeric value.");
            }
        }
		if(!this.words.contains(guess.toLowerCase()) )
		{
			throw new IllegalArgumentException("Word not in dictionary.");
		}
	}

	public String getAnswer() {
		
		return answer;
		
	}
	
	/**
	 * Returns the array of index_result enum for all alphabets
	 * @return Array of index result enums
	 */

	public INDEX_RESULT[] getGuessedCharacters() {
		
		return guessedCharacters;
		
	}
	
	/**
	 * Returns the progress array 
	 * @return Guess[] progress
	 */
	public Guess[] getProgress() {
		
		return progress;
		
	}
	
	

}
