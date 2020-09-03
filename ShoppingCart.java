package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class ShoppingCart extends Application {

	

    ArrayList<Inventory> inventoryList, cartList;

    ListView<Inventory> inventoryView, cartView;

    Label subTotal, tax, total;

    double cartsubTotal = 0, cartTax = 0, cartTotal = 0;
    
    final double taxRate = 0.08;

    public static void main(String[] args) {

        launch(args); // Launch the application.

    }

    
    @Override
    
    public void start(Stage primaryStage) throws IOException
    {
    	//Build the inventory array
    	readinventoryFile();
    	
    	//convert inventory array to observable 
    	
    	ObservableList<Inventory> list = FXCollections.observableArrayList(inventoryList);
    	
    	//builf the inventory listview
    	inventoryView = new ListView<>(list);
    	
    	//shopping cart listview
    	cartView = new ListView<>();
    	
    	//create labels for the cart
    	subTotal = new Label("Your Subtotal: ");
    	tax = new Label("Your Tax: ");
    	total = new Label("Your Cart: ");
    	
    	//create buttons 
    	
    	Button addtoCart = new Button("Add item to Cart");
    	cartList = new ArrayList<>();
    	
    	addtoCart.setOnAction(e -> {
    		
    		//the clicked item is picked
    		int selected = inventoryView.getSelectionModel().getSelectedIndex();
    		
    		//when the selected item is picked, it is added to the cart
    		if (selected != -1)
    		{
    			Inventory j = inventoryList.get(selected);
    			//update the cart
    			
    			cartList.add(inventoryList.get(selected));
    			
    			//update the cartview 
    			cartView.setItems(FXCollections.observableArrayList(cartList));
    			
    			//update the cart subtotal
    			cartsubTotal += j.getPrice();
    		}
    		
    });
	
    	//remove item from cart button
    	
    	Button removefromCart = new Button("Remove item from Cart");
    	
    	removefromCart.setOnAction(e -> {
    		int selected = cartView.getSelectionModel().getSelectedIndex();
    		
    		if (selected != -1) {
    			//update the subtotal
    			cartsubTotal -= cartList.get(selected).getPrice();
    			
    			//remove the clicked item from the cart & update
    			
    			cartList.remove(selected);
    			
    			cartView.setItems(FXCollections.observableArrayList(cartList));
    			
    		}
    		
    		
    	});
    	
    	 Button checkOut = new Button("Checkout");


	        checkOut.setOnAction(e -> {

	            // Calculate the tax

	            cartTax = cartsubTotal * taxRate;

	            // Calculate the total

	            cartTotal = cartsubTotal + cartTax;

	            Labels();

	            //setup dates

	            String timeStamp = new SimpleDateFormat("MM-DD-YYYY HH:mm:ss").format(Calendar.getInstance().getTime());

	            String fntimeStamp = new SimpleDateFormat("MM-DD-YYYY.HH.mm.ss").format(Calendar.getInstance().getTime());

	            //create and open receipt file

	            String filename = "Receipt-" + fntimeStamp + ".txt";

	            try {

	                //open file

	                PrintWriter receiptFile = new PrintWriter("src/application/" + filename);

	                //create the receipt

	                receiptFile.println("Your Receipt: "+timeStamp);

	                receiptFile.printf("%-25s %-10s %-10s %-10s\n", "Title", "Quantity", "Price", "Total");

	                //creating a set to get rid of duplicates

	                Set<Inventory> inventorySet = new LinkedHashSet<>(cartList);

	                for (Inventory j : inventorySet) {

	                    //finding quantity

	                    int quantity = inventoryQuantity(j, cartList);

	                    receiptFile.printf("%-25s %-10d $%-9.2f $%-9.2f\n", j.getItem(),quantity, j.getPrice(), j.getPrice() * quantity);

	                }

	                receiptFile.printf("%-25s %-10s %-10s $%-9.2f\n", "Total", "", "", cartsubTotal);

	                receiptFile.printf("%-25s %-10s %-10s $%-9.2f\n", "Tax", "", "", cartTax);

	                receiptFile.printf("%-25s %-10s %-10s $%-9.2f\n", "Grand Total", "", "", cartTotal);

	                //close the file

	                receiptFile.close();

	            } catch (FileNotFoundException e1) {

	                e1.printStackTrace();

	                System.out.println("file open error");

	            }

	        });
    	

	      VBox vbox = new VBox(addtoCart);
	      
        vbox.setAlignment(Pos.BOTTOM_CENTER);

        HBox hbox = new HBox(removefromCart, checkOut);
        hbox.setSpacing(50);

        hbox.setAlignment(Pos.BOTTOM_RIGHT);

        Label label = new Label("Available Items: ");

        Label label1 = new Label("Your Cart: ");
        
        VBox labels = new VBox(subTotal, tax, total);
        // Put everything into a VBox
        labels.setAlignment(Pos.CENTER);
        labels.setSpacing(10);
        HBox root = new HBox(label, inventoryView,vbox, label1, cartView,  hbox, labels);

        
        // Add the main VBox to a scene.

        Scene scene = new Scene(root, 1300, 500);

        primaryStage.setScene(scene);

        primaryStage.show();


        
    }
    
    private void Labels()
    {
    	   subTotal.setText(String.format("Your Subtotal: $%.2f", cartsubTotal));

	        tax.setText(String.format("Your Tax: $%.2f", cartTax));

	        total.setText(String.format("Your Total: $%.2f", cartTotal));
    }
    
    //determine the quantity of the cart 
    
    private int inventoryQuantity(Inventory j, ArrayList<Inventory> list)
    {
    	int counter = 0;
    	
    	for (Inventory items : list ) {
    		if (items.getItem().equalsIgnoreCase(j.getItem())) {
    			counter++;
    		}
    	}
    	return counter;
    }
    
    private void readinventoryFile() throws IOException{
    	inventoryList = new ArrayList<Inventory>();
    	
    	String input; 
    	
    	File file = new File("src/application/Inventory.txt");
    	
    	Scanner readFile = new Scanner(file);
    	
    	//read the opened file 
    	while (readFile.hasNext()) {
    		String item = readFile.nextLine();
    		
    		//tokenize
    		String area [] = item.split(",");
    		
    		Inventory j = new Inventory(area[0].trim(), Double.parseDouble(area[1].trim()));
    		
    		//add the item to the array
    		inventoryList.add(j);
    	}
    			readFile.close();
    	
    }
}
    


		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	