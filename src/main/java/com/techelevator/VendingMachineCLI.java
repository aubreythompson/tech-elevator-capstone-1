package com.techelevator;

import com.sun.jdi.PrimitiveValue;
import com.techelevator.view.Menu;

import java.sql.Statement;
import java.text.NumberFormat;
import java.util.List;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	private static final String PURCHASE_MENU_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_FEED_MONEY, PURCHASE_MENU_SELECT_PRODUCT, PURCHASE_MENU_FINISH_TRANSACTION};
	private static final String ENTER_MONEY_TO_FEED = "Select Amount To Feed";
	private static final String[] FEED_MONEY_OPTIONS = {"$1", "$2", "$5", "$10", "$20", "$50", "$100", "Return to Main Menu"};
	private static final String SELECT_PRODUCT_CODE = "Please select a product code:";
	private static final String CURRENT_MONEY_PROVIDED = "\nCurrent Money Provided: ";
	private static final String CHANGE_PROVIDED = "Change Provided:";
	private static final String QUARTERS = "\nQuarters: ";
	private static final String NICKELS = "\nDimes: ";
	private static final String DIMES = "\nNickels: ";
	private static final String PENNIES = "\nPennies: ";
	private static final String NO_CHANGE = "No money detected in machine. No change dispensed.";
	private static final String VENDING_MACHINE_SOURCE_FILE = "vendingmachine.csv";

	private final Menu menu;
	private final VendingMachine machine = new VendingMachine(VENDING_MACHINE_SOURCE_FILE);

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			switch (choice) {
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					System.out.println("Product Code | Name | Price | Quantity Remaining");
					for (Product product : machine.getProducts().values()) {
						System.out.println(product.toString());
					}
					break;
				case MAIN_MENU_OPTION_PURCHASE:
					purchaseSwitch();
					break;
				case MAIN_MENU_OPTION_EXIT:
					if (machine.getCurrentBalance() > 0){
						int [] change = machine.makeChange();
						System.out.println(CHANGE_PROVIDED + QUARTERS + change[0] + DIMES + change[1] + NICKELS + change[2] + PENNIES + change[3]);
					} else {
						System.out.println(NO_CHANGE);
					}
					//place logic for writing temp/persistent logs here
					System.exit(0);
			}
		}
	}

	public void purchaseSwitch(){
		String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

		switch (choice){
			case PURCHASE_MENU_FEED_MONEY:
				System.out.println(ENTER_MONEY_TO_FEED);
				String moneyFeed = (String) menu.getChoiceFromOptions(FEED_MONEY_OPTIONS);
				if (moneyFeed.equals(FEED_MONEY_OPTIONS[7]))
					break; /**this does not always return to the main menu
			 				-	if there is money, it goes to the purchase menu */
				machine.feedMoney(moneyFeed);
				System.out.println(CURRENT_MONEY_PROVIDED + NumberFormat.getCurrencyInstance().format(machine.getCurrentBalance()));
				purchaseSwitch();
			case PURCHASE_MENU_SELECT_PRODUCT:
				System.out.println(SELECT_PRODUCT_CODE);
				String productCode = (String) menu.getChoiceFromOptions(machine.getProducts().keySet().toArray()); /**we can use this, but it looks ugly :P */
				System.out.println(machine.makePurchase(productCode));
				System.out.println(CURRENT_MONEY_PROVIDED + NumberFormat.getCurrencyInstance().format(machine.getCurrentBalance()));
				purchaseSwitch();
			case PURCHASE_MENU_FINISH_TRANSACTION:
				if (machine.getCurrentBalance() > 0){
					int [] change = machine.makeChange();
					System.out.println(CHANGE_PROVIDED + QUARTERS + change[0] + DIMES + change[1] + NICKELS + change[2] + PENNIES + change[3]);
				} else{
					System.out.println(NO_CHANGE);
				}
				/** I changed this because we're supposed to return to the main menu here*/ //System.exit(0);
				break; /**this break has the same problem as above*/
		}
	}
}
