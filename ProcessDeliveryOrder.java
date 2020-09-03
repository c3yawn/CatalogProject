import java.io.File;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;



public class ProcessDeliveryOrder
{
	
	
	public static void main(String args[]) 
			throws IOException
	{
		
		int a=0;
		int b=0;
		String order_status="Ready";
		String order;
		String Order_list[] = new String[10];
		String inventory;
		String Inventory_list[] = new String[10];
		String Inventory_Order[] = new String[10];	
		int i=0;
		int c=0;
		int d=0;
		
		
		Scanner keyboard = new Scanner(System.in);
		
		
		
		System.out.println("Enter order file name");
		order = keyboard.nextLine();
		
		//Asks user for Order text file
		File readfile = new File(order);
		Scanner inputFile = new Scanner(readfile);
		
		while(inputFile.hasNext())
		{
			String line = inputFile.nextLine();
			Order_list[a]=line;
			a++;
			
		}
		
		
		
		inputFile.close();
		
		//Asks user for  inventory file
		System.out.println("Enter inventory file name");
		inventory = keyboard.nextLine();
		
		
		File readfile2 = new File(inventory);
		Scanner inputFile2 = new Scanner(readfile2);
		
		while(inputFile2.hasNext())
		{
			String line = inputFile2.nextLine();
			Inventory_list[b]=line;
			b++;
			
		}
		
		inputFile2.close();
		
		//removes null items from order list array and inventory
		
		Order_list = Arrays.stream(Order_list)
                .filter(s -> (s != null && s.length() > 0))
                .toArray(String[]::new); 
		
		Inventory_list = Arrays.stream(Inventory_list)
                .filter(s -> (s != null && s.length() > 0))
                .toArray(String[]::new); 
		
		
		//outputs order and inventory
		System.out.println("Order ");
		System.out.println(Arrays.toString(Order_list));
		
		System.out.println("Inventory ");
		System.out.println(Arrays.toString(Inventory_list));
		
		//compares both arrays to find if order items are in stock
		//sets oder status 
		
		while(c <= Order_list.length-2)
		{
			
			
		if(Objects.equals(Inventory_list[i], Order_list[c]))
		{
			
			int I_result = Integer.parseInt(Inventory_list[i+1]);
			int O_result = Integer.parseInt(Order_list[c+1]);
			
			if(I_result>=O_result)
			{
			System.out.println(Order_list[c] + " are in stock");
			c=c+2;
			}
			else
			{
				System.out.println(Order_list[c]+" is out of stock");
				
				order_status = "Not Ready";
				Inventory_Order[d] = Order_list[c];
				c=c+2;
				d++;
			}
		}
		else
		{
			i++;
		}
		
	
		
		}
		
		
		
		 //outputs order status
		 System.out.println("Order is "+order_status);
		 
		 //outputs array of inventory order if needed
		 if(d>0)
		 {
			 
			 Inventory_Order = Arrays.stream(Inventory_Order)
		                .filter(s -> (s != null && s.length() > 0))
		                .toArray(String[]::new); 
			 
			 System.out.println("An inventory order is needed for these items");
			 System.out.println(Arrays.toString(Inventory_Order));
		 }
		 
		
	}

}
