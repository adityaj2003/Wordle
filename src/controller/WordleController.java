package controller;

/**
 * @author Aditya Jadhav
 * File: WordleController.java
 * Assignment: Assignment 4
 * Course: CSc 335; Spring 2022
 * Purpose: This is the controller section of the wordle assignment.
 * 			It manipulates the data structures set in wordle model and 
 * 			uses them to feed data to view.
 */


import utilities.Guess;
import utilities.INDEX_RESULT;
import java.io.*;
import java.util.*;

import model.WordleModel;



public class WordleController {
	
	/**
	 * Instantiates a wordle model from which data structures are taken
	 */
	
	private WordleModel model;
	
	
	
	/**
	 * Keeps track of the number of guesses made
	 */
	private int numGuesses = 0;
	

	/**
	 * Constructor for this class. Takes in a WordleModel and 
	 * puts in null values initially to signify no guesses made.
	 * @param model : Model inherited from WordleModel class and used in controller class
	 */
	public WordleController (WordleModel model) {

		this.model = model;
		for (int i = 0; i<6; i++)
			model.getProgress()[i] = null;
		
		
	} 
	
	/**
	 * Checks if the game is over. Game is over when correct guess is made
	 * or the user runs out of guesses
	 * @return Boolean indicating whether games has ended
	 */
	
	public boolean isGameOver() {

		if (model.getProgress()[numGuesses-1].getIsCorrect() == true) {
			return true;
		}
		
		if (numGuesses == 6)
			return true;
		return false;
	}
	
	/**
	 * Returns the answer of the current wordle model
	 * @return String object containing the answer
	 */
	public String getAnswer() {
		return model.getAnswer();
	}
	
	/**
	 * Updates progress after user makes a guess by calling model's makeGuess. 
	 * @param guess is the word the user inputs into wordle
	 * @return Guess object containing details of the users guess
	 */
	public void makeGuess(String guess) {
		model.makeGuess(numGuesses, guess.toLowerCase());
		numGuesses+=1;
	}

}
