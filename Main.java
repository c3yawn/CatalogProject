//package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert.*;
import javafx.stage.Stage;
import java.util.*;


public class Main extends Application {

	Button signIn = new Button("Sign In");
	Button createAccount = new Button("Create an Account");
	Button back = new Button("Back");
	
	//Stuff to add to main:
	Button order = new Button("Order");
	Button viewOrder = new Button("View Order");
	
	//Order Items
	//When adding a new item to the order menu
	//create the following for said item
	//with the format listed:
	//
	//Inventory Object: item#
	//Buttons: b_#, #_remove
	//Labels: #Amount
	Inventory itemOne = new Inventory("Lettuce", 4.0);
	Inventory itemTwo = new Inventory("Chicken", 6.0);
	Inventory itemThree = new Inventory("Beef", 8.0);
	Inventory itemFour = new Inventory("Broccoli", 3.0);
	
	
	
	//Order Cart
	//initialized here for simplicity
	int orderIndex = 0;
	Double total;
	int itemCardinality;
	//Items array will keep track of amount of each item
	//Please make a list that shows which index
	//is responsible for which item
	//0 - Lettuce
	//1 - Chicken
	//2 - Beef
	//3 - Broccoli
	//Hard-coded 4 for debug purposes, change according to amount of items
	int items[] = new int[4]; 
	//Customer object
	Customer customer;
	
	List<String> orders = new ArrayList<String>();
	
	//final checkout method
	//return true if completed
	//return false if not enough funds
	private boolean Transaction() {
		if (customer.Purchase(total)) {
			String p_format = String.format("%s - Cart: %s (%s), %s (%s), %s (%s), %s (%s) All items:(%s) TOTAL: %s\n",
					orderIndex, itemOne.getItem(), (itemOne.getPrice() * items[0]),
					itemTwo.getItem(), (itemTwo.getPrice() * items[1]),
					itemThree.getItem(), (itemThree.getPrice() * items[2]),
					itemFour.getItem(), (itemFour.getPrice() * items[3]),
					itemCardinality, total);
			orders.add(p_format);
			System.out.print(p_format);
			orderIndex++;
			return true;
		}
		else {
			return false;
		}
		
	}
	//Stuff to add - END
	
	Stage window;
	
	
	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	//Customer hard-code settings, 
    	//delete with data implementation
    	customer = new Customer("John Smith", 100.00);
    	
    	
        StackPane root = new StackPane(); // TLC (Top Layer Container) a root container for all other components, which in your case is the Button 
        root.getChildren().add(signIn);
        root.getChildren().add(createAccount);
        root.getChildren().add(viewOrder);
        //Add 1
        root.getChildren().add(order);
        signIn.setAlignment(Pos.CENTER);
       //signIn.setTranslateY(10);
        createAccount.setTranslateX(0);
        createAccount.setTranslateY(40);
        //Add 2
        order.setTranslateX(0);
        order.setTranslateY(80);
        //Add 3
        viewOrder.setTranslateX(0);
    	viewOrder.setTranslateY(120);
        Scene scene = new Scene(root, 500,500); // create the scene and set the root, width and height
        primaryStage.setScene(scene); // set the scenes
        primaryStage.setTitle("Store");
        primaryStage.show();
        
        
        
        
        
        // add action listener, 	
        //I will use the lambda style 
        //(which is data and code at the same time, read more about it in Oracle documentation)
        signIn.setOnAction(e->{
            //primaryStage.close(); // you can close the first stage from the beginning

            // create the structure again for the second GUI
            // Note that you CAN use the previous root and scene and just create a new Stage 
            //(of course you need to remove the button first from the root like this, root.getChildren().remove(0); at index 0)
            StackPane root2 = new StackPane();
            Label label = new Label("Welcome to the Sign In Page");
            root2.getChildren().add(label);
            Scene secondScene = new Scene(root2, 600,600);
            Stage secondStage = new Stage();
            secondStage.setScene(secondScene); // set the scene
            secondStage.setTitle("Sign In");
            secondStage.show();
            primaryStage.close(); // close the first stage (Window)
            root2.getChildren().add(back);
            back.setTranslateX(-275);
            back.setTranslateY(-275);  
            back.setOnAction(p -> {
        		secondStage.close();
        		primaryStage.show();
        	});
        });
        
        
        viewOrder.setOnAction(e ->{
        	StackPane root4 = new StackPane();
        	Label label = new Label("Current Orders");
        	root4.getChildren().add(label);
        	Scene fourthScene = new Scene(root4, 600, 600);
        	Stage fourthStage = new Stage();
        	fourthStage.setTitle("Current Orders");
        	fourthStage.setScene(fourthScene);
        	fourthStage.show();
        	primaryStage.close();
        	
        	
        	root4.getChildren().add(back);

        	back.setTranslateX(-275);
        	back.setTranslateY(-275);
        	back.setOnAction(p -> {
        		fourthStage.close();
        		primaryStage.show();
        	});
        });
        
        
        //Order section - New to add
        //Still need to add a go back button
        order.setOnAction(e->{
        	//Initialize items array to 0s
        	for(int i = 0; i < 4; i++) {
        		items[i] = 0;
        	}
        	
        	//This will host every single object
        	StackPane root3 = new StackPane();
        	//Order buttons 
        	Button checkout = new Button("Checkout");
        	total = 0.0;
        	itemCardinality = 0;

        	
        	//Initialized with dependence on the items above
        	Button b_one = new Button(itemOne.getItem());
        	Button b_two = new Button(itemTwo.getItem());
        	Button b_three = new Button(itemThree.getItem());
        	Button b_four = new Button(itemFour.getItem());
        	
        	//Decrementors:
        	Button one_remove = new Button("-1");
        	Button two_remove = new Button("-1");
        	Button three_remove = new Button("-1");
        	Button four_remove = new Button("-1");
        	
        	//Misc labels
        	Label amountColumn = new Label("Count");
        	Label oneAmount = new Label();
        	Label twoAmount = new Label();
        	Label threeAmount = new Label();
        	Label fourAmount = new Label();
        	Label totalAmount = new Label("Total due: ");
        	Label itemAmounts = new Label("Items in cart: ");
        		
        	//Customer labels
        	Label customerName = new Label("Name: " + customer.getName());
        	Label customerBalance = new Label("Balance: " + customer.getBalanceString());
        	root3.getChildren().add(customerName);
        	root3.getChildren().add(customerBalance);
        	customerName.setTranslateX(-200);
        	customerName.setTranslateY(-200);
        	customerBalance.setTranslateX(-200);
        	customerBalance.setTranslateY(-160);
        	
        	//All objects being added to the Stackpane(root3)
        	root3.getChildren().add(totalAmount);
        	root3.getChildren().add(itemAmounts);
        	root3.getChildren().add(amountColumn);
        	root3.getChildren().add(oneAmount);
        	root3.getChildren().add(twoAmount);
        	root3.getChildren().add(threeAmount);
        	root3.getChildren().add(fourAmount);
        	root3.getChildren().add(checkout);
        	root3.getChildren().add(back);
        	root3.getChildren().add(b_one);
        	root3.getChildren().add(b_two);
        	root3.getChildren().add(b_three);
        	root3.getChildren().add(b_four);

        	//Positions of all objects
        	//All done in the following format:
        	//object1.x
        	//object1.y
        	amountColumn.setTranslateX(70);
        	amountColumn.setTranslateY(-40);
        	
        	oneAmount.setTranslateX(70);
        	oneAmount.setTranslateY(0);
        	twoAmount.setTranslateX(70);
        	twoAmount.setTranslateY(40);
        	threeAmount.setTranslateX(70);
        	threeAmount.setTranslateY(80);
        	fourAmount.setTranslateX(70);
        	fourAmount.setTranslateY(120);
        	
        	checkout.setTranslateX(0);
        	checkout.setTranslateY(160);
        	
        	back.setTranslateX(-200);
        	back.setTranslateY(-250);
        
        	
        	totalAmount.setTranslateX(180);
        	totalAmount.setTranslateY(-200);
        	itemAmounts.setTranslateX(180);
        	itemAmounts.setTranslateY(-160);
        	
        	one_remove.setTranslateX(-70);
        	one_remove.setTranslateY(0);
        	two_remove.setTranslateX(-70);
        	two_remove.setTranslateY(40);
        	three_remove.setTranslateX(-70);
        	three_remove.setTranslateY(80);
        	four_remove.setTranslateX(-70);
        	four_remove.setTranslateY(120);
        	
        	b_one.setTranslateX(0);
        	b_one.setTranslateY(0);
        	b_two.setTranslateX(0);
        	b_two.setTranslateY(40);
        	b_three.setTranslateX(0);
        	b_three.setTranslateY(80);
        	b_four.setTranslateX(0);
        	b_four.setTranslateY(120);
        	
        	//Extremely Important, suspect no change here:
        	Scene thirdScene = new Scene(root3, 600, 600);
        	Stage thirdStage = new Stage();
        	thirdStage.setTitle("Order");
        	thirdStage.setScene(thirdScene);
        	thirdStage.show();
        	primaryStage.close();
        	
        	back.setOnAction(p -> {
        		thirdStage.close();
        		primaryStage.show();
        	});
        	
        	//Check out button
        	//Alert when insufficient funds or empty cart
        	//completetion otherwise with fund removal working
        	checkout.setOnAction(p->{
        		Alert a = new Alert(AlertType.NONE);
        		if(total > 0) {
        			if(Transaction()){
            			customerBalance.setText(this.customer.getBalanceString());
            			a.setContentText("Transaction complete!");
            			a.setAlertType(AlertType.CONFIRMATION);
            			a.show();
            		}
            		else {
            			a.setAlertType(AlertType.ERROR);
            			a.setContentText("INSUFFICIENT FUNDS");
            			a.show();
            		}
        		}
        		else {
        			a.setAlertType(AlertType.ERROR);
        			a.setContentText("CART EMPTY");
        			a.show();
        		}
        	});
        	
        	     
        	
        	//Button actions for clicking
        	//the specific food
        	//increments items[] respectively
        	b_one.setOnAction(p->{
        		if(items[0] == 0){
        			root3.getChildren().add(one_remove);
        		}
        		items[0]++;
        		total += itemOne.getPrice();
        		itemCardinality++;
        		itemAmounts.setText("Items in car: " + itemCardinality + "");
        		totalAmount.setText("Total due: " + total + "");
        		oneAmount.setText(items[0] + "");
        	});
        	b_two.setOnAction(p->{
        		if(items[1] == 0){
        			root3.getChildren().add(two_remove);
        		}
        		items[1]++;
        		total += itemTwo.getPrice();
        		itemCardinality++;
        		itemAmounts.setText("Items in cart: " + itemCardinality + "");
        		totalAmount.setText("Total due: " + total + "");
        		twoAmount.setText(items[1] + "");
        	});
        	b_three.setOnAction(p->{
        		if(items[2] == 0){
        			root3.getChildren().add(three_remove);
        		}
        		items[2]++;
        		total += itemThree.getPrice();
        		itemCardinality++;
        		itemAmounts.setText("Items in cart: " + itemCardinality + "");
        		totalAmount.setText("Total due: " + total + "");
        		threeAmount.setText(items[2] + "");
        	});
        	b_four.setOnAction(p->{
        		if(items[3] == 0){
        			root3.getChildren().add(four_remove);
        		}
        		items[3]++;
        		total += itemFour.getPrice();
        		itemCardinality++;
        		itemAmounts.setText("Items in cart: " + itemCardinality + "");
        		totalAmount.setText("Total due: " + total + "");
        		fourAmount.setText(items[3] + "");
        	});
        	
        	one_remove.setOnAction(p->{
        		if(items[0] == 1){
        			root3.getChildren().remove(one_remove);
        		}
        		items[0]--;
        		total -= itemOne.getPrice();
        		itemCardinality--;
        		itemAmounts.setText("Items in car: " + itemCardinality + "");
        		totalAmount.setText("Total due: " + total + "");
        		oneAmount.setText(items[0] + "");
        	});
        	two_remove.setOnAction(p->{
        		if(items[1] == 1){
        			root3.getChildren().remove(two_remove);
        		}
        		items[1]--;
        		total -= itemTwo.getPrice();
        		itemCardinality--;
        		itemAmounts.setText("Items in car: " + itemCardinality + "");
        		totalAmount.setText("Total due: " + total + "");
        		twoAmount.setText(items[1] + "");
        		
        	});
        	three_remove.setOnAction(p->{
        		if(items[2] == 1){
        			root3.getChildren().remove(three_remove);
        		}
        		items[2]--;
        		total -= itemThree.getPrice();
        		itemCardinality--;
        		itemAmounts.setText("Items in car: " + itemCardinality + "");
        		totalAmount.setText("Total due: " + total + "");
        		threeAmount.setText(items[2] + "");
        		
        	});
        	four_remove.setOnAction(p->{
        		if(items[3] == 1){
        			root3.getChildren().remove(four_remove);
        		}
        		items[3]--;
        		total -= itemFour.getPrice();
        		itemCardinality--;
        		itemAmounts.setText("Items in car: " + itemCardinality + "");
        		totalAmount.setText("Total due: " + total + "");
        		fourAmount.setText(items[3] + "");
        		
        	});
        	     
            
        });

        
        createAccount.setOnAction(e->{
            //primaryStage.close(); // you can close the first stage from the beginning

            // create the structure again for the second GUI
            // Note that you CAN use the previous root and scene and just create a new Stage 
            //(of course you need to remove the button first from the root like this, root.getChildren().remove(0); at index 0)
            StackPane root2 = new StackPane();
            Label label = new Label("Welcome to the Create Account Page");
            root2.getChildren().add(label);
            Scene secondScene = new Scene(root2, 600,600);
            Stage secondStage = new Stage();
            secondStage.setScene(secondScene); // set the scene
            secondStage.setTitle("Create Account");
            secondStage.show();
            primaryStage.close(); // close the first stage (Window)
            root2.getChildren().add(back);
            back.setTranslateX(-275);
            back.setTranslateY(-275);
            back.setOnAction(p -> {
        		secondStage.close();
        		primaryStage.show();
        	});
                    
        
        });
        
    }
    


    public static void main(String[] args) {
        launch();

    }

}