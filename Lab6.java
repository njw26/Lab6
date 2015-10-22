import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class Lab6 
{
	public static void main(String[] args) 
	{
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("How big would you like the universe to be?");
		int size = 0; //Initialize size to 0
		
		boolean flag = true; //While loop making sure that the user types in a valid response (that being an int).
		
		while(flag)
		{
			if(sc.hasNextInt())
			{
				size = sc.nextInt();
				flag = false;
			}
			
			else //If they type something that isn't an int...
			{
				sc.next(); //Get rid of this token
				System.out.println("Please enter an integer for universe size.");
			}
		}
		
		char[] universe = new char[size]; //Creates character array with size of whatever the user has input.
		
		universe = createUniverse(universe); //Uses createUniverse method to initially create the char array known as universe
		
		printUniverse(universe); //Uses printUniverse to print the universe to the screen for the first time
		
		boolean frog = true;
		do
		{
			System.out.println("Advance(A), Save(S), or Quit(Q)?");
			String response = sc.next();
			response = response.toLowerCase();
			
			if(response.equals("a")) //If they choose to advance, advance universe then print it out
			{
				universe = advanceUniverse(universe);
				printUniverse(universe);
			}
			else if(response.equals("s")) //If they choose to save the universe, save it then print it out
			{
				saveUniverse(universe);
				printUniverse(universe);
			}
			else //Otherwise exit program
			{
				System.exit(0);
			}
		}
		while(frog == true);
	}
	
	private static char[] createUniverse(char[] arrSoFar) //Method for initially creating the universe (array).
	{
		for(int i = 0; i < arrSoFar.length; i++)
		{
			if(i == 0 || (i % 7) == 0)
			{
				arrSoFar[i] = 0; //If the index position is 0 or a multiple of 7, place a baby (0) there.
			}
			else if(i % 5 == 0)
			{
				arrSoFar[i] = '^'; //If the index position is a multiple of 5, put a carrot (^) there.
			}
			else
			{
				arrSoFar[i] = '.'; //Otherwise, just put a . there.
			}
		}
		
		return arrSoFar; 
	}
		
	private static char[] advanceUniverse(char[] currentArr) //Method for advancing the array upon user desire
	{ 
		for(int j = 0; j < currentArr.length; j++) //Scans each element in the array (each object in the universe)
		{
			if(currentArr[j] == 2) //If there is an adult at this index position
			{
				if(j != currentArr.length - 1) //If the adult is not at the end of the array
				{
					if(currentArr[j + 1] != 0 && currentArr[j + 1] != 1 && currentArr[j + 1] != 2) //If there isn't a numericon at the position right in front of the adult
					{
						if(currentArr[j + 1] == '^') //If there is a carrot at the position right ahead of the adult
						{
							currentArr[j + 1] = 0; //Adult eats carrot, moves to next position, and becomes baby
							currentArr[j] = '.'; //Adult's former position becomes a .
							j++; //Need to increment j one more time before running through the loop again, since you don't want to touch this spot again
						}
						else //If there isn't a carrot there
						{
							currentArr[j + 1] = 2; //Adult simply moves there, doesn't change form
							currentArr[j] = '.'; //Adult's former position becomes a .
							j++; //Need to increment j one more time before running through the loop again, since you don't want to touch this spot again
						}
					}
				}
				else //If the adult is at the end of the array, we'll now check the first element of the array, which is the adult's next destination
				{
					if(currentArr[0] != 0 && currentArr[0] != 1 && currentArr[0] != 2) //If there isn't a numericon at the first position in the array (where the adult needs to go)
					{
						currentArr[0] = 2; //Put the adult here
						currentArr[j] = '.'; //Put a dot at the adult's old position
					}
				}
			}
			
			else if(currentArr[j] == 1) //If there is a child at this index position
			{
				currentArr[j] = 2; //Turn the child into an adult
			}
			
			else if(currentArr[j] == 0) //If there is a baby at this index position
			{
				currentArr[j] = 1; //Turn that baby into a child
			}
		}
		
		return currentArr;
	}
	
	private static void printUniverse(char[] arr) //Method for printing the universe (array)
	{
		for(int i = 0; i < arr.length; i++)
		{
			System.out.print(arr[i]);
		}
		System.out.print("\n");
	}
	
	private static void saveUniverse(char[] arrToBeSaved) //Method for saving the universe to a file called "universe.txt" 
	{
		int babies = 0;
		int children = 0;
		int adults = 0;
		
		BufferedWriter writer = null;
	    try 
		{
	        writer = new BufferedWriter(new FileWriter("universe.txt"));
	        for (int i = 0; i < arrToBeSaved.length; i++)
	        {      
	          writer.write(arrToBeSaved[i]);
			  if(arrToBeSaved[i] == 0)
			  {
				  babies++;
			  }
			  if(arrToBeSaved[i] == 1)
			  {
				  children++;
			  }
			  if(arrToBeSaved[i] == 2)
			  {
				  adults++;
			  }
	          writer.newLine();
	          writer.flush();
	        }
	        writer.newLine();
			
			writer.write("Babies: " + babies);
			writer.newLine();
			writer.write("Children: " + children);
			writer.newLine();
			writer.write("Adults: " + adults);
			
			writer.close();
	    } 
		
		catch(IOException exc)
		{
		}
	}
}