package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

	private final PrintWriter out;
	private final Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options,boolean hasHiddenOption,int hiddenOption) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options,hasHiddenOption,hiddenOption);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options,boolean hasHiddenOption,int hiddenOption) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			if (!(hasHiddenOption && i==hiddenOption)){
				out.println(optionNum + ") " + options[i]);
			}
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	/**Displays menu options for purchase specifically
	 * The following two functions were added so the user could select products via the product code
	 *
	 * @param options
	 */
	public void purchaseMenuOptions(Object[] options){
		out.println();
		for (int i = 0; i < options.length; i++){
			out.println(options[i]);
		}
		out.print(System.lineSeparator() + "please choose an option >>> ");
		out.flush();
	}

	/**
	 * Gets user input and returns the product code choice if it is valid
	 * @param options
	 * @return
	 */
	public Object purchaseInput(String[] options){
		Object choice = null;

		purchaseMenuOptions(options);
		String userInput = in.nextLine();
		choice = userInput;
		for (String key : options){
			if (key.substring(0,2).toUpperCase().equals(choice.toString().toUpperCase())){ //gets just the product code
				return choice;
			}
		}
		out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		return null;
	}
}
