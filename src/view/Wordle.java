package view;

import javafx.application.Application;
import view.WordleTextView;
import view.WordleGUIView;

public class Wordle {
	
    public static void main(String[] args) {
    	if (args[0].equals("text")) {
    		String[] arr = new String[0];
    		WordleTextView view = new WordleTextView();
    		view.main(arr);
    	}
    	
    	if (args[0].equals("gui")) {
    		Application.launch(WordleGUIView.class, args);
    	}
    }
    
}
