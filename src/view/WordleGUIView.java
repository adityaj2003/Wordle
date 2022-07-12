package view;




import java.util.Observable;
import java.util.Observer;


import controller.WordleController;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.WordleModel;
import utilities.Guess;
import utilities.INDEX_RESULT;


/**
 * @author adityaj2003
 * Assignment: Assignment 4
 * File: WordleGUIView.java
 * Course: CSC 335; Spring 2022
 * Purpose:
 *This is the WordleGUiView which is the gui view module for wordle MVC. This is responsible 
 *for running the wordle game in GUI view. It extends application and implements the Observer class
 */


public class WordleGUIView extends Application  implements Observer{


	/**
	 * int variable for defining size of scene
	 */
	private static final int SCENE_SIZE = 800;

/**
 * This keeps track of the letters input till now
 */
	
	private String[] inputText = new String[5];
	/**
	 * Array of guess objects which tracks progress and keeps in sync with model's progress array 
	 */
	
	private Guess[] progress;
	/**
	 * Gets enum INDEX_RESULT for all letters in the alphabet
	 */
	private INDEX_RESULT[] guessedCharacters = new INDEX_RESULT[26];
	/**
	 * Keeps track of the number of letters put in till now
	 */
	private int numLetters = 0;
	/**
	 * Array of buttons of the letters in the English alphabet
	 */
	private Button[] buttonArray = new Button[26];
	/**
	 * Int which keeps track of the number of guesses made till now
	 */
	private int numGuesses = 0; 
	
	/**
	 * This function is the main function which starts the gui view and takes input 
	 *from the user through buttons and textEvents. 
	 *@param Stage: It takes a stage as input and adds different nodes
	 *
	 */
	@Override
	public void start(Stage stage) {
		
	
		/**
		 * Model instance for the current game
		 */
		WordleModel model = new WordleModel();
		model.addObserver(this);
		
		/**
		 * Controller instance for this game
		 */
		WordleController controller = new WordleController(model);
			GridPane pane = new GridPane();
			VBox vbox = new VBox();
			
			vbox.setStyle("-fx-background-color:black;");
			pane.setStyle("-fx-background-color: black");
			pane.setPadding(new Insets(5));
		    pane.setHgap(10);
		    pane.setVgap(10);
		    
		 // The below lines of code are for setting up labels that display 
		 // the letters the user has input
		    for (int i = 0; i<6; i++) {
		    	for (int j = 0; j<5; j++) {
		    		Label label = new Label();
		    		label.setMinWidth(50);
		    		label.setMinHeight(50);
		    		
		    		label.setStyle("-fx-border-color: black; -fx-background-color:#3A3A3C;-fx-font-family:Arial;"
		    				+ "-fx-font-weight: bold;-fx-text-alignment:center;-fx-font-size:1.5em;-fx-font-width:3px");
		    		pane.add(label, j+22,i);
		    	}
		    }
		    
		    GridPane window = new GridPane();
		
		    Text text = new Text("Wordle");
		    GridPane.setHalignment(text, HPos.CENTER);
		    //Sets style for wordle heading
		    text.setStyle("-fx-font-family:Arial; -fx-font-weight:bold; -fx-font-size:4em;-fx-text-alignment:center;-fx-fill:white;-fx-font-width:4px;");
		    window.add(text,25 ,0);
		    
		    //Adds 2 gridPanes to the vbox
		    vbox.getChildren().add(window);
		    vbox.getChildren().add(pane);
		    
		    
		    //FirstKeys is the first row of keys shown at the bottom.
		   //The below lines are adding gaps between the buttons
		    GridPane firstKeys = new GridPane();
		    firstKeys.setHgap(10);
		    firstKeys.setVgap(10);
		    GridPane secondKeys = new GridPane();
		    secondKeys.setHgap(10);
		    secondKeys.setVgap(10);
		    GridPane thirdKeys = new GridPane();
		    thirdKeys.setHgap(10);
		    thirdKeys.setVgap(10);

		
		    vbox.setSpacing(20);
		    
		    String[] letters = new String[] {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		    
		    
		    Scene scene = new Scene(vbox , SCENE_SIZE, SCENE_SIZE);
		    
		    // The below lines of code are for setting up buttons for each letter of the alphabet 
			 // The buttons also take user input.
		    for (int i = 0; i<26; i++) {
		    	  Button button = new Button(letters[i]);
		    	  button.setMinHeight(50);
		    	  button.setMinWidth(40);
		    	  button.setStyle("-fx-border-color: black; -fx-background-color:#818384; -fx-text-fill:white; -fx-font-weight:bold;-fx-font-family:Arial;-fx-font-size:1.5em;"
		    	  		+ "-fx-background-radius:7px;-fx-border-radius:7px;");
		    	  if (i<10) {
		    		  firstKeys.add(button, i+10,0);
		    		  
		    	  }
		    	  else if (i<19) {
		    		  secondKeys.add(button, i, 1);
		    		
		    	  }
		    	  
		    	  else {
		    		  thirdKeys.add(button, i-9, 2);
		    		  
		    	  }
		    	  buttonArray[i] = button;
		    	  //This is the button handler which handles the pressing of onscreen button 
		    	  //and takes input when buttons are pressed
		    	  button.setOnAction(new EventHandler<ActionEvent>() {
		    		    @Override public void handle(ActionEvent event) {
		    		    	Object node = event.getSource();
		    		    	Button button = (Button) node; 
		    		    	if (numLetters < 5) {
		    		    	 inputText[numLetters] = button.getText().toUpperCase();
						        if (numLetters < 5) 
						        	numLetters++;
						    	for (int j = 0; j<5; j++) {
						    		Label label = new Label(inputText[j]);
						    		label.setMinWidth(50);
						    		label.setMinHeight(50);
						    		label.setAlignment(Pos.CENTER);
						    		
						    		label.setStyle("-fx-border-color: black; -fx-background-color:#3A3A3C;-fx-text-fill:white;-fx-font-family:Arial;"
						    				+ "-fx-font-weight: bold;-fx-text-alignment:center;-fx-font-size:1.5em;");
						    		pane.add(label, j+22,numGuesses);
						    	}

					        stage.setScene(scene);
							stage.show();
		    		    }
		    		    }
		    		});
		    }

		    vbox.getChildren().add(firstKeys);
		    vbox.getChildren().add(secondKeys);
		    vbox.getChildren().add(thirdKeys);
	       
	       
	   
	        
	        
			//This is the event handler for keyevent. Users can give input with keyboard as well
			
			scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			    @Override
			    public void handle(KeyEvent event) {
			    	
			    	if (event.getCode().equals(KeyCode.ENTER)) {
			    		
			    		enterGuess(controller,model, pane, stage, scene);
			    		
			    	}
			    	
			    	else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
			    	
			    		 deleteLetter(pane, stage, scene);
			    		
			    	}
			    	
			    	else {
			    		if (event.getCode().isLetterKey())
			    		addLetter(event, pane, stage, scene);
				        
			    	}
			    }
			
			});
			
			
			
			
			//These 2 lines are for scene updation
			stage.setScene(scene);
			stage.show();
			
	}
	
	/**
	 * This function updates the color of the labels and onscreen buttons. It takes in the pane and scene as parameter and 
	 * changes color of labels and letter buttons based on the progress array which contains index_letters enum describing whether 
	 * the letters in guesssed word are correct, incorrect or correct but wrong place
	 * @param pane : This is the GridPane in which we are adding labels
	 * @param stage : This is the stage of the application which is shown when launching
	 * @param scene : This is the scene which contains all labels and buttons
	 * @param word : This is the word that the user has guessed 
	 * @param controller : Controller instance for this game
	 */
	
	public void updateColor(GridPane pane, Stage stage, Scene scene, String word, WordleController controller) {
		
		    	for (int j = 0; j<5; j++) {
		    		//Updates color of label based on the index_result enum. Therefore there are 3 if statements for each case
		    		if (progress[numGuesses].getIndices()[j] == INDEX_RESULT.CORRECT && controller.getAnswer().toLowerCase().charAt(j) == (word.toLowerCase().charAt(j))) {
		    		Label label = new Label(inputText[j]);
		    		
		    		//Below lines are for the transitions of the label after user presses enter.
		    		RotateTransition fadeTransition = new RotateTransition(Duration.seconds(1+j/3), label);
		    		fadeTransition.setAxis(Rotate.X_AXIS);
		    		fadeTransition.setByAngle(360);
		    		
		    		label.setMinWidth(50);
		    		label.setMinHeight(50);
		    		label.setAlignment(Pos.CENTER);
		    		label.setStyle("-fx-border-color: black; -fx-background-color:#538d4e;-fx-text-fill:white;-fx-font-family:Arial;"
		    				+ "-fx-font-weight: bold;-fx-text-alignment:center;-fx-font-size:1.5em;");
		    		fadeTransition.play();
		    		pane.add(label, j+22,numGuesses);
		    		}
		    		
		    		
		    		//Changes label for letters for all letters which are in the answer but at the wrong index.
		    		
		    		else if (progress[numGuesses].getIndices()[j] == INDEX_RESULT.CORRECT_WRONG_INDEX) {
			    		Label label = new Label(inputText[j]);
			    		
			    		RotateTransition fadeTransition = new RotateTransition(Duration.seconds(1+j/3), label);
			    		fadeTransition.setAxis(Rotate.X_AXIS);
			    		fadeTransition.setByAngle(360);
			    		label.setMinWidth(50);
			    		label.setMinHeight(50);
			    		label.setAlignment(Pos.CENTER);
			    		label.setStyle("-fx-border-color: black; -fx-background-color:#b59f3b;-fx-text-fill:white;-fx-font-family:Arial;"
			    				+ "-fx-font-weight: bold;-fx-text-alignment:center;-fx-font-size:1.5em;");
			    		fadeTransition.play();
			    		pane.add(label, j+22,numGuesses);
			    		}
		    		
		    		//Changes label for letters for all letters which are incorrect. 
		    		
		    		else {
			    		Label label = new Label(inputText[j]);
			    		label.setMinWidth(50);
			    		label.setMinHeight(50);
			    		label.setAlignment(Pos.CENTER);
			    		label.setStyle("-fx-border-color: black; -fx-background-color:#3A3A3C;-fx-text-fill:white;-fx-font-family:Arial;"
			    				+"-fx-font-weight: bold;-fx-text-alignment:center;-fx-font-size:1.5em;");
			    		pane.add(label, j+22,numGuesses);
			    		}
		    		
		    		
		    		
		    		stage.setScene(scene);
					stage.show();
		    	}
		    	
		    	for (int i = 0; i<26; i++) {
		    		//This loop changes color of button based on user input and index_result enum of guessedCharacters 
		    		if (guessedCharacters[i] == INDEX_RESULT.CORRECT) {
		    			buttonArray[i].setStyle("-fx-background-color:#538d4e; -fx-text-fill:white; -fx-font-weight:bold;-fx-font-family:Arial;-fx-font-size:1.5em;"
		    					+ "-fx-background-radius:7px;-fx-border-radius:7px;");
		    		}
		    		if (guessedCharacters[i] == INDEX_RESULT.CORRECT_WRONG_INDEX) {
		    			buttonArray[i].setStyle("-fx-background-color:#b59f3b; -fx-text-fill:white; -fx-font-weight:bold;-fx-font-family:Arial;-fx-font-size:1.5em;"
		    					+"-fx-background-radius:7px;-fx-border-radius:7px;");
		    		}
		    		
		    		if (guessedCharacters[i] == INDEX_RESULT.INCORRECT) {
		    			buttonArray[i].setStyle("-fx-background-color:#3a3a3c; -fx-text-fill:white; -fx-font-weight:bold;-fx-font-family:Arial;-fx-font-size:1.5em;"
		    					+"-fx-background-radius:7px;-fx-border-radius:7px;");
		    		}
		    		
		    			
		    		
		    	}
		    	
		    	 
		    	
		    	
		    	
		
	}
	
	/**
	 * This function is called when user presses enter to mark that he is guessing the current word input. 
	 * It calls  controller's makeGuess and changes everything in the gui after the guess changes the progress 
	 * and guessed characters in model. It updates the numGuess variable and resets the current guess array.
	 * @param controller: Controller instance for this iteration of game
	 * @param model : Model instance for this iteration of game
	 * @param pane : GridPane which contains all labels and buttons
	 * @param stage : Stage is the stage which is shown when launching application
	 * @param scene : Scene which is set to stage and keeps refreshing
	 */
	
	public void enterGuess(WordleController controller, WordleModel model, GridPane pane, Stage stage, Scene scene) {
		
			
		
		
			String word = "";
			for (int i = 0; i<5; i++) {
				word+=inputText[i];
			}
			

				
			try {
			controller.makeGuess(word);
			
			update(model, progress);
			//Updates color after a guess has been entered
			updateColor(pane, stage, scene, word, controller);
		
		numGuesses++;
		numLetters = 0;
		for (int i = 0; i<5; i++ )
		{
			inputText[i] = null;
		}
		
			if (controller.isGameOver()) {
				//Alert for when game is over
				Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText("CONGRATULATIONS!");
				a.setContentText("The correct word was "+controller.getAnswer());
				a.show();
				
			}
			}
			
			catch(IllegalArgumentException e) {
				//This is the alert for when an illegal word has been put in 
				Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText("Illegal word");
				a.setContentText("The word should be 5 letters and in dictionary.txt");
				a.show();
				
			}
			
			
		
		}
		
		
	
	
	/**
	 * This function is called when backspace is entered. Deletes one letter from the word input in the gui.
	 * Resets the label which displays the guess, the user is typing currently. 
	 * by the user
	 * @param pane
	 * @param stage
	 * @param scene
	 */
	public void deleteLetter(GridPane pane, Stage stage, Scene scene) {
		if (numLetters >0)
		inputText[numLetters-1] = null; //Removes the last letter user input in the array of inputText and updates labels
		 if (numLetters > 0)
			 numLetters--;
		for (int j = 0; j<5; j++) {
			//These arrays are recurring but required as they change color of each label in current progress based on guessedCharacters array
   		Label label = new Label(inputText[j]);
   		label.setMinWidth(50);
   		label.setMinHeight(50);
   		label.setAlignment(Pos.CENTER);
   		label.setStyle("-fx-border-color: black; -fx-background-color:#3A3A3C;-fx-text-fill:white;"
   				+ "-fx-font-family:Arial;"
   				+ "-fx-font-weight: bold;-fx-text-alignment:center;-fx-font-size:1.5em;");
   		pane.add(label, j+22,numGuesses);
   	}
	

   stage.setScene(scene);
	stage.show();
	}

	/**
	 * This functions is called when the user enters letters either through buttons or key events. 
	 * Displays the letters entered by the user by updating labels of each letter that is added and 
	 * adding the input letter to letter array inputText. 
	 * @param event : Event is the keyEvent which gives us the letter user pressed
	 * @param pane : GridPane containing all labels and buttons
	 * @param stage : Stage which is shown when application is launched
	 * @param scene : Scene which is set and refreshed after each event
	 */
	
	public void addLetter(KeyEvent event, GridPane pane, Stage stage, Scene scene) {
		if (numLetters < 5) {
		inputText[numLetters] = event.getText().toUpperCase();
        if (numLetters < 5) 
        	numLetters++;
    	for (int j = 0; j<5; j++) {
    		//Adds new label with updated letters from user input at the previous coordinates
    		Label label = new Label(inputText[j]);
    		label.setMinWidth(50);
    		label.setMinHeight(50);
    		label.setAlignment(Pos.CENTER);
    		
    		label.setStyle("-fx-border-color: black; -fx-background-color:#3A3A3C;-fx-text-fill:white;-fx-font-family:Arial;"
    				+ "-fx-font-weight: bold;-fx-text-alignment:center;-fx-font-size:1.5em;");
    		pane.add(label, j+22,numGuesses);
    	}

    stage.setScene(scene);
	stage.show();
	}
	}
	
	/**
	 * Update function which updates progress and guessedCharacters as guiView is observer of 
	 * model class
	 */

	@Override
	public void update(Observable model, Object arg) {
		this.progress = ((WordleModel) model).getProgress();
		this.guessedCharacters = ((WordleModel) model).getGuessedCharacters();
	}

}
