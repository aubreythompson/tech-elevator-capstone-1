package com.techelevator;

import com.techelevator.view.Menu;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String SUPER_SECRET_SALES_LOG = "";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, SUPER_SECRET_SALES_LOG };
	private static final String PURCHASE_MENU_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_FEED_MONEY, PURCHASE_MENU_SELECT_PRODUCT, PURCHASE_MENU_FINISH_TRANSACTION};
	private static final String ENTER_MONEY_TO_FEED = "Select Amount To Feed";
	private static final String[] FEED_MONEY_OPTIONS = {"$1", "$2", "$5", "$10", "$20", "$50", "$100", "Return to Main Menu", "Return to Purchase Menu"};
	private static final String SELECT_PRODUCT_CODE = "Please select a product code:";
	private static final String CURRENT_MONEY_PROVIDED = "\nCurrent Money Provided: ";
	private static final String CHANGE_PROVIDED = "Change Provided:";
	private static final String QUARTERS = "\nQuarters: ";
	private static final String NICKELS = "\nNickels: ";
	private static final String DIMES = "\nDimes: ";
	private static final String PENNIES = "\nPennies: ";
	private static final String NO_CHANGE = "No money detected in machine. No change dispensed.";
	private static final String VENDING_MACHINE_SOURCE_FILE = "vendingmachine.csv";
	private static final String VENDING_MACHINE_LOG_FILE = "log.txt";
	private static final String VENDING_MACHINE_SALES_LOG_FILE = "salesLog.txt";
	private static final String INVALID_DOLLAR_AMOUNT = "Invalid dollar amount entered, please try again";

	private final Menu menu;
	private final VendingMachine machine = new VendingMachine(VENDING_MACHINE_SOURCE_FILE);

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void mainMenu() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS,true,3);

			switch (choice) {
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					List<String> productList = new ArrayList<>();

					//Replaces displaying products via hashmap with displaying products via a list
					//this way they can be displayed in order alphanumerically by slot
					//seems to update fine from testing
					//still left getproducts returning a hashmap though (assumed you made it that way with intent for future use)
					for (Product product : machine.getProducts().values()) {
						productList.add(product.toString());
					}
					Collections.sort(productList);
					System.out.println("Product Code | Name | Price | Quantity Remaining");
					for (String item : productList){
						System.out.println(item);
					}
					break;
				case MAIN_MENU_OPTION_PURCHASE:
					purchaseSwitch();
					break;
				case MAIN_MENU_OPTION_EXIT:
					machine.writeLog(VENDING_MACHINE_LOG_FILE);
					machine.writeSalesLog(VENDING_MACHINE_SALES_LOG_FILE);
					System.exit(0);
				case SUPER_SECRET_SALES_LOG:
					HashMap<String, Integer> logMap = machine.readSalesLog(VENDING_MACHINE_SALES_LOG_FILE);
					String totalSales = logMap.entrySet().toString();
					totalSales = totalSales.substring(1, totalSales.length()-1);
					String[] totalSalesArray = totalSales.split(", ");
					for (String line : totalSalesArray){
						System.out.println(line.replace("=", "|"));
					}
			}
		}
	}

	public void purchaseSwitch(){
		String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS,false,0);

		switch (choice){
			case PURCHASE_MENU_FEED_MONEY:
				System.out.println(ENTER_MONEY_TO_FEED);
				String moneyFeed = (String) menu.getChoiceFromOptions(FEED_MONEY_OPTIONS,false,0);
				if (moneyFeed.equals(FEED_MONEY_OPTIONS[7]))
					mainMenu();
				if (moneyFeed.equals(FEED_MONEY_OPTIONS[8]))
					purchaseSwitch();
				if (moneyFeed.equals(null))
					System.out.println(INVALID_DOLLAR_AMOUNT);
				machine.feedMoney(moneyFeed);
				System.out.println(CURRENT_MONEY_PROVIDED + NumberFormat.getCurrencyInstance().format(machine.getCurrentBalance()));
				purchaseSwitch();
			case PURCHASE_MENU_SELECT_PRODUCT:
				System.out.println(SELECT_PRODUCT_CODE);
				String productKeys = machine.getProducts().keySet().toString();
				//had to make substring to remove brackets that for some reason were placed in string when getting the keysets
				//so that the for loop below could properly read the values (some values were being assigned to null due to A4 really being A4] in the array)
				productKeys = productKeys.substring(1, productKeys.length()-1);
				String slotSelection = (String) menu.purchaseInput(purchaseProductsArray(productKeys));
				if (slotSelection != null){
					System.out.println(machine.makePurchase(slotSelection.toUpperCase()));
					System.out.println(CURRENT_MONEY_PROVIDED + NumberFormat.getCurrencyInstance().format(machine.getCurrentBalance()));
				}
				purchaseSwitch();
			case PURCHASE_MENU_FINISH_TRANSACTION:
				if (machine.getCurrentBalance().compareTo(new BigDecimal(0)) ==1){
					int [] change = machine.makeChange();
					System.out.println(CHANGE_PROVIDED + QUARTERS + change[0] + DIMES + change[1] + NICKELS + change[2] + PENNIES + change[3]);
				} else{
					System.out.println(NO_CHANGE);
				}
				mainMenu();
		}
	}

	public String[] purchaseProductsArray(String productKeys){
		String[] productKeysArray = productKeys.split(", ");
		Arrays.sort(productKeysArray);
		String[] productValues = new String[productKeysArray.length];
		Map<String, Product> productMap = machine.getProducts();
		for (int i = 0; i < productKeysArray.length; i++){
			if (productMap.containsKey(productKeysArray[i])){
				productValues[i] = productMap.get(productKeysArray[i]).getSlot() + ": " + productMap.get(productKeysArray[i]).getName();
			}
		}
		return productValues;
	}
}
